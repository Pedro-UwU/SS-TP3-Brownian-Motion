import statistics
import matplotlib
import matplotlib.pyplot as plt
import numpy as np



class ColissionStats:

    @staticmethod
    def avg_frecuency(runs):
        f = [ r['frecuency'] for r in runs ]
        return np.average(f)
    @staticmethod
    def stderror_frecuency(runs):
        f = [ r['frecuency'] for r in runs ]
        return np.std(f)/np.sqrt(len(f))
    @staticmethod
    def avg_collitionTime(runs):
        times = []
        for r in runs:
            times = times + r['times'] 
        return np.average(times)

    
    @staticmethod
    def stderror_collitionTime(runs):
        t= []
        for r in runs:
            t = t + r['times']
        tms = [ time*1000 for time in t]
        #print(tms)
        return np.std(tms) / np.sqrt(len(tms))

    @staticmethod
    def pdf_collitionTime(runs,label):
        x = []
        #Agarro todas las colisiones
        for test in runs:
            x = x + test['times']
        #con N setteo cuantas subdivisiones quiero
        bins = int(max(x)*1000)
        print(bins)
        #Creo el histograma
      #  print(max(x))
        p , z = np.histogram(x  , bins , (min(x), max(x)))
      #  print(len(z))
        #Centro el z
        z_size = int((z[1] - z[0] ) * 1000) # in ms
        #z = z[:-1] + z_size/2
        z_aux = [ int(x * 1000) for x in z[:-1] ]
        z_labels =  [f'{x}-{x+z_size}' for x in z_aux]
       # print(len(p))
       # print(len(z_labels))
        
        #Lo divido para que me quede la distribucion de probabilidad
        pdf =  [y/(len(x)) for y in p ]
        sum = 0 
        for i in pdf:
            sum+= i
      #  print(sum)
        #Compruebo que efectivamente me dio 1
        
        
        #Hago el grafico
        plt.plot(z_labels , pdf , 'o-' , label=label , ms=12 , alpha=0.8)
     
     


    @staticmethod
    def graph_init():
   
        plt.figure(figsize=(15, 7.5))
        plt.xticks(rotation=65)
        ax = plt.gca()
        ax.set_ylim([0, 0.7])

    @staticmethod
    def graph_show():
        plt.legend()
        plt.xlabel('Tiempo entre colisiones (ms)')
        plt.grid(alpha=0.25)
        plt.ylabel('Probabilidad')
        plt.show()

    @staticmethod
    def avg_freq_graph(r100 , r115 , r130):
        plt.figure(figsize=(7.5,6))
        times = []
            
        ax = plt.gca()
        x = [ '100' , '115' , '130']
        color = [ 'cornflowerblue', 'orange' , 'forestgreen']
        ferror = [ ColissionStats.stderror_frecuency(r100) , ColissionStats.stderror_frecuency(r115) , ColissionStats.stderror_frecuency(r130)] 
        freqs = [ ColissionStats.avg_frecuency(r100) , ColissionStats.avg_frecuency(r115) , ColissionStats.avg_frecuency(r130)]
        bars = plt.bar(x , freqs , color = color , yerr=ferror , capsize=10,  alpha=0.8)
        ax.bar_label(bars , fmt='%.2f')
        plt.ylabel('Frecuencia promedio $s^-1$')
        plt.xlabel('N partículas chicas')

        
    @staticmethod
    def avg_col_graph(r100 , r115 , r130):
        plt.figure(figsize=(7.5,6))
        ax = plt.gca()
        x = [ '100' , '115' , '130']
        cerror = [ColissionStats.stderror_collitionTime(r100) , ColissionStats.stderror_collitionTime(r115) , ColissionStats.stderror_collitionTime(r130)]
        ctime = [ ColissionStats.avg_collitionTime(r100) * 1000, ColissionStats.avg_collitionTime(r115)* 1000 , ColissionStats.avg_collitionTime(r130)* 1000]
        color = [ 'cornflowerblue', 'orange' , 'forestgreen']
        bars = plt.bar(x , ctime , color = color , alpha=0.8 , yerr=cerror , capsize=10)
        ax.bar_label(bars , fmt='%.3f' )
        plt.ylabel('Tiempo promedio entre colisiones ms')
        plt.xlabel('N partículas chicas')