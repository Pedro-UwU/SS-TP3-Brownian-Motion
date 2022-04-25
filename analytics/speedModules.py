
import matplotlib.pyplot as plt
import numpy as np
import re
class SpeedModules:

    @staticmethod
    def get_pdf(data):
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