
from cProfile import label
import statistics
from temperature import Temperature
from diffussion import Difussion
from speedModules import SpeedModules
from resultReader import ResultReader
from scipy.interpolate import UnivariateSpline
import matplotlib.pyplot as plt
import numpy as np
import json

def punto_2():
    SpeedModules.punto_2(ResultReader.parse_ej2('../results' , 'Sim_135_[0-9]+') , 25)

def main():
    punto_2()
    #data =  ResultReader.parse('../results' , 'Sim')
   # dcm_array , times = Difussion.get_dcm(data[0]['info'] , 0)
   # Difussion.graphicate_dcm(dcm_array , times)
   
if __name__ == "__main__":
    main()