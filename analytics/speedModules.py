
from operator import mod
from os import stat
from tracemalloc import Statistic
import matplotlib.pyplot as plt
import numpy as np
import re
class SpeedModules:

    @staticmethod
    def get_pdf(data, n):
        z = data[0]['modules_x']
        #Agarro todos los histogramas
        hist = data[0]['modules_hist']
        for test in data[1:]:
            for idx,value in enumerate(hist):
                hist[idx] = test['modules_hist'][idx] + value
        acum = 0
        for v in hist:
            acum+=v
        #con N setteo cuantas subdivisiones quiero
        #Creo el histograma
        #p , z = np.histogram(x  , n , (min(x), max(x)))
        #Centro el z
        z = z[:-1] + (z[1] - z[0])/2
        deltaZ = z[1] -z[0]
        #Lo divido para que me quede la densidad de probabilidad
        pdf =  [y/(acum*deltaZ) for y in hist ]
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
    def initialize_hist(min_x , max_x , n):
        x = np.linspace(min_x , max_x , n +1)
        plot = [0] * n
        return plot , x

    @staticmethod
    def put_hist(value , plot , x):
        for idx , val in enumerate(x):
                if( val <= value):
                    plot[idx] += 1

    @staticmethod
    def fill_hist(modules , plot , x ):
        
        for m in modules:    
            for idx , val in enumerate(x):
                if( val <= m):
                    plot[idx] += 1
            
        

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
    def punto_2(data, n = 25):
       # z1 , pdf_inicial = SpeedModules.get_initial_pdf(data , n)
       # plt.bar(z1 , pdf_inicial , color='r')
       # plt.ylabel('Densidad de probabilidad')
       # plt.ylabel('Módulo de velocidad')
       # plt.show()
       # plt.savefig('PDF inicial')
      #  plt.clf()
        z2 , pdf = SpeedModules.get_pdf(data , n)
        plt.bar(z2 , pdf)
        plt.ylabel('Densidad de probabilidad')
        plt.xlabel('Módulo de velocidad')
        plt.show()
        plt.savefig('PDF2.png')
        plt.clf()