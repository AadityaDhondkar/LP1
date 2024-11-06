import java.util.*;

class Process {
    int id, arrivalTime, burstTime, priority, waitingTime, turnaroundTime;

    Process(int id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
    }
}

public class PriorityScheduling {
    public static void priorityScheduling(List<Process> processes) {
        // Sort processes by arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int n = processes.size();
        int time = 0;
        int completed = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        while (completed < n) {
            // Find processes that have arrived by the current time
            List<Process> readyQueue = new ArrayList<>();
            for (Process p : processes) {
                if (p.arrivalTime <= time && p.burstTime > 0) {
                    readyQueue.add(p);
                }
            }

            // Select the process with highest priority (lowest priority number = highest priority)
            if (!readyQueue.isEmpty()) {
                Process currentProcess = readyQueue.stream()
                        .min(Comparator.comparingInt(p -> p.priority))
                        .orElse(null);

                // Execute the process
                time += currentProcess.burstTime;
                currentProcess.turnaroundTime = time - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                totalWaitingTime += currentProcess.waitingTime;
                totalTurnaroundTime += currentProcess.turnaroundTime;
                currentProcess.burstTime = 0;  // Mark process as completed
                completed++;
            } else {
                time++;  // If no process is ready, increment time
            }
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / n);
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 6, 2));  // Process id, arrival time, burst time, priority
        processes.add(new Process(2, 2, 4, 1));
        processes.add(new Process(3, 4, 2, 3));
        processes.add(new Process(4, 6, 1, 4));
        
        priorityScheduling(processes);
    }
}
