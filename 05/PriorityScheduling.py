class Process:
    def __init__(self, id, arrival_time, burst_time, priority):
        self.id = id
        self.arrival_time = arrival_time
        self.burst_time = burst_time
        self.priority = priority
        self.waiting_time = 0
        self.turnaround_time = 0

def priority_scheduling(processes):
    # Sort processes by arrival time
    processes.sort(key=lambda p: p.arrival_time)
    
    time = 0
    completed = 0
    n = len(processes)
    total_waiting_time = 0
    total_turnaround_time = 0

    while completed < n:
        # Find processes that have arrived by the current time
        ready_queue = [p for p in processes if p.arrival_time <= time and p.burst_time > 0]

        if ready_queue:
            # Select process with highest priority (lowest priority value = highest priority)
            current_process = min(ready_queue, key=lambda p: p.priority)
            
            # Execute the process
            time += current_process.burst_time
            current_process.turnaround_time = time - current_process.arrival_time
            current_process.waiting_time = current_process.turnaround_time - current_process.burst_time
            
            total_waiting_time += current_process.waiting_time
            total_turnaround_time += current_process.turnaround_time
            current_process.burst_time = 0  # Mark process as completed
            completed += 1
        else:
            time += 1  # If no process is ready, increment time

    print(f"Average Waiting Time: {total_waiting_time / n}")
    print(f"Average Turnaround Time: {total_turnaround_time / n}")

if __name__ == "__main__":
    # Creating process instances (ID, Arrival Time, Burst Time, Priority)
    processes = [
        Process(1, 0, 6, 2),
        Process(2, 2, 4, 1),
        Process(3, 4, 2, 3),
        Process(4, 6, 1, 4)
    ]
    
    priority_scheduling(processes)
