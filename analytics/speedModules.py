
import matplotlib.pyplot as plt
import numpy as np
import re
class SpeedModules:

    @staticmethod
    def get_pdf(data):
        x = []
        #Agarro todos los modulos de velocidad
        for test in data:
            x = x + test['modules']
        #con N setteo cuantas subdivisiones quiero
        n = 25
        #Creo el histograma
        p , z = np.histogram(x  , n , (min(x), max(x)))
        #Centro el z
        z = z[:-1] + (z[1] - z[0])/2
        #Lo divido para que me quede la densidad de probabilidad
        pdf =  [y/(len(x)) for y in p ]
        sum = 0 
        for i in pdf:
            sum+= i
        print(sum)
        #Compruebo que efectivamente me dio 1
        
        return z , pdf
        #Hago el grafico
        plt.plot(z ,pdf , 'ro') #lineal (queremos que parezca discreto?)
        plt.show()


    @staticmethod
    def get_initial_pdf(data):
        x = []
        #Agarro todos los modulos de velocidad
        for test in data:
            x = x + test['starting_module']
        #con N setteo cuantas subdivisiones quiero
        n = 25
        #Creo el histograma
        p , z = np.histogram(x  , n , (min(x), max(x)))
        #Centro el z
        z = z[:-1] + (z[1] - z[0])/2
        #Lo divido para que me quede la densidad de probabilidad
        pdf =  [y/(len(x)) for y in p ]
        sum = 0 
        for i in pdf:
            sum+= i
        print(sum)
        #Compruebo que efectivamente me dio 1
        
        return z , pdf