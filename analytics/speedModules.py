
from tracemalloc import Statistic
import matplotlib.pyplot as plt
import numpy as np
import re
class SpeedModules:

    @staticmethod
    def get_pdf(data, n):
        x = []
        #Agarro todos los modulos de velocidad
        for test in data:
            x = x + test['modules']
        #con N setteo cuantas subdivisiones quiero
        #Creo el histograma
        p , z = np.histogram(x  , n , (min(x), max(x)))
        #Centro el z
        z = z[:-1] + (z[1] - z[0])/2
        deltaZ = z[1] -z[0]
        #Lo divido para que me quede la densidad de probabilidad
        pdf =  [y/(len(x)*deltaZ) for y in p ]
        sum = 0 
        for i in pdf:
            sum+= i *deltaZ
        print(sum)
        #Compruebo que efectivamente me dio 1
        
        return z , pdf
        #Hago el grafico
        plt.plot(z ,pdf , 'ro') #lineal (queremos que parezca discreto?)
        plt.show()


    @staticmethod
    def get_initial_pdf(data , n):
        x = []
        #Agarro todos los modulos de velocidad
        for test in data:
            x = x + test['starting_module']

        #Creo el histograma
        p , z = np.histogram(x  , n , (min(x), max(x)))
        #Centro el z
        z = z[:-1] + (z[1] - z[0])/2
        deltaZ = z[1] -z[0]
        #Lo divido para que me quede la densidad de probabilidad
        pdf =  [y/(len(x)*deltaZ) for y in p ]
        sum = 0 
        for i in pdf:
            sum+= i *deltaZ
        print(sum)
        #Compruebo que efectivamente me dio 1
        
        return z , pdf

    @staticmethod
    def punto_2(data, n ):
        z1 , pdf_inicial = SpeedModules.get_initial_pdf(data , n)
        plt.bar(z1 , pdf_inicial , color='r')
        plt.ylabel('Densidad de probabilidad')
        plt.ylabel('Módulo de velocidad')
        plt.show()
        plt.savefig('PDF inicial')
        plt.clf()
        z2 , pdf = SpeedModules.get_pdf(data)
        plt.bar(z2 , pdf)
        plt.ylabel('Densidad de probabilidad')
        plt.ylabel('Módulo de velocidad')
        plt.show()
        plt.savefig('PDF')
        plt.clf()