import base64
import datetime
import time
import urllib
import urllib2
import logging

import logging

from django.utils import simplejson

parse_json = lambda s: simplejson.loads(s.decode("utf-8"))

class FriendFeed(object):
    def __init__(self, auth_nickname=None, auth_key=None):
        """Creates a new FriendFeed session for the given user.

        The credentials are optional for some operations, but required for
        private feeds and all operations that write data, like publish_link.
        """
        self.auth_nickname = auth_nickname
        self.auth_key = auth_key

    def fetch_public_feed(self, **kwargs):
        """Returns the public feed with everyone's public entries.

        Authentication is not required.
        """
        return self._fetch_feed("/api/feed/public", **kwargs)

    def fetch_user_feed(self, nickname, **kwargs):
        """Returns the entries shared by the user with the given nickname.

        Authentication is required if the user's feed is not public.
        """
        return self._fetch_feed(
            "/api/feed/user/" + urllib.quote_plus(nickname), **kwargs)
    def fetch_user_comments_feed(self, nickname, **kwargs):
        """Returns the entries the given user has commented on."""
        return self._fetch_feed(
            "/api/feed/user/" + urllib.quote_plus(nickname) + "/comments",
            **kwargs)

    def fetch_user_likes_feed(self, nickname, **kwargs):
        """Returns the entries the given user has "liked"."""
        return self._fetch_feed(
            "/api/feed/user/" + urllib.quote_plus(nickname) + "/likes",
            **kwargs)

    def fetch_user_discussion_feed(self, nickname, **kwargs):
        """Returns the entries the given user has commented on or "liked"."""
        return self._fetch_feed(
            "/api/feed/user/" + urllib.quote_plus(nickname) + "/discussion",
            **kwargs)

    def fetch_multi_user_feed(self, nicknames, **kwargs):
        """Returns a merged feed with all of the given users' entries.

        Authentication is required if any one of the users' feeds is not
        public.
        """
        return self._fetch_feed("/api/feed/user", nickname=",".join(nicknames),
                                **kwargs)

    def fetch_home_feed(self, **kwargs):
        """Returns the entries the authenticated user sees on their home page.

        Authentication is always required.
        """
        return self._fetch_feed("/api/feed/home", **kwargs)

    def search(self, q, **kwargs):
        """Searches over entries in FriendFeed.

        If the request is authenticated, the default scope is over all of the
        entries in the authenticated user's Friends Feed. If the request is
        not authenticated, the default scope is over all public entries.

        The query syntax is the same syntax as
        http://friendfeed.com/advancedsearch
        """
        kwargs["q"] = q
        return self._fetch_feed("/api/feed/search", **kwargs)
    
    def _fetch_feed(self, uri, post_args=None, **kwargs):
        """Publishes to the given URI and parses the returned JSON feed."""
        # Parse all the dates in the result JSON
        result = self._fetch(uri, post_args, **kwargs)
        rfc3339_date = "%Y-%m-%dT%H:%M:%SZ"
        date_properties = frozenset(("updated", "published"))
        for entry in result.get("entries", []):
            entry["updated"] = self._parse_date(entry["updated"])
            entry["published"] = self._parse_date(entry["published"])
            for comment in entry.get("comments", []):
                comment["date"] = self._parse_date(comment["date"])
            for like in entry.get("likes", []):
                like["date"] = self._parse_date(like["date"])
        return result

    def _fetch(self, uri, post_args, **url_args):
        url_args["format"] = "json"
        args = urllib.urlencode(url_args)
        url = "http://friendfeed.com" + uri + "?" + args
        if post_args is not None:
            request = urllib2.Request(url, urllib.urlencode(post_args))
        else:
            request = urllib2.Request(url)
        if self.auth_nickname and self.auth_key:
            pair = "%s:%s" % (self.auth_nickname, self.auth_key)
            token = base64.b64encode(pair)
            request.add_header("Authorization", "Basic %s" % token)
        stream = urllib2.urlopen(request)
        data = stream.read()
        stream.close()
        return parse_json(data)

    def _parse_date(self, date_str):
        rfc3339_date = "%Y-%m-%dT%H:%M:%SZ"
        return datetime.datetime(*time.strptime(date_str, rfc3339_date)[:6])

def _unicodify(json):
    """Makes all strings in the given JSON-like structure unicode."""
    if isinstance(json, str):
        return json.decode("utf-8")
    elif isinstance(json, dict):
        for name in json:
            json[name] = _unicodify(json[name])
    elif isinstance(json, list):
        for part in json:
            _unicodify(part)
    return json

def _example():
    # Fill in a nickname and a valid remote key below for authenticated
    # actions like posting an entry and reading a protected feed
    # session = FriendFeed(auth_nickname=nickname, auth_key=remote_key)
    session = FriendFeed('eduspur', 'lean752bolas')

    #feed = session.fetch_public_feed()
    # feed = session.fetch_user_feed("bret")
    # feed = session.fetch_user_feed("paul", service="twitter")
    # feed = session.fetch_user_discussion_feed("bret")
    # feed = session.fetch_multi_user_feed(["bret", "paul", "jim"])
    # feed = session.search("who:bret friendfeed")
    #for entry in feed["entries"]:
    #    print entry["published"].strftime("%m/%d/%Y"), entry["title"]

    if session.auth_nickname and session.auth_key:
        # The feed that the authenticated user would see on their home page
        feed = session.fetch_home_feed()
        
        for entry in feed["entries"]:
            print entry["published"].strftime("%m/%d/%Y"), entry["title"]
        # Post a message on this user's feed
        #entry = session.publish_message("Testing the FriendFeed API")
        #print "Posted new message at http://friendfeed.com/e/%s" % entry["id"]

        # Post a link on this user's feed
        #entry = session.publish_link(title="Testing the FriendFeed API",
        #                             link="http://friendfeed.com/")
        #print "Posted new link at http://friendfeed.com/e/%s" % entry["id"]

        # Post a link with two thumbnails on this user's feed
        #entry = session.publish_link(
        #    title="Testing the FriendFeed API",
        #    link="http://friendfeed.com/",
        #    image_urls=[
        #        "http://friendfeed.com/static/images/jim-superman.jpg",
        #        "http://friendfeed.com/static/images/logo.png",
        #    ],
        #)
        #print "Posted images at http://friendfeed.com/e/%s" % entry["id"]
        

if __name__ == "__main__":
    _example()
