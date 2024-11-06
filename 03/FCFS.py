def fcfs_scheduling(arrival_time, burst_time):
    n = len(arrival_time)
    completion_time = [0] * n
    waiting_time = [0] * n
    turnaround_time = [0] * n

    current_time = 0  # Tracks current time

    for i in range(n):
        if current_time < arrival_time[i]:
            current_time = arrival_time[i]
        completion_time[i] = current_time + burst_time[i]
        current_time = completion_time[i]
        waiting_time[i] = completion_time[i] - arrival_time[i] - burst_time[i]
        turnaround_time[i] = waiting_time[i] + burst_time[i]

    return completion_time, waiting_time, turnaround_time


def main():
    # Input number of processes
    n = int(input("Enter number of processes: "))
    
    arrival_time = []
    burst_time = []

    # Input arrival time and burst time for each process
    print("Enter arrival times:")
    for i in range(n):
        arrival_time.append(int(input()))

    print("Enter burst times:")
    for i in range(n):
        burst_time.append(int(input()))

    # FCFS Scheduling
    completion_time, waiting_time, turnaround_time = fcfs_scheduling(arrival_time, burst_time)

    # Output results
    print("\nProcess\tArrival\tBurst\tCompletion\tWaiting\tTurnaround")
    for i in range(n):
        print(f"{i+1}\t{arrival_time[i]}\t{burst_time[i]}\t{completion_time[i]}\t\t{waiting_time[i]}\t{turnaround_time[i]}")

    # Calculate and display averages
    avg_waiting_time = sum(waiting_time) / n
    avg_turnaround_time = sum(turnaround_time) / n

    print(f"\nAverage Waiting Time: {avg_waiting_time:.2f}")
    print(f"Average Turnaround Time: {avg_turnaround_time:.2f}")


if __name__ == "__main__":
    main()
