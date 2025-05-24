import plotly.express as px
import pandas as pd

file = open("output.txt")
lines = file.readlines()
processes: dict[str, list[str]] = {}
for line in lines:
    line = line.replace("\n", "")
    pieces = line.split(" ")
    if not processes.get(pieces[3]):
        processes[pieces[3]] = []
    processes[pieces[3]].append(line)

df = []
for processName in processes:
    schedules = processes[processName]
    for i in range(0, len(schedules), 2):
        pieces1 = schedules[i].split(" ")
        pieces2 = schedules[i + 1].split(" ")

        Task = processName
        CPU = pieces1[2]
        Start = "2025-01-"+pieces1[1]
        Finish = "2025-01-"+pieces2[1]
        df.append(dict(CPU=CPU, Start=Start, Finish=Finish, Task=Task))

print(df)

fig = px.timeline(df, x_start="Start", x_end="Finish", y="CPU", color="Task")
fig.show()
