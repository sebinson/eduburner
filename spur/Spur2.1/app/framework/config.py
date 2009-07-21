#coding=utf-8

import logging

DEBUG = True
LOGGING_LEVEL = logging.DEBUG
DEFAUT_RESOURCE_FORMAT = 'html'

class Config:
    
    class __impl:
        def __init__(self):
            self.template_dir = ''

    __instance = None

    def __init__(self):
        if Config.__instance is None:
            Config.__instance = Config.__impl()

        self.__dict__['_Config__instance'] = Config.__instance

    def __getattr__(self, attr):
        return getattr(self.__instance, attr)

    def __setattr__(self, attr, value):
        return setattr(self.__instance, attr, value)
