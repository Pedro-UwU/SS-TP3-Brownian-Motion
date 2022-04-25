
from cProfile import label
import statistics
from diffussion import Difussion
from speedModules import SpeedModules
from resultReader import ResultReader
from scipy.interpolate import UnivariateSpline
import matplotlib.pyplot as plt
import numpy as np
import json

def punto_2( data):
    SpeedModules.punto_2(data , 25)

def main():
    data =  ResultReader.parse('../results' , 'Sim')
   # dcm_array , times = Difussion.get_dcm(data[0]['info'] , 0)
   # Difussion.graphicate_dcm(dcm_array , times)
   
if __name__ == "__main__":
    main()