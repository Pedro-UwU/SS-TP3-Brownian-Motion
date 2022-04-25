
import json
from os import listdir
from os.path import isfile, join
from typing import Dict
import matplotlib.pyplot as plt
from speedModules import SpeedModules
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
                vel_modules_initial = []
                for vel in data['info'][0][1:]:
                    vel_modules_initial.append(np.sqrt(vel[0]**2 + vel[1]**2))
                run['starting_module'] = vel_modules_initial
                run['times'] = [ t['t']-data['info'][i]['t'] for i , t in enumerate(data['info'][1:]) ]
                run['avg_time'] = np.average(run['times'])
                run['info'] = data['info']
                runs.append(run)       
        return runs

    @staticmethod
    def parse_ej2(path , regex = '.*'):
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

                totalTime = data['info'][-1]['t']
                vel_modules = [] 
                hist , x = SpeedModules.initialize_hist(0 , 5 , 25)
                for snapshot in data['info']:
                    if( 2/3 * totalTime <= snapshot['t']):
                        for vel in snapshot['v'][1:]:
                            SpeedModules.put_hist(np.sqrt(vel[0]**2 + vel[1]**2) , hist , x)
                run['modules_hist'] = hist
                run['modules_x'] =x
                vel_modules_initial = []
                hist2 , x2 = SpeedModules.initialize_hist(0 , 5 , 25)
                for vel in data['info'][0]['v'][1:]:
                    SpeedModules.put_hist(np.sqrt(vel[0]**2 + vel[1]**2) , hist2 , x2)
                run['starting_module_hist'] = hist2
                run['starting_module_x'] = hist2
                runs.append(run)       
        return runs
