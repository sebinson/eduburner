from google.appengine.api import urlfetch
from google.appengine.api import apiproxy_stub_map
from google.appengine.api import urlfetch_stub

_GREADER_SHARED_FEED_URL = 'http://www.google.com/reader/public/atom/user%2F10524518607750129215%2Fstate%2Fcom.google%2Fbroadcast'

class FeedFetcher(object):
    def __init__(self):
        self.feed_url = _GREADER_SHARED_FEED_URL
    
    def fetch_feed(self):
        fetch_result = urlfetch.fetch(self.feed_url)
        content = None
        if fetch_result.status_code == 200:
            content = fetch_result.content
        print content
        return content

def _example():
    fetcher = FeedFetcher()
    fetcher.fetch_feed()

if __name__ == "__main__":
    apiproxy_stub_map.apiproxy = apiproxy_stub_map.APIProxyStubMap() 
    apiproxy_stub_map.apiproxy.RegisterStub('urlfetch', urlfetch_stub.URLFetchServiceStub()) 
    _example()
    