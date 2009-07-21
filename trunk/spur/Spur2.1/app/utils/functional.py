#coding=utf-8

from google.appengine.api import memcache
import logging

def memoize(keyformat, time=600000):
    """Decorator to memoize functions using memcache."""
    def decorator(fxn):
        def wrapper(*args, **kwargs):
            key = keyformat % args[0:keyformat.count('%')]
            data = memcache.get(key)
            if data is not None:
                return data
            data = fxn(*args, **kwargs)
            memcache.set(key, data, time)
            return data
        return wrapper
    return decorator