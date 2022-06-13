# Read a json and export the module of the velocities in the last third of the simulation
import json
import math
import sys
import os

def main():
    name = sys.argv[1]
    print(f"NAME: {name}")
    static_file = open(f'{os.getcwd()}/../../results/{name}/static.json', "r+")
    dynamic_file = open(f'{os.getcwd()}/../../results/{name}/snapshots.json', "r+")
    json_dy = json.load(dynamic_file)

    vel_mags = []
    max_t = json_dy["info"][-1]['t']
    t_to_save = 3*max_t/4
    
    for step in json_dy["info"]:
        if step['t'] < t_to_save:
            continue
        first_p = True
        for v in step['v']:
            if first_p:
                first_p = False
                continue
            vx = v[0]
            vy = v[1]
            mag = math.sqrt((vx**2) + (vy**2))
            vel_mags.append(mag)
    
    result = {}
    result["vels"] = vel_mags
    with open(f"{os.getcwd()}/results/{name}.json", 'w+') as out_json:
        json.dump(result, out_json)

    static_file.close()
    dynamic_file.close()



if __name__ == "__main__":
    main()