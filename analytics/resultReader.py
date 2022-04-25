
import json
from os import listdir
from os.path import isfile, join
from typing import Dict
import matplotlib.pyplot as plt
import numpy as np
import re


class ResultReader:
    def __init__(self):
        print("Reader ready")


    @staticmethod
    def parse(path , regex = '.*'):
        folders = [ folder for folder in listdir(path) if re.match(regex  , folder )]
        #folders = listdir(path)
        print(folders)
        runs = []
        
        for foldername in folders:
            run = dict()
            with open(f'{path}/{foldername}/static.json') as json_file:
                data = json.load(json_file)
                run['total'] = data['total_particles']
                run['width'] = data['space_width']
                #print(run)
                
            with open(f'{path}/{foldername}/snapshots.json') as json_file:

                data = json.load(json_file)

            # print("len: " , len(data['snapshots']))
                collitions = len(data['info']) -1
                if( data['info'][-1]['c'] == 3):
                    collitions= collitions - 1
                totalTime = data['info'][-1]['t']
                run['frecuency'] = collitions / totalTime
                vel_modules = [] 
                for snapshot in data['info']:
                    if( 2/3 * totalTime <= snapshot['t']):
                        speedList = []
                        for vel in snapshot['v'][1:]:
                            vel_modules.append(np.sqrt(vel[0]**2 + vel[1]**2))
                run['modules'] = vel_modules
                runs.append(run)       
        return runs
    
