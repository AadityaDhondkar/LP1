import java.util.Scanner;

public class FCFS_Scheduling {
    
    static void findWaitingTime(int processes[], int n, int bt[], int wt[]) {
        wt[0] = 0; // Waiting time for the first process is 0
        
        // Calculate waiting time for the rest of the processes
        for (int i = 1; i < n; i++) {
            wt[i] = bt[i - 1] + wt[i - 1];
        }
    }
    
    static void findTurnAroundTime(int processes[], int n, int bt[], int wt[], int tat[]) {
        // Turnaround time = Burst time + Waiting time
        for (int i = 0; i < n; i++) {
            tat[i] = bt[i] + wt[i];
        }
    }
    
    static void findAverageTime(int processes[], int n, int bt[]) {
        int wt[] = new int[n];
        int tat[] = new int[n];
        
        // Find waiting time and turnaround time
        findWaitingTime(processes, n, bt, wt);
        findTurnAroundTime(processes, n, bt, wt, tat);
        
        int total_wt = 0, total_tat = 0;
        
        // Calculate total waiting time and total turnaround time
        for (int i = 0; i < n; i++) {
            total_wt += wt[i];
            total_tat += tat[i];
        }
        
        // Print results
        System.out.println("Process\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i] + "\t\t" + bt[i] + "\t\t" + wt[i] + "\t\t" + tat[i]);
        }
        
        // Calculate and print average waiting time and turnaround time
        System.out.println("\nAverage waiting time: " + (float) total_wt / n);
        System.out.println("Average turnaround time: " + (float) total_tat / n);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        
        int processes[] = new int[n];
        int burstTime[] = new int[n];
        
        // Input the burst time for each process
        for (int i = 0; i < n; i++) {
            processes[i] = i + 1; // Process numbers are usually 1, 2, 3, ...
            System.out.print("Enter burst time for process " + processes[i] + ": ");
            burstTime[i] = sc.nextInt();
        }
        
        // Calculate average waiting time and turnaround time
        findAverageTime(processes, n, burstTime);
        
        sc.close();
    }
}
