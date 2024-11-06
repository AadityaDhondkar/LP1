from collections import deque

class Process:
    def __init__(self, id, arrival_time, burst_time):
        self.id = id
        self.arrival_time = arrival_time
        self.burst_time = burst_time
        self.remaining_time = burst_time
        self.waiting_time = 0
        self.turnaround_time = 0

def round_robin_scheduling(processes, quantum):
    time = 0
    completed = 0
    n = len(processes)
    total_waiting_time = 0
    total_turnaround_time = 0

    # Sort processes by arrival time
    processes.sort(key=lambda p: p.arrival_time)

    ready_queue = deque()
    index = 0
    
    while completed < n:
        # Add all processes that have arrived by the current time to the ready queue
        while index < n and processes[index].arrival_time <= time:
            ready_queue.append(processes[index])
            index += 1

        if ready_queue:
            current_process = ready_queue.popleft()

            # Calculate time spent in execution
            execution_time = min(current_process.remaining_time, quantum)
            current_process.remaining_time -= execution_time
            time += execution_time

            # Calculate waiting and turnaround times
            if current_process.remaining_time > 0:
                ready_queue.append(current_process)  # Requeue if not finished
            else:
                completed += 1
                current_process.turnaround_time = time - current_process.arrival_time
                current_process.waiting_time = current_process.turnaround_time - current_process.burst_time
                total_waiting_time += current_process.waiting_time
                total_turnaround_time += current_process.turnaround_time
        else:
            time += 1  # If no process is ready, increment time

    print(f"Average Waiting Time: {total_waiting_time / n}")
    print(f"Average Turnaround Time: {total_turnaround_time / n}")

if __name__ == "__main__":
    # Creating process instances (ID, Arrival Time, Burst Time)
    processes = [
        Process(1, 0, 6),
        Process(2, 2, 4),
        Process(3, 4, 2),
        Process(4, 6, 1)
    ]
    
    quantum = 3  # Time Quantum
    round_robin_scheduling(processes, quantum)
