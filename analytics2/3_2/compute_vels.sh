#!/bin/bash

for NAME in "$@"
do
    python3 get_vels.py $NAME
    python3 get_vels_beg.py $NAME
done

python3 graph_results.py $@