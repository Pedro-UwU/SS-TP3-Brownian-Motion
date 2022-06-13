
import json
from os import listdir
from os.path import isfile, join
from typing import Dict
import matplotlib.pyplot as plt
import numpy as np
import re


class TimeExporter:
    def __init__(self):
        print("Reader ready")


    @staticmethod
    def exportTimes(path , regex = '.*'):
        folders = [ folder for folder in listdir(path) if re.match(regex  , folder )]
        print(folders)
        
        for foldername in folders:
            print(f'in folder {foldername}')
            run = dict()
            # with open(f'{path}/{foldername}/static.json') as json_file:
            #     data = json.load(json_file)
            #     run['total'] = data['total_particles']
            #     run['width'] = data['space_width']
                
            with open(f'{path}/{foldername}/snapshots.json') as json_file:
                data = json.load(json_file)
                collitions = len(data['info']) -1
                if( data['info'][-1]['c'] == 3):
                    collitions= collitions - 1
                totalTime = data['info'][-1]['t']
                run['frecuency'] = collitions / totalTime
                run['times'] = [ t['t']-data['info'][i]['t'] for i , t in enumerate(data['info'][1:]) ]
            
            outfile = open(f'../dt-exports/{foldername}_times.json' , "w")
            json.dump(run , outfile)
            outfile.close()