import json
import sys
import numpy as np
import matplotlib.pyplot as plt

def main():
    
    files = []
    vels = []
    init_vels = []

    first_argv = True
    for i in sys.argv:
        if first_argv: 
            first_argv = False
            continue
        files.append(i)
    
    for file_name in files:
        with open(f'results/{file_name}.json', 'r+') as json_file:
            data = json.load(json_file)
            for v in data["vels"]:
                vels.append(v)
        with open(f'results/{file_name}_beg.json', 'r+') as json_beg:
            data = json.load(json_beg)
            for v in data["vels"]:
                init_vels.append(v)

    print(len(vels))
    total_bins = 20
    min_v = 0
    max_v = 4
    

    hist, bins = np.histogram(vels, bins=total_bins, range=(min_v, max_v))
    hist_beg, bins_beg = np.histogram(init_vels, bins=bins, range=(min_v, max_v))

    shifted_bins = bins.copy()
    for i, bin in enumerate(shifted_bins):
        shifted_bins[i] = bin - 0.1

    ax = plt.gca()
    
    print(bins[-1])


    maxV = bins[-1]
    bin_w = maxV/total_bins
    bin_w *= 0.95


    
    plt.bar(shifted_bins[1:], hist_beg/(len(init_vels)), color='orange', alpha=0.6, width=bin_w, label='Velocidades Iniciales')
    plt.bar(shifted_bins[1:], hist/(len(vels)), color='blue', alpha=0.4, width=bin_w, label='Velocidades Finales')

    ax.set_xticks(bins)
    plt.xticks(rotation=45)

    legends = ["Instante inicial", "Último tercio"]

    plt.xlabel("Magnitud de velocidad (m/s)")
    plt.ylabel("Probabilidad")
    plt.legend(legends)
    plt.show()


if __name__ == "__main__":
    main()