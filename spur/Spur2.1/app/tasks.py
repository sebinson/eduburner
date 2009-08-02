import re
import datetime
import logging

from google.appengine.api import urlfetch
from google.appengine.api import memcache

from lib.friendfeed import *
from models import *

FF_USERNAME = 'eduspur'
FF_REMOTE_KEY = 'lean752bolas'
class FriendFeedTask(object):
    @staticmethod
    def run():
        logging.debug('begin to run friend feed task')
        session = FriendFeed(FF_USERNAME, FF_REMOTE_KEY)
        feed = session.fetch_home_feed()
        FriendFeedTask.save_feed(feed)
    
    @staticmethod
    def save_feed(feed):
        logging.debug('begin to save feed')
        entries = feed['entries']
        all_entries = FeedEntry.get_all()
        for entry in entries:
            entry_id = entry['id']
            key_name = 'kn' + entry_id
            if len(filter(lambda entry: entry.key().name() == key_name, all_entries)) == 1:
                continue
            published = entry['published']
            link = entry['link']
            updated = entry['updated']
            title = entry['title']
            service = entry['service']
            feedService = Service.get_service(service['id'], service['profileUrl'], service['entryType'], service['name'])
            feedEntry = FeedEntry(key_name = key_name, entry_id = entry_id, published = published, updated = updated, link = link, title = title, service=feedService)
            feedEntry.save()
            memcache.delete(constants.FEED_FIRST_PAGE_CACHE_KEY)