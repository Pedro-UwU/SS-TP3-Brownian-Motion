@echo off
for %%n in (%*) do (
    py get_vels.py %%n
    py get_vels_beg.py %%n
)

py graph_results.py %*