#coding=utf-8

import sys
import logging
import re
import time
import urllib
import random
import uuid

from google.appengine.api import users
from google.appengine.ext import db
from google.appengine.api import memcache

import utils
import constants
from paginator import *

gen_key_name = lambda s: 'kn' + uuid.uuid3(uuid.NAMESPACE_URL, utils.encode_str(s)).get_hex()

_SHARDS_PER_COUNTER = 20
#http://feeds.feedburner.com/~r/AppEngineFan/~3/310349458/efficient-global-counters.html
class ShardCounter(db.Model):
    name = db.StringProperty(required=True)
    count = db.IntegerProperty(default=0)
    
    @classmethod
    def get_count(cls, nameOfCounter):
        memcache_id = '/ShardCounter/%s' %  nameOfCounter
        result = utils.get_from_cache(memcache_id)
        if not (result == None):
            return result
        result = 0
        for shard in cls.gql('WHERE name=:1', nameOfCounter):
            result += shard.count
        memcache.set(memcache_id, result)
        return result
    @classmethod
    def update_count(cls, nameOfCounter, delta):
        memcache_id = '/ShardCounter/%s' %  nameOfCounter
        memcache.delete(memcache_id)
        shard_id = '/%s/%s' % (nameOfCounter, random.randint(1, _SHARDS_PER_COUNTER))
        def update():
            shard = cls.get_by_key_name(shard_id)
            if shard:
                shard.count += delta
            else:
                shard = cls(key_name=shard_id, name=nameOfCounter, count=delta)
            shard.put()
        db.run_in_transaction(update)

class Account(db.Model):
    
    user         = db.UserProperty(required=True)
    email        = db.EmailProperty(required=True)  # key == <email>
    nickname     = db.StringProperty()
    created      = db.DateTimeProperty(auto_now_add=True)
    modified     = db.DateTimeProperty(auto_now=True)
    access_count = db.IntegerProperty(default = 0)

    @classmethod
    def get_account_for_user(cls, user):
        """Get the Account for a user, creating a default one if needed."""
        email = user.email()
        assert email
        key = '<%s>' % email
        nickname = user.nickname()
        if '@' in nickname:
            nickname = nickname.split('@', 1)[0]
        assert nickname
        return cls.get_or_insert(key, user=user, email=email, nickname=nickname)

    @classmethod
    def get_account_for_email(cls, email):
        """Get the Account for an email address, or return None."""
        assert email
        key = '<%s>' % email
        return cls.get_by_key_name(key)
    
    @classmethod
    def get_current_account(cls):
        usr = users.get_current_user()
        return Account.get_account_for_user(usr)
    
    @property
    def is_admin(self):
        return users.is_current_user_admin()
    
    @property
    def is_sign_in(self):
        return not self.email == 'anonymous@gmail.com' 
    

class Permission(db.Model):
    username     = db.StringProperty()
    object_type  = db.StringProperty()
    object_id    = db.IntegerProperty()
    date_created = db.DateTimeProperty()

class Service(db.Model):
    service_id       = db.StringProperty()
    profile_url      = db.StringProperty()
    entry_type       = db.StringProperty()
    name             = db.StringProperty() 
    
    @classmethod
    def get_service(cls, service_id, profile_url, entry_type, name):
        memcache_id = ':'.join(['service', service_id, profile_url]);
        service = utils.get_from_cache(memcache_id)
        if service is None:
            service = cls.gql('WHERE service_id = :1 AND profile_url = :2', service_id, profile_url).get();
            if service is None:
                service = cls(service_id = service_id, profile_url = profile_url, entry_type = entry_type, name = name)
                service .put()
            memcache.set(memcache_id, service)
        return service

class FeedEntry(db.Model):
    entry_id  = db.StringProperty()
    title     = db.StringProperty()
    published = db.DateTimeProperty()
    updated   = db.DateTimeProperty()
    link      = db.StringProperty()
    service   = db.ReferenceProperty(Service)
    
    @classmethod
    def get_all(cls):
        memcache_id = 'feed_entries'
        all_entries = utils.get_from_cache(memcache_id)
        if all_entries is None:
            all_entries = cls.gql('ORDER BY published DESC').fetch(1000)
            memcache.set(memcache_id, all_entries)
        return all_entries
    
    def save(self):
        memcache.delete('feed_entries')
        self.put();
    
    @classmethod
    def get_page(cls, page_index, page_size = constants.DEFAULT_PAGE_SIZE):
        all_entries = cls.get_all()
        total_count = len(all_entries)
        offset = (page_index - 1) * page_size
        end = offset + page_size if offset + page_size < total_count else total_count
        page = Page(all_entries[offset:end], page_index, total_count, page_size)
        return page
    
    @property
    def domain_name(self):
        return utils.extract_domain_name(self.link)
    
    @property
    def desplay_date(self):
        return utils.get_display_date(self.published)
    
