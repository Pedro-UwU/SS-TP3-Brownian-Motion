import json
from os import listdir
from os.path import isfile, join
import matplotlib.pyplot as plt
import numpy as np
import re

class JsonReader:
    def __init__(self):
        print("Reader ready")


    @staticmethod
    def readList(path , regex = '.*'):
        folders = [ folder for folder in listdir(path) if re.match(regex  , folder )]
        print(folders)
        jsonList = []
        for foldername in folders:
            with open(f'{path}/{foldername}') as json_file:
                jsonList.append(json.load(json_file))    

        return jsonList