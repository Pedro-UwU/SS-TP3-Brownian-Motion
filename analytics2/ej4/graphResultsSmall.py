import json
import math
import numpy as np
import matplotlib.pyplot as plt

delta_t = 0.05

def main():
    # primero con la grande
    dcm_big = dict()
    min_t = math.inf
    
    with open(f'results/dcm_small_Sim_130_0.json', 'r') as data_file:
        data = json.load(data_file)
        min_t = len(data['0'])
        for key in data:
            for t, dist in enumerate(data[key]):
                if not t in dcm_big:
                    dcm_big[t] = list()
                dcm_big[t].append(dist)
    avgs = list()
    stds = list()
    x = [i*delta_t for i in range(min_t)]

    for i in range(min_t):
        avgs.append(np.average(dcm_big[i]))
        stds.append(np.std(dcm_big[i])/math.sqrt(5))
    
    regres = [0.04*a for a in x]
    
    plt.errorbar(x, avgs, yerr=stds, fmt='-', lw=0.5, color='blue', alpha=0.2)
    plt.plot(x, avgs, color='blue', lw=1.4)
    plt.plot(x, regres, lw=4)
    plt.xlabel('Tiempo (s)')
    plt.ylabel("DCM (m^2)")
    plt.grid()
    plt.show()
    
    errors = list()
    ms = [x/100 for x in range(-50, 100)]
    for m in ms:
        error = 0
        for t, avg in enumerate(avgs):
            diff = (avg - (t*delta_t)*m)**2
            error += diff
        errors.append(error)

    min_error = min(errors)
    min_index = errors.index(min_error)
    min_m = -0.5 + min_index/100
    print(min_m)
    
    plt.plot(ms, errors, 'o-', ms=5, alpha=0.5)
    plt.xlabel("Pendiente de recta")
    plt.ylabel("Error de ajuste")
    plt.show()
    


if __name__ == '__main__':
    main()