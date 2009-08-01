#coding=utf-8

import logging

from google.appengine.api import users
from google.appengine.ext import db
from google.appengine.api import memcache

from models import *
from app import *
from utils import util
from utils import page
import constants

### Decorators for request handlers ###
def login_required(func):
  """Decorator that redirects to the login page if you're not logged in."""
  def login_wrapper(self, *args, **kwds):
    if not self.get_account().is_sign_in:
      self.handler.redirect(users.create_login_url(self.request.path))
    return func(self, *args, **kwds)
  return login_wrapper

def admin_required(func):
  def admin_wrapper(self, *args, **kwds):
    """Decorator that insists that you're logged in as administratior."""
    if not self.get_account().is_sign_in:
      self.handler.redirect(users.create_login_url(self.request.path))
    if not self.get_account().is_admin:
      self.render('admin-required')
      return None
    return func(self, *args, **kwds)
  return admin_wrapper
#######################################################

class IndexController(RestController):
    def index(self, kwds = None):
        page = utils.get_from_cache(constants.FEED_FIRST_PAGE_CACHE_KEY)
        if page is None:
            page = FeedEntry.get_page(1)
            memcache.set(constants.FEED_FIRST_PAGE_CACHE_KEY, page)
        self.render('index', {'page' : page})
        
        #page = utils.get_from_cache(constants.FIRST_PAGE_CACHE_KEY)
        #if page is None:
            #page = Entry.get_page(1)
            #memcache.set(constants.FIRST_PAGE_CACHE_KEY, page)
        #tags = EntryTag.all_entry_tags().fetch(constants.DEFAULT_PAGE_SIZE)
        #self.render('index', {'page' : page, 'tags' : tags})
        
class EntryController(RestController):
    #如果tag_key_name不为空，则显示某一个Tag下的Entry
    def index(self, kwds = None):
        if kwds != None and len(kwds) > 0:
            tag_kn = kwds['tag_key_name']
            tag = EntryTag.get_by_key_name(tag_kn)
            entries = {}
            if tag is not None:
                entries = tag.entries;
            return self.render('frag/entries-for-tag', {'entries' : entries, 
                                                        'tag'    :  tag})
        else:
            fromAdmin = self.request.get('from')
            #来自管理界面
            if fromAdmin == 'admin':   
                entries = Entry.all()
                entry_array = [{ 'id'    : entry.key().id(), 
                             'title' : entry.title,
                             'status': entry.status} for entry in entries]
                self.render_json({'msg' : 'OK', 'entries' : entry_array})
            #分页查看
            else:
                page_number = int(self.request.get('page'), 10)
                page = Entry.get_page(page_number)
                tags = EntryTag.all_entry_tags().fetch(constants.DEFAULT_PAGE_SIZE)
                self.render('index', {'page' : page, 'tags' : tags})
            
    def show(self, id, kwds = None):
        entry = Entry.get_by_id(long(id))
        if entry is not None:
            return self.render("entry", {'entry' : entry})
        return self.render('error')
    
    def edit_new(self, kwds = None):
        tags = EntryTag.all_entry_tags().fetch(constants.DEFAULT_PAGE_SIZE)
        self.render('entry-form', {'tags' : tags})
        
    def edit(self, id, kwds = None):
        entry = Entry.get_by_id(long(id))
        if entry is None:
            return self.render('error')
        tags = EntryTag.all_entry_tags().fetch(constants.DEFAULT_PAGE_SIZE)
        self.render('entry-form', {'id' : id, 'entry' : entry, 'tags' : tags}) 
             
    def create(self, kwds = None):
        logging.debug('begin to save entry...')
        entry = Entry()
        entry.status  = 'DRAFT'
        self._bind_entry(self.request, entry)
        entry.create()
        memcache.delete(constants.FIRST_PAGE_CACHE_KEY)
        self.render_json({'msg':'OK'})
           
    @admin_required     
    def update(self, id, kwds = None):
        entry = Entry.get_by_id(long(id))
        if entry is None:
            self.render_json({'msg' : 'ERROR'})
        else:
            f = self.request.get('from')
            o = self.request.get('operation')
            if f == 'admin' and o == 'approve':
                entry.approve()
                self.render_json({'msg' : 'OK'})
            else:
                self._bind_entry(self.request, entry) 
                entry.update()
                memcache.delete(constants.FIRST_PAGE_CACHE_KEY)
                self.render_json({'msg' : 'OK'})
                
    @admin_required        
    def destroy(self, id, kwds = None):
        entry = Entry.get_by_id(long(id))
        if entry is None:
            self.render_json({'msg' : 'ERROR'})
        else:
            entry.remove()
            memcache.delete(constants.FIRST_PAGE_CACHE_KEY)
            return self.render_json({'msg' : 'OK'})
    
    def _bind_entry(self, request, entry):
        #TODO: 还需要考虑验证问题
        #XXX: 解决中文问题的改进
        validate_result = {}
        entry.title       = request.get('title')
        logging.debug('entry title: ' + entry.title)
        entry.text        = request.get('text')
        entry.tags_as_str = request.get('tags')
        entry.author      = self.get_account().email

