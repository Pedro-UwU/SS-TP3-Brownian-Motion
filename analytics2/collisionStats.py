import statistics
import matplotlib.pyplot as plt
import numpy as np



class ColissionStats:

    @staticmethod
    def avg_frecuency(runs):
        f = [ r['frecuency'] for r in runs ]
        return np.average(f)

    @staticmethod
    def avg_collitionTime(runs):
        t = [ np.average(r) for r in runs]
        return np.average(t)

    @staticmethod
    def pdf_collitionTime(runs):
        x = []
        #Agarro todos los modulos de velocidad
        for test in runs:
            x = x + test['times']
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
        
        
        #Hago el grafico
        plt.plot(z , pdf)
        plt.show()