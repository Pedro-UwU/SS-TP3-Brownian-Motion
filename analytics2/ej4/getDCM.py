import sys
import json

def main():
    first_arg = True

    delta_t = 0.05

    
    indices = None
    data = None
    name = sys.argv[1]

    results = dict()
    particle = 0

    with open(f'results/indices_{name}.json', 'r') as index_file:
        indices = json.load(index_file)['indices']
    with open(f'../../results/{name}/snapshots.json', 'r') as data_file:
        data = json.load(data_file)
    for i in indices:
        print(f"Computing {i}")
        d = computeDCM(i, data, delta_t)
        results[particle] = d
        particle += 1
        
    with open(f'results/dcm_small_{name}.json', 'w+') as out_file:
        json.dump(results, out_file)



def computeDCM(index, data, delta_t):
    distances = list()
    next_t = 0
    initial_pos = data['info'][0]['p'][index]
    for snapshot in data['info']:
        t = snapshot['t']
        
        if  t < next_t:
            continue
        position = snapshot['p'][index]
        dist = distance(position, initial_pos)
        distances.append(round(dist, 5))
        print(f'Current t {t}')
        next_t += delta_t
    return distances


def distance(pos1, pos2):
    return (pos1[0]-pos2[0])**2 + (pos1[1]-pos2[1])**2

if __name__ == '__main__':
    main()