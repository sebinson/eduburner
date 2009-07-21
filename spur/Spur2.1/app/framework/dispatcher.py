#coding=utf-8

import sys, os  
import logging
import wsgiref.handlers

from google.appengine.ext import webapp

from app.controllers import *
import mapper
        
class RestHandler(webapp.RequestHandler):
   
    def __init__(self):
        self.route_info = None
        self.controller = None
    
    def initialize(self, request, response):
        webapp.RequestHandler.initialize(self, request, response)
        self.route_info = RestHandler.get_route_info(request, response)
          
        if self.route_info is not None:
            self.controller = self.route_info['controller'](self, self.route_info['format'])
            self.controller.initialize()
        else:
            self.route_info = {'action' : 'index', 'params':{}}
            self.controller = ErrorController(self)
               
        self.controller.initialize()
          
    @staticmethod
    def get_route_info(request, response):
      m = mapper.Mapper()
      route_info = m.resolve(request.path)
      return route_info

    def get(self):
      ri = self.route_info
      c = self.controller
      params = ri['params']
      action = ri['action']
      if c is not None and action is not None:
          if action == 'show' or action == 'edit':
              id = params['id']
              getattr(c, action)(id, params)
          else:
              getattr(c, action)(ri['params'])

    def post(self):
      ri = self.route_info
      c = self.controller
      params = ri['params']
      if c is not None:
          m = self.request.get('_method')
          if m is None or m == '':
              c.create(params)
          elif m == 'PUT':
              id = params['id']
              c.update(id, params)
          elif m == 'DELETE':
              id = params['id']
              c.destroy(id, params)
          else:
              self.error(404)       
    
    def put(self):
      ri = self.route_info
      c = self.controller
      params = ri['params'] 
      if c != None:
        id = params['id']
        c.update(id, params)
      else:
        self.error(404)
            
    def delete(self):
      ri = self.route_info
      c = self.controller
      params = ri['params'] 
      if c != None:
        id = params['id']
        c.destroy(id, params)
      else:
        self.error(404)