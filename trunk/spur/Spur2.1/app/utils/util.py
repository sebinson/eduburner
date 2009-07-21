#coding=utf-8

import logging
import htmllib
import urllib
import sys
import datetime
import re

from google.appengine.api import memcache

############################################## time utils
#GAE中的中国时间解决方案
import datetime

def parse_time(utc_string):
    return datetime.datetime.strptime (utc_string, "%Y-%m-%dT%H:%M:%SZ")

def get_local_time(value):
    return value.replace(tzinfo=UtcTzinfo()).astimezone(CctTzinfo())

class UtcTzinfo(datetime.tzinfo):
  def utcoffset(self, dt): return datetime.timedelta(0)
  def dst(self, dt): return datetime.timedelta(0)
  def tzname(self, dt): return 'UTC'
  def olsen_name(self): return 'UTC'

class CctTzinfo(datetime.tzinfo):
    def utcoffset(self, dt): return datetime.timedelta(hours=8)
    def dst(self, dt): return datetime.timedelta(0)
    def tzname(self, dt): return 'CCT'
    def olsen_name(self): return 'Asia/Shanghai'

##############################################################

def get_display_date(then, now=None):
    def agohence(n, what, divisor=None):
        if divisor: n = n // divisor
        out = str(abs(n)) + '' + what       # '2 day'
        out += ''                           # '2 days '
        if n < 0:
            out += '以后'
        else:
            out += '以前'
        return out                           # '2 days ago'
    oneday = 24 * 60 * 60

    if not now: now = datetime.datetime.utcnow()
    if type(now).__name__ == "DateTime":
        now = datetime.datetime.fromtimestamp(now)
    if type(then).__name__ == "DateTime":
        then = datetime.datetime.fromtimestamp(then)
    elif type(then).__name__ == "date":
        then = datetime.datetime(then.year, then.month, then.day)
    delta = now - then
    deltaseconds = int(delta.days * oneday + delta.seconds + delta.microseconds * 1e-06)
    deltadays = abs(deltaseconds) // oneday
    if deltaseconds < 0: deltadays *= -1 # fix for oddity of floor

    if deltadays:
        if abs(deltadays) < 4:
            return agohence(deltadays, '天')

        out = then.strftime('%m月%d日') # e.g. 'June 13'
        month = then.month
        day = then.day
        if then.year != now.year or deltadays < 0:
            out = '%s年' + out % then.year
        if out[0] == '0':
            out = out[1:]
        return out

    if int(deltaseconds):
        if abs(deltaseconds) > (60 * 60):
            return agohence(deltaseconds, '小时', 60 * 60)
        elif abs(deltaseconds) > 60:
            return agohence(deltaseconds, '分', 60)
        else:
            return agohence(deltaseconds, '秒')

    deltamicroseconds = delta.microseconds
    if delta.days: deltamicroseconds = int(delta.microseconds - 1e6) # datetime oddity
    if abs(deltamicroseconds) > 1000:
        return agohence(deltamicroseconds, '微秒', 1000)

    return agohence(deltamicroseconds, '微秒')


def extract_domain_name(url, remove_www=False):
    if url.count('/') >= 2:
        domain = url.split('/')[2]
    else:
        domain = url.strip('/')
    # Remove user:password@
    if domain.count('@'):
        domain = domain.split('@')[1]
    # Remove port (e.g. :8000)
    if domain.count(':'):
        domain = domain.split(':')[0]
    # Remove www. if requested
    while domain.startswith('www.') and remove_www:
        domain = domain[4:]
    return domain

    
################################################### String utils

def is_cn_character(uchar):
    if uchar >= u'\u4e00' and uchar <= u'\u9fa5':
        return True
    else:
        return False

rx = re.compile(u"[a-zA-Z0-9_\u0392-\u03c9]+|[\u4E00-\u9FFF\u3400-\u4dbf\uf900-\ufaff\u3040-\u309f\uac00-\ud7af]+", re.UNICODE)
def cal_words(s, encoding='utf-8'):
    result = []
    if not isinstance(s, unicode):
        s = unicode(s, encoding, 'ignore')
    words = rx.findall(s)
    return len(words)

#bug http://code.google.com/p/googleappengine/issues/detail?id=457
def decode_url(url):
    return urllib.unquote(url).decode('utf-8')

def quote_url(string, encoding):
    """A Unicode handling version of urllib.quote."""
    if encoding:
        if isinstance(string, unicode):
            s = string.encode(encoding)
        elif isinstance(string, str):
            # assume the encoding is already correct
            s = string
        else:
            s = unicode(string).encode(encoding)
    else:
        s = str(string)
    return urllib.quote(s, '/')


def encode_str(string, encoding='utf-8'):
    if encoding:
        if isinstance(string, unicode):
            s = string.encode(encoding)
        elif isinstance(string, str):
            # assume the encoding is already correct
            s = string
        else:
            s = unicode(string).encode(encoding)
    else:
        s = str(string)
    return s

#def extract_param(request, param):
#    val = request.get(param)
#    if val == None or val == '':
#        return None
#    return unicode(val, 'utf-8')

def get_summary(src, max_length = 200):

    sub_len = max_length if max_length < len(src) else len(src)
    
    return src[0:sub_len] + '......'

def test():
    #print is_cn_character('a')
    url = ['a', 'b']
    print len(url)

if __name__ == "__main__":
    test()