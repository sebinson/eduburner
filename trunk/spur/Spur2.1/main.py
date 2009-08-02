#coding=utf-8

import logging
import wsgiref.handlers

from google.appengine.ext import webapp

from app.framework import config
from app.framework import dispatcher
from app.framework import mapper

from app.controllers import *


def initMapper():
    m = mapper.Mapper()
    m.connect('/entries/',                       controller = EntryController)
    m.connect('/tags/{tag_key_name}/entries',    controller = EntryController)
    m.connect('/resources/',                     controller = ResourceController)
    m.connect('/tags/{tag_key_name}/resources',  controller = ResourceController)
    m.connect('/entrytags/',                     controller = EntryTagController)
    m.connect('/resourcetags/',                  controller = ResTagController)
    m.connect('/projects/',                      controller = ProjectController)
    m.connect('/admin/',                         controller = AdminController)
    m.connect('/friendfeed/',                    controller = FriendFeedController)
    m.connect('/reader/',                        controller = GReaderSharedController)
        
def main():
    logging.getLogger().setLevel(config.LOGGING_LEVEL)
    
    cfg = config.Config()
    cfg.template_dir = os.path.join(os.path.dirname(__file__), 'templates')

    initMapper()
    
    application = webapp.WSGIApplication([(r'.*', dispatcher.RestHandler)], debug=config.DEBUG)
    wsgiref.handlers.CGIHandler().run(application)

if __name__ == '__main__':
    main()
