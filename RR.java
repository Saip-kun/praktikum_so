import java.util.Arrays;
import java.util.Scanner;

public class RR {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Input validation
        int n;
        do {
            System.out.print("Masukkan jumlah proses (harus lebih besar dari 0): ");
            n = input.nextInt();
        } while (n <= 0);

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];

        System.out.print("Masukkan quantum (harus lebih besar dari 0): ");
        int quantum;
        do {
            quantum = input.nextInt();
        } while (quantum <= 0);

        for (int i = 0; i < n; i++) {
            System.out.println("Masukkan data untuk proses " + (i + 1) + ":");
            System.out.print("  - Waktu kedatangan: ");
            arrivalTime[i] = input.nextInt();
            System.out.print("  - Waktu burst: ");
            burstTime[i] = input.nextInt();
        }

        // Sort processes by arrival time
        int[][] processes = new int[n][2];
        for (int i = 0; i < n; i++) {
            processes[i][0] = arrivalTime[i];
            processes[i][1] = i;
        }
        Arrays.sort(processes, (a, b) -> Integer.compare(a[0], b[0]));

        int currentTime = 0;
        int completed = 0;
        int[] remainingTime = new int[n];

        for (int i = 0; i < n; i++) {
            remainingTime[i] = burstTime[processes[i][1]];
        }

        while (completed != n) {
            boolean isCompleted = true;
            for (int i = 0; i < n; i++) {
                int j = processes[i][1];
                if (arrivalTime[j] <= currentTime && remainingTime[j] > 0) {
                    isCompleted = false;
                    if (remainingTime[j] <= quantum) {
                        currentTime += remainingTime[j];
                        completionTime[j] = currentTime;
                        turnAroundTime[j] = completionTime[j] - arrivalTime[j];
                        waitingTime[j] = turnAroundTime[j] - burstTime[j];
                        remainingTime[j] = 0;
                        completed++;
                    } else {
                        currentTime += quantum;
                        remainingTime[j] -= quantum;
                    }
                }
            }
            // If no process was executed, increase current time
            if (isCompleted) {
                currentTime++;
            }
        }

        double totalTurnAroundTime = 0;
        double totalWaitingTime = 0;
        System.out.println("\nHasil:");
        System.out.format(
                "+-----------------+-----------------------+------------------+-------------------+-------------------+-------------------+%n");
        System.out.format("|%15s  | %21s | %16s | %17s | %17s | %17s |%n", "Proses", "Waktu Kedatangan", "Waktu Burst",
                "Waktu Selesai", "Waktu Tunggu", "Waktu Putar");
        System.out.format(
                "+-----------------+-----------------------+------------------+-------------------+-------------------+-------------------+%n");
        for (int i = 0; i < n; i++) {
            System.out.format("|%15s  | %21s | %16s | %17s | %17s | %17s |%n", (i + 1), arrivalTime[i], burstTime[i],
                    completionTime[i], waitingTime[i], turnAroundTime[i]);
            totalTurnAroundTime += turnAroundTime[i];
            totalWaitingTime += waitingTime[i];
        }
        System.out.format(
                "+-----------------+-----------------------+------------------+-------------------+-------------------+-------------------+%n");
        double averageTurnAroundTime = totalTurnAroundTime / n;
        double averageWaitingTime = totalWaitingTime / n;
        System.out.format("%-17s %-25.2f %-19s %-18.2f %-18s %-18.2f%n", "Waktu rata-rata putar:",
                averageTurnAroundTime, "\nWaktu rata-rata tunggu:", averageWaitingTime);
    }
}
