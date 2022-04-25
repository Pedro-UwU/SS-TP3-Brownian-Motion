
from cProfile import label
import statistics
from resultReader import ResultReader
from scipy.interpolate import UnivariateSpline
import matplotlib.pyplot as plt
import numpy as np
import json

def main():
    data =  ResultReader.parse('../results')
    print(data)
    for x in data:
      print(x['frecuency'])
    
    x = data[0]['modules']

    p , x = np.histogram(x  , 50 , (min(x), max(x)))
    x = x[:-1] + (x[1] - x[0])/2
    f = UnivariateSpline(x, p, s=50)
    plt.plot(x, f(x))
    plt.show()
    #with open(f'results.json') as json_file:
     #           dataPorVariacion = json.load(json_file)
    
   # Orden.plotOrden(dataPorVariacion)
   # print(dataPorVariacion)
    
   
if __name__ == "__main__":
    main()