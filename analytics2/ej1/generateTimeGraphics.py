
# importing
from jsonReader import JsonReader
from collisionStats import ColissionStats
import matplotlib
def main():

    runs100 = JsonReader.readList("../dt-exports" , r'Sim_100_')
    runs115 = JsonReader.readList("../dt-exports" , r'Sim_115_')
    runs130 = JsonReader.readList("../dt-exports" , r'Sim_130_')


    font = {'family' : 'normal',
    'size'   : 14}
    matplotlib.rc('font', **font)
    ColissionStats.avg_freq_graph(runs100 , runs115 , runs130)
    ColissionStats.avg_col_graph(runs100 , runs115 , runs130)
    
    
    ColissionStats.graph_init()
    ColissionStats.pdf_collitionTime(runs100 , 'N=100')
    ColissionStats.pdf_collitionTime(runs115 , 'N=115')
    ColissionStats.pdf_collitionTime(runs130 , 'N=130')
    ColissionStats.graph_show()
if __name__ == "__main__":
    main()