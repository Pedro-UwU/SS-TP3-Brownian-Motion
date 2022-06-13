from jsonReader import JsonReader
import matplotlib.pyplot as plt
import numpy as np

def main():
    runs = JsonReader.readList("../trayectory-exports")
    
    minT = 300
    for run in runs:
        if run['t'][-1] < minT:
            minT = run['t'][-1]

    for run in runs:
        newP = []
        posTime = zip(run['p'] , run['t'])
        for pt in posTime:
            if pt[1] > minT:
                break 
            newP.append(pt[0])
        run['p'] = newP
        
    plt.figure(figsize=(10,10))
    ax = plt.gca()
    ax.set_ylim([0,6])
    ax.set_xlim([0,6])
    vel=1.0
    color = [ 'cornflowerblue', 'orange' , 'forestgreen']
    i = 0
    for run in runs[:3]:
        plt.plot([ x[0] for x in run['p']] , [ y[1] for y in run['p']] , alpha=0.7 , lw=3 , label=f'vel={vel}(m/s)' , color=color[i])
     
        xstart = run['p'][-1][0]
        ystart = run['p'][-1][1]
        plt.plot(xstart, ystart, 'o', ms=15,color=color[i])
        vel = vel + 0.5
        i+=1
    
    plt.xlabel('Posicion X (m)')
    plt.ylabel('Posición Y (m)')
    plt.grid(alpha=0.25)
    plt.legend()
    print(minT)
    plt.show()
if __name__ == "__main__":
    main()