
import json
from os import listdir
from os.path import isfile, join
from typing import Dict
import matplotlib.pyplot as plt
import numpy as np
import re


class TrayectoryExporter:
    def __init__(self):
        print("Reader ready")


    @staticmethod
    def exportTrayectories(path , regex = '.*'):
        folders = [ folder for folder in listdir(path) if re.match(regex  , folder )]
        print(folders)
        
        for foldername in folders:
            run = dict()
            with open(f'{path}/{foldername}/static.json') as json_file:
                data = json.load(json_file)
                run['width'] = data['space_width']
                
            with open(f'{path}/{foldername}/snapshots.json') as json_file:
                data = json.load(json_file)
                run['p'] = [ x['p'][0] for x in data['info'] ]
                run['t'] = [ x['t'] for x in data['info'] ]
            outfile = open(f'../trayectory-exports/{foldername}_trayectory.json' , "w")
            json.dump(run , outfile)
            outfile.close()