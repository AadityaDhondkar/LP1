from collections import deque

class Process:
    def __init__(self, id, arrival_time, burst_time):
        self.id = id
        self.arrival_time = arrival_time
        self.burst_time = burst_time
        self.remaining_time = burst_time

def sjf_preemptive_scheduling(processes):
    time = 0
    completed = 0
    n = len(processes)
    total_waiting_time = 0
    total_turnaround_time = 0

    # Sort processes by arrival time
    processes.sort(key=lambda p: p.arrival_time)

    ready_queue = deque()
    current_process = None

    while completed < n:
        # Add all processes that have arrived by the current time
        for p in processes:
            if p.arrival_time <= time and p not in ready_queue and p.remaining_time > 0:
                ready_queue.append(p)

        if current_process is None or current_process.remaining_time == 0:
            # If no process is running or current process finished
            if ready_queue:
                # Select the process with shortest remaining burst time (SJF)
                current_process = min(ready_queue, key=lambda p: p.remaining_time)
                ready_queue.remove(current_process)
                current_process.remaining_time -= 1
                total_waiting_time += (time - current_process.arrival_time)
        
        else:
            current_process.remaining_time -= 1

        if current_process.remaining_time == 0:
            # Process finished
            completed += 1
            total_turnaround_time += (time - current_process.arrival_time + 1)
            current_process = None
        
        time += 1

    print("Average Waiting Time:", total_waiting_time / n)
    print("Average Turnaround Time:", total_turnaround_time / n)

if __name__ == "__main__":
    # Creating process instances (ID, Arrival Time, Burst Time)
    processes = [
        Process(1, 0, 6),
        Process(2, 2, 4),
        Process(3, 4, 2),
        Process(4, 6, 1)
    ]
    
    sjf_preemptive_scheduling(processes)
