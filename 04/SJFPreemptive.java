import java.util.*;

class Process {
    int id, arrivalTime, burstTime, remainingTime;

    Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SJFPreemptive {
    public static void sjfPreemptiveScheduling(List<Process> processes) {
        int time = 0;
        int completed = 0;
        int n = processes.size();
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        // Sort processes by arrival time initially
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        // Queue to store processes
        Queue<Process> readyQueue = new LinkedList<>();
        Process currentProcess = null;
        
        while (completed < n) {
            // Add all processes that have arrived by the current time
            for (Process p : processes) {
                if (p.arrivalTime <= time && !readyQueue.contains(p)) {
                    readyQueue.offer(p);
                }
            }

            if (currentProcess == null || currentProcess.remainingTime == 0) {
                // If no process is currently running or current process is completed
                if (!readyQueue.isEmpty()) {
                    // Select the process with shortest burst time (SJF)
                    currentProcess = getShortestJob(readyQueue);
                    readyQueue.remove(currentProcess);
                    currentProcess.remainingTime--;
                    totalWaitingTime += (time - currentProcess.arrivalTime);
                }
            } else {
                currentProcess.remainingTime--;
            }
            
            if (currentProcess.remainingTime == 0) {
                // Process finished
                completed++;
                totalTurnaroundTime += (time - currentProcess.arrivalTime + 1);
                currentProcess = null;
            }
            
            time++;
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / n);
        System.out.println("Average Turnaround Time: " + (double) totalTurnaroundTime / n);
    }

    public static Process getShortestJob(Queue<Process> readyQueue) {
        return readyQueue.stream().min(Comparator.comparingInt(p -> p.remainingTime)).orElse(null);
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 6));  // Process id, arrival time, burst time
        processes.add(new Process(2, 2, 4));
        processes.add(new Process(3, 4, 2));
        processes.add(new Process(4, 6, 1));
        
        sjfPreemptiveScheduling(processes);
    }
}
