#coding=utf-8

import re
import urllib
import logging

from app.utils import util
from app.utils.functional import *
from app.controllers import *

import config

"""
 Route codes taken from python routes
"""
class Route(object):
   
    def __init__(self, routepath, **kargs):
        self.routepath = routepath
        self.action = kargs.get('action', 'index')  #action name
        self.controller = kargs.get('controller', None)
        self.params = {}
        
        self.reqs = kargs.get('requirements', {})
        self.encoding = kargs.pop('_encoding', 'utf-8')
        self.decode_errors = 'replace'
        
        self.done_chars = ('/', ',', ';', '.', '#')
        
        if routepath.startswith('/'):
            routepath = routepath[1:]
        
        self.routelist = routelist = self._pathkeys(routepath)
        
        routekeys = frozenset([key['name'] for key in routelist if isinstance(key, dict)])
    
    def makeregexp(self):
        reg = self.buildnextreg(self.routelist)[0]
        if not reg:
            reg = '/'
        reg = reg + '(/)?' + '$'
        if not reg.startswith('/'):
            reg = '/' + reg
        reg = '^' + reg
        self.regexp = reg
        self.regmatch = re.compile(reg)
    
    def match(self, url):
        url_split = url.split('.')
        if len(url_split) == 1:
            format = config.DEFAUT_RESOURCE_FORMAT
        elif len(url_split) == 2:
            url = url_split[0]
            format = url_split[1];
        else:
            return False
        match = self.regmatch.match(url)
        if not match:
            return False
        params = {}
        matchdict = match.groupdict()
        for key, val in matchdict.iteritems():
            try:
                val = val and util.decode_url(val)
            except UnicodeDecodeError:
                return False
            params[key] = val
        
        return { 'controller': self.controller,
                 'format': format,
                 'action': self.action,
                 'params': params }
    
    def _pathkeys(self, routepath):
        """Utility function to walk the route, and pull out the valid
        dynamic/wildcard keys."""
        collecting = False
        current = ''
        done_on = ''
        var_type = ''
        just_started = False
        routelist = []
        for char in routepath:
            if char in [':', '*', '{'] and not collecting \
               or char in ['{'] and not collecting:
                just_started = True
                collecting = True
                var_type = char
                if char == '{':
                    done_on = '}'
                    just_started = False
                if len(current) > 0:
                    routelist.append(current)
                    current = ''
            elif collecting and just_started:
                just_started = False
                if char == '(':
                    done_on = ')'
                else:
                    current = char
                    done_on = self.done_chars + ('-',)
            elif collecting and char not in done_on:
                current += char
            elif collecting:
                collecting = False
                if var_type == '{':
                    opts = current.split(':')
                    if len(opts) > 1:
                        current = opts[0]
                        self.reqs[current] = opts[1]
                    var_type = ':'
                routelist.append(dict(type=var_type, name=current))
                if char in self.done_chars:
                    routelist.append(char)
                done_on = var_type = current = ''
            else:
                current += char
        if collecting:
            routelist.append(dict(type=var_type, name=current))
        elif current:
            routelist.append(current)
        return routelist
        
    def buildnextreg(self, path):
        if path:
            part = path[0]
        else:
            part = ''
        reg = ''
        
        (rest, noreqs, allblank) = ('', True, True)
        if len(path[1:]) > 0:
            self.prior = part
            (rest, noreqs, allblank) = self.buildnextreg(path[1:])
        
        if isinstance(part, dict) and part['type'] == ':':
            var = part['name']
            partreg = ''
            
            # First we plug in the proper part matcher
            if self.reqs.has_key(var):
                partreg = '(?P<' + var + '>' + self.reqs[var] + ')'
            elif self.prior in ['/', '#']:
                partreg = '(?P<' + var + '>[^' + self.prior + ']+?)'
            else:
                if not rest:
                    partreg = '(?P<' + var + '>[^%s]+?)' % '/'
                else:
                    end = ''.join(self.done_chars)
                    rem = rest
                    if rem[0] == '\\' and len(rem) > 1:
                        rem = rem[1]
                    elif rem.startswith('(\\') and len(rem) > 2:
                        rem = rem[2]
                    else:
                        rem = end
                    rem = frozenset(rem) | frozenset(['/'])
                    partreg = '(?P<' + var + '>[^%s]+?)' % ''.join(rem)
            
            if self.reqs.has_key(var):
                noreqs = False
            
            allblank = False
            noreqs = False
            
            if noreqs:
                if self.reqs.has_key(var):
                    allblank = False
                    reg = partreg + rest
                else:
                    allblank = False
                    reg = partreg + rest
            # In this case, we have something dangling that might need to be
            # matched
            else:
                reg = partreg + rest
        elif isinstance(part, dict) and part['type'] == '*':
            var = part['name']
            if noreqs:
                if self.defaults.has_key(var):
                    reg = '(?P<' + var + '>.*)' + rest
                else:
                    reg = '(?P<' + var + '>.*)' + rest
                    allblank = False
                    noreqs = False
            else:
                allblank = False
                noreqs = False
                reg = '(?P<' + var + '>.*)' + rest
        elif part and part[-1] in self.done_chars:
            if allblank:
                reg = re.escape(part[:-1]) + '(' + re.escape(part[-1]) + rest
                reg += ')?'
            else:
                allblank = False
                reg = re.escape(part) + rest
        else:
            noreqs = False
            allblank = False
            reg = re.escape(part) + rest
        
        return (reg, noreqs, allblank)