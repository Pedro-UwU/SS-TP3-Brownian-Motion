
import statistics
import matplotlib.pyplot as plt
import numpy as np


class Difussion:
    @staticmethod
    def get_dcm(snapshots , particle ):
        timeDelta = 0.2
        time = 0
        dcm_array = []
        times = []
        print(snapshots)
        inital_pos = snapshots[0]['p'][0]
        for snapshot in snapshots:
            print('snapshot')
            if snapshot['t'] >= time:
                print('adding time')
                times.append(time)
                x = snapshot['p'][particle][0] - inital_pos[0]
                y = snapshot['p'][particle][1] - inital_pos[1]
                dcm_array.append(x**2 + y **2)
                time+= timeDelta


        return dcm_array , times

    @staticmethod
    def calculate_avg(dcm_multi):
        min_t = None
        min_size = None
        for dcm in dcm_multi:
            if( min_size is None or len(dcm[0]) < min_size):
                min_size = len(dcm[0])
        avg_dcm = [0] * min_size
        error_dcm = [0] * min_size
        for i in range( 0 , min_size):
            values = []
            for dcm in dcm_multi:
                values.append(dcm[i])
            avg_dcm[i] = statistics.mean(values)
            error_dcm[i] = statistics.stdev(values)/np.sqrt(len(values))
        return avg_dcm , error_dcm , [x*0.2 for x in range(0 , len(avg_dcm))]
      #  plt.plot([x*0.2 for x in range(0 , len(avg_dcm))] , avg_dcm)
       # plt.show()

    @staticmethod
    def reg_lineal(avg_dcm , times):
        m , b = np.polyfit(times , avg_dcm , 1)
        return m*times + b


    @staticmethod
    def graphicate_dcm(dcm_array , times):
        plt.plot(times , dcm_array )
        plt.xlabel('Tiempo (s)', color='#1C2833')
        plt.ylabel('DCM (m^2)', color='#1C2833')
        plt.show()

    
