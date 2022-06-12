from sqlite3 import Time
from timeExport import TimeExporter

def main():
    TimeExporter.exportTimes("../results" , r"Sim")
   
if __name__ == "__main__":
    main()