class EntryCommentController(RestController):
    def index(self, kwds = None):
        pass
    def show(self, id, kwds = None):
        pass
    def edit_new(self, kwds = None):
        pass
    def edit(self, id, kwds = None):
        pass
    def create(self, kwds = None):
        pass
    
        
class ResourceController(RestController):
    def index(self, kwds = None):
        logging.debug('entering index method')
        if kwds == None or len(kwds) == 0:
            f = self.request.get('from')
            if f == 'admin':   #来自管理界面
                resources = models.Res.all()
                res_array = [{ 'id' : res.key().id(),
                               'url' : res.link,
                               'status' : res.status} for res in resources]
                return self.render_json({'msg' : 'OK', 'resources' : res_array} )
            else:
                page_number = self.request.get('page')
                if page_number is None or page_number == '':
                    page_number = 1
                page = Res.get_page(page_number)
                tags = ResTag.all_res_tags().fetch(constants.DEFAULT_PAGE_SIZE)
                self.render('res', {'page' : page, 'tags' : tags})
        else:
            tag_kn = kwds['tag_key_name']
            tag = ResTag.get_by_key_name(tag_kn)
            resources = {}
            if tag is not None:
                resources = tag.resources;
            self.render("frag/res-for-tag", {'resources' : resources, 'tag' : tag})
        
    def show(self, id, kwds = None):
        pass
    
    def edit_new(self, kwds = None):
        self.render('res-form')
        
    def edit(self, id, kwds = None):
        self.render('res-form', {'id' : id})
    
    def create(self, kwds = None):
        res = Res()
        self._bind_res(self.request, res)
        res.status = 'DRAFT'
        res.create()
        self.render_json({'msg' : 'OK'})
        
    @admin_required    
    def update(self, id, kwds = None):
        res = Res.get_by_id(long(id))
        if res is None:
            self.render_json({'msg' : 'ERROR'})
        else:
            f = self.request.get('from')
            o = self.request.get('operation')
            if f == 'admin' and o == 'approve':
                res.approve()
                self.render_json({'msg' : 'OK'})
            
    @admin_required
    def destroy(self, id, kwds = None):
        res = Res.get_by_id(long(id))
        if res is None:
            self.render_json({'msg' : 'ERROR'})
        else:
            res.remove()
            self.render_json({'msg' : 'OK'})
            
    def _bind_res(self, request, res):
        res.link = request.get('url')
        res.feed_link = request.get('feedurl')
        res.description = request.get('description')
        res.tags_as_str = request.get('tags')
        res.author = self.get_account().email

class EntryTagController(RestController):
    def index(self, kwds = None):
        tags = EntryTag.all_entry_tags().fetch(constants.DEFAULT_PAGE_SIZE)
        self.render('frag/entry-tags', {'tags' : tags})

class ResTagController(RestController):
    def index(self, kwds = None):
        tags = ResTag.all_res_tags().fetch(constants.DEFAULT_PAGE_SIZE)
        self.render('frag/res-tags', {'tags' : tags})

class ProjectController(RestController):
    def index(self, kwds = None):
        self.render('projects')

class AdminController(RestController):
    @admin_required
    def index(self, kwds = None):
        self.render('admin')

class ErrorController(RestController):
    def index(self, kwds = None):
        self.render('error')        