class Entry(db.Model):
    """The class representing an entry in the blog"""
    
    ENTRY_STATUS = (
            (1 , 'DRAFT'),
            (2 , 'PUBLISHED'),
            (3 , 'PENDING'),
            (4 , 'SCHEDULED')
        )
    
    ENTRY_TYPE = (
            (1, 'ESSAY'),
            (2, 'FRIEND_FEED_API')
        )
    
    title          = db.StringProperty()
    link           = db.StringProperty()
    summary        = db.TextProperty()
    text           = db.TextProperty()
    content_type   = db.StringProperty()
    content_src    = db.StringProperty()
    entry_type     = db.IntegerProperty(default = 1) #news or essay
    author         = db.StringProperty()
    pub_time       = db.DateTimeProperty(auto_now_add=True)
    update_time    = db.DateTimeProperty(auto_now=True)
    allow_comments = db.BooleanProperty()
    status         = db.StringProperty(default = 'DRAFT', choices = ['DRAFT', 'PUBLISHED', 'PENDING', 'SCHEDULED'])
    
    #用逗号分隔的标签名称
    tags_as_str       = db.StringProperty(default = '')
    
    tag_key_list   = db.ListProperty(db.Key)
    
    def save(self):
        logging.debug('entering save method...')
        
        logging.debug('new tags are: ' + self.tags_as_str)
        new_tag_list = [t for t in re.split('[\s,]+', self.tags_as_str) if t]
        
        for tk in self.tag_key_list:
            tag = db.get(tk)
            tag.entry_count -= 1
            if tag.entry_count <= 0:
                tag.delete()
            else:
                tag.put()
        
        self.tag_key_list = [EntryTag.get_or_insert(gen_key_name(tag_name), name = tag_name).key() for tag_name in new_tag_list]
        
        for tk in self.tag_key_list:
            tag = db.get(tk)
            tag.entry_count += 1
            tag.put()
        
        self.put()
    
    def create(self):
        self.save()
        #总数计数器增1
        ShardCounter.update_count(constants.TOTAL_ENTRY_COUNT, 1)
    
    def update(self):
        self.save()
    
    def remove(self):
        for tk in self.tag_key_list:
            tag = db.get(tk)
            tag.entry_count -= 1
            if tag.entry_count <= 0:
                tag.delete()
            else:
                tag.put()
        self.delete()
        #总数计数器减1
        ShardCounter.update_count(constants.TOTAL_ENTRY_COUNT, -1)
    
    @classmethod
    def get_page(cls, page_num, page_size = constants.DEFAULT_PAGE_SIZE):
        offset = (page_num - 1) * page_size
        entries = cls.gql('WHERE status = :1 ORDER BY pub_time DESC', 'PUBLISHED').fetch(page_size, offset)
        entry_count = ShardCounter.get_count(constants.TOTAL_ENTRY_COUNT)
        logging.debug('entry count: ' + str(entry_count))
        page = Page(entries, page_num, entry_count, page_size)
        return page
    
    def approve(self):
        self.status = 'PUBLISHED'
        self.put()
    
    @property
    def id(self):
        return self.key().id()
        
    @property
    def local_put_time(self):
        return utils.get_local_time(self.pub_time).strftime('%Y-%m-%d %H:%M')
    
    @property
    def content_summary(self):
        logging.debug('begin to generate summary')
        summary = utils.get_summary(self.text)
        return summary
    
    @property
    def entry_tags(self):
        return [db.get(tk) for tk in self.tag_key_list]
    
    @property 
    def tags_as_string(self):
        return ','.join([t.name for t in self.entry_tags])

