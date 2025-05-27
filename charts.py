import plotly.express as px
import pandas as pd
import datetime

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
        Start = datetime.datetime.fromtimestamp(int(pieces1[1]))
        Finish = datetime.datetime.fromtimestamp(int(pieces2[1]))
        Delta = int(pieces2[1]) - int(pieces1[1])
        df.append(dict(CPU=CPU, Start=Start, Finish=Finish, Task=Task, ExitType=pieces2[0], Delta=Delta))
        
CPUs = sorted(set(d["CPU"] for d in df), key=lambda x: int(x))
print(CPUs)

fig = px.timeline(
    df,
    x_start="Start",
    x_end="Finish",
    y="CPU",
    color="Task",
    category_orders={"CPU": CPUs},
    hover_data=["ExitType", "Delta"]
)
fig.show()
