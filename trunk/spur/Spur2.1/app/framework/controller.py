#coding=utf-8

import sys, os
import logging

try:
  # When deployed
  from google.appengine.runtime import DeadlineExceededError
except ImportError:
  # In the development server
  from google.appengine.runtime.apiproxy_errors import DeadlineExceededError

from google.appengine.api import users
from google.appengine.ext import db
from google.appengine.api import memcache
from google.appengine.ext.webapp import template

from django.utils import simplejson

import config

from app.models import *

class BaseController(object):
    def __init__(self, handler, format = config.DEFAUT_RESOURCE_FORMAT):
        self.handler = handler
        self.request = handler.request
        self.response = handler.response
        self.format = format
    
    # cache here?
    def get_response_content(self, tpl, payload={}):
        
        payload['user'] = self.get_account()
        payload['sign_in_url'] = users.create_login_url(self.request.url)
        payload['sign_out_url'] = users.create_logout_url(self.request.url) 
        
        cfg = config.Config()
        path = os.path.join(cfg.template_dir, tpl + '.' + self.format)
        content = template.render(path, payload, debug=config.DEBUG)
        
        return content
    
    def render(self, tpl, payload={}): 
        content = self.get_response_content(tpl, payload)
        self.response.out.write(content)
    
    def render_content(self, content):
        self.response.out.write(content)
        
    def render_json(self, json_res):
        content = simplejson.dumps(json_res)
        return self.response.out.write(content)
    
    def get_account(self):
        anonymous_user_email = "anonymous@gmail.com"
        user = users.get_current_user()
        if user is None:
            user = users.User(anonymous_user_email)
        account = memcache.get(user.email())
        if account is None:
            account = Account.get_account_for_user(user)
            memcache.set(user.email(), account)
        return account
    
    def initialize(self):
        """"""
    #GET      /movie      Movie.index 
    def index(self, params=None):
        """"""
    #GET  /movie/Thrillers  Movie.show  id="Thrillers" 
    def show(self, res_id, params=None):
        """"""        
    #POST      /movie      Movie.create 
    def create(self, params=None):
        """"""
    #PUT      /movie/Thrillers      Movie.update      id="Thrillers" 
    def update(self, res_id, params=None):
        """"""
    #GET  /movie/Thrillers/edit  Movie.edit id="Thrillers" 
    def edit(self, res_id, params=None):
        """"""
    #GET  /movie/new      Movie.editNew 
    def edit_new(self, params=None):
        """"""
    #DELETE      /movie/Thrillers      Movie.destroy      id="Thrillers" 
    def destroy(self, res_id, params=None):
        """"""