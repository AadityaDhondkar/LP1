import java.util.*;

class Process {
    int id, burstTime, remainingTime, arrivalTime, waitingTime, turnaroundTime;

    Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
}

public class RoundRobin {
    public static void roundRobinScheduling(List<Process> processes, int quantum) {
        int time = 0;
        int completed = 0;
        int n = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        Queue<Process> readyQueue = new LinkedList<>();
        int index = 0;
        
        while (completed < n) {
            // Add processes to readyQueue that have arrived by the current time
            while (index < n && processes.get(index).arrivalTime <= time) {
                readyQueue.add(processes.get(index));
                index++;
            }

            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.poll();

                // Calculate time spent in execution
                int executionTime = Math.min(currentProcess.remainingTime, quantum);
                currentProcess.remainingTime -= executionTime;
                time += executionTime;

                // Calculate waiting and turnaround times
                if (currentProcess.remainingTime > 0) {
                    readyQueue.add(currentProcess);  // Requeue the process if it's not finished
                } else {
                    completed++;
                    currentProcess.turnaroundTime = time - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;
                }
            } else {
                time++;  // No process is ready, increment time
            }
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / n);
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 6));  // Process ID, Arrival Time, Burst Time
        processes.add(new Process(2, 2, 4));
        processes.add(new Process(3, 4, 2));
        processes.add(new Process(4, 6, 1));
        
        int quantum = 3;  // Time Quantum
        roundRobinScheduling(processes, quantum);
    }
}
