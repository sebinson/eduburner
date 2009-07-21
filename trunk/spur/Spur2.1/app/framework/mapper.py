#coding=utf-8

import re
import urllib
import logging

from app.utils.functional import *
from app.controllers import *

import config

from route import *

class Mapper(object):
    class __impl:
        def __init__(self):
            self.route_set = []

        def connect(self, pattern, **kwds):
            if pattern.endswith('/'):
                pattern = pattern[:-1]
            print kwds
            #如果有action，则不采用restful url，直接使用action
            if 'action' in kwds:
                self._add_route(pattern, controller=kwds.get('controller'), action=kwds.action)
            else:
                self._add_route(pattern, controller=kwds.get('controller'), action='index')
                self._add_route(pattern + '/new', controller=kwds.get('controller'), action='edit_new')
                self._add_route(pattern + '/{id}', controller=kwds.get('controller'), action='show')
                self._add_route(pattern + '/{id}/edit', controller=kwds.get('controller'), action='edit')
        
        @memoize('route:url=%s-%s')        
        def resolve(self, url):
            if url == '/':
                return { 'params' : {}, 
                         'controller' : IndexController,
                         'format': config.DEFAUT_RESOURCE_FORMAT, 
                         'action' : 'index' }
            """ Resolve the url to the correct mapping """
            for route in self.route_set:
                match_result = route.match(url)
                if match_result is not False:
                    return match_result
            return None
        
        def _add_route(self, pattern, controller, action):
            route = Route(pattern, controller=controller, action=action)
            route.makeregexp()
            if route not in self.route_set:
                self.route_set.append(route)

    __instance = None

    def __init__(self):
        if Mapper.__instance is None:
            Mapper.__instance = Mapper.__impl()
        self.__dict__['_Mapper__instance'] = Mapper.__instance

    def __getattr__(self, attr):
        return getattr(self.__instance, attr)

    def __setattr__(self, attr, value):
        return setattr(self.__instance, attr, value)