class EntryTag(db.Model):
    
    name        = db.StringProperty(required=True)
    add_time    = db.DateTimeProperty(auto_now_add = True)
    update_time = db.DateTimeProperty(auto_now=True)
    entry_count = db.IntegerProperty(default = 0)       #标签所包含的文章数，初始值是0
    
    #值为显示标签时的css值
    @property
    def weight(self):
        if self.entry_count >= 4:
            return 3
        elif self.entry_count <= 4 and self.entry_count >=2:
            return 4
        else:
            return 5
    
    @property
    def key_name(self):
        return self.key().name()
    
    @property
    def entries(self):
        return Entry.gql('WHERE tag_key_list = :1 and status = :2', self.key(), 'PUBLISHED')
    
    @classmethod
    def all_entry_tags(cls):
        return EntryTag.gql('WHERE entry_count > 0')


class EntryComment(db.Model):
    
    COMMENT_STATUS = (
            (1 , 'APPROVED'),
            (2 , 'DISAPPROVED'),
            (3 , 'SPAM'),
            (4 , 'PENDING')
        )
    
    name         = db.StringProperty()
    email        = db.StringProperty()
    url          = db.StringProperty()
    content      = db.TextProperty()
    post_time    = db.DateTimeProperty()
    status       = db.StringProperty()
    notify       = db.BooleanProperty()
    remote_host  = db.StringProperty()
    referer      = db.StringProperty()
    user_agent   = db.StringProperty()
    content_type = db.StringProperty()
    
    weblog_entry = db.ReferenceProperty(Entry, collection_name='entry_comments')
    
    def save(self):
        logging.debug('entering save comment method')
        self.put()
        if self.weblog_entry is not None:
            self.weblog_entry.put()
    
class Res(db.Model):
    link  = db.StringProperty()
    feed_link = db.StringProperty()
    description  = db.StringProperty()
    author = db.StringProperty()
    status = db.StringProperty()
    pub_time       = db.DateTimeProperty(auto_now_add=True)
    update_time    = db.DateTimeProperty(auto_now=True)
    
    tag_key_list = db.ListProperty(db.Key)
    
    tags_as_str  = db.StringProperty()
    
    @property
    def res_tags(self):
        return (db.get(tk) for tk in self.tag_key_list)
    
    @classmethod
    def get_page(cls, page_num, page_size = constants.DEFAULT_PAGE_SIZE):
        res_count = ShardCounter.get_count(constants.TOTAL_RES_COUNT)
        offset = (page_num - 1) * page_size
        resources = Res.gql('WHERE status = :1 ORDER BY pub_time', 'PUBLISHED').fetch(page_size, offset)
        res_count = ShardCounter.get_count(constants.TOTAL_ENTRY_COUNT)
        page = Page(resources, page_num, res_count, page_size)
        return page
        
    
    def save(self):
        new_tag_list = [t for t in re.split('[\s,]+', self.tags_as_str) if t]
        
        #remove old tags
        for tk in self.tag_key_list:
            t = db.get(tk)
            t.res_count -= 1
            if t.res_count <= 0:
                t.delete()
            else:
                t.put()
        
        self.tag_key_list = [ResTag.get_or_insert(gen_key_name(tag_name), name = tag_name).key() for tag_name in new_tag_list]
        
        for tk in self.tag_key_list:
            t = db.get(tk)
            t.res_count += 1
            t.put()

        self.put()
    
    def create(self):
        self.save()
        ShardCounter.update_count(constants.TOTAL_RES_COUNT, 1)
    
    def update(self):
        self.save()
    
    def remove(self):
        for tk in self.tag_key_list:
            t = db.get(tk)
            t.res_count -= 1
            if t.res_count <= 0:
                t.delete()
            else:
                t.put()
        self.delete()
        ShardCounter.update_count(constants.TOTAL_RES_COUNT, -1)
    
    def approve(self):
        self.status = 'PUBLISHED'
        self.put()
    
    @property
    def encoded_feed_url(self):
        return urllib.quote(self.feed_link) if self.feed_link is not None else urllib.quote(self.link)
    
class ResTag(db.Model):
    name = db.StringProperty()
    add_time    = db.DateTimeProperty(auto_now_add = True)
    update_time = db.DateTimeProperty(auto_now=True)
    res_count = db.IntegerProperty(default=0)
    
    @property
    def weight(self):
        if self.res_count >= 4:
            return 3
        elif self.res_count <= 4 and self.res_count >=2:
            return 4
        else:
            return 5
    
    @property
    def resources(self):
        return Res.gql('WHERE tag_key_list = :1 and status = :2', self.key(), 'PUBLISHED')
    
    @classmethod
    def all_res_tags(cls):
        return ResTag.gql('WHERE res_count > 0')
    
    @property
    def key_name(self):
        return self.key().name()
    
    