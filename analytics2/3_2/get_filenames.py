
import json
from os import listdir
from os.path import isfile, join
from typing import Dict
import matplotlib.pyplot as plt
import numpy as np
import re

path = '../../results'
regex = r'Sim_130'
folders = [ folder for folder in listdir(path) if re.match(regex  , folder )]
for x in folders:
    print(f'{x} ' , end='')