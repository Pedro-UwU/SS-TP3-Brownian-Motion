
from cProfile import label
import statistics
from resultReader import ResultReader
from scipy.interpolate import UnivariateSpline
import matplotlib.pyplot as plt
import numpy as np
import json

def main():
    data =  ResultReader.parse('../results')
    ##print(data)
    ##for x in data:
     ## print(x['frecuency'])
    
    x = data[0]['modules']
    n = 25
    p , z = np.histogram(x  , n , (min(x), max(x)))
    
    z = z[:-1] + (z[1] - z[0])/2
    #print(delta)
    pdf =  [y/(len(x)) for y in p ]
    sum = 0 
    for i in pdf:
      sum+= i
    print(max(x))
    print(min(x))
    print(sum)
    plt.plot(z , pdf)
    plt.show()
    #with open(f'results.json') as json_file:
     #           dataPorVariacion = json.load(json_file)
    
   # Orden.plotOrden(dataPorVariacion)
   # print(dataPorVariacion)
    
   
if __name__ == "__main__":
    main()