import sys
import json
def main():
    sim_name = sys.argv[1]
    run = None
    with open(f'../../results/{sim_name}/snapshots.json' , 'r') as json_file:
        run = json.load(json_file)    

    distance_center = []
    initial_pos = run['info'][0]['p']
    final_pos = run['info'][-1]['p']
    initial_pos.pop(0)
    final_pos.pop(0)
#   both = zip(initial_pos , final_pos)
    for idx , p in enumerate(initial_pos):
        distance_center.append( (distance(p , (3,3)) , idx) )

    distance_center.sort(key=lambda x:x[0])
    print(distance_center)

    top20start = distance_center[:20]
    print(len(top20start))

    top20Ending = []
    for p in top20start:
        final_pos_p = final_pos[p[1]] 
        top20Ending.append( (distance(final_pos_p , (3,3)) , p[1]))
    top20Ending.sort(key=lambda x:-x[0])
    indexes = [ x[1] for x in top20Ending[:5]]
    print(indexes)
    top5start = []
    for p in top20start:
        if p[1] in indexes:
            top5start.append(p)
    top5ending = top20Ending[:5]
    top5ending.sort(key=lambda x:x[1])
    top5start.sort(key=lambda x:x[1])
    print(top5start)
    print(top5ending)
    result = dict()
    result['indices'] = [x[1] for x in top5ending]
    with open(f'results/indices_{sim_name}.json', 'w+') as out_file:
        json.dump(result, out_file)


def distance( a , b):
    return (a[0]-b[0])**2 + (a[1]-b[1])**2 

if __name__ == "__main__":
    main()