import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan jumlah proses: ");
        int n = input.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Masukkan data untuk proses " + (i + 1) + ":");
            System.out.print("  - Waktu kedatangan: ");
            arrivalTime[i] = input.nextInt();
            System.out.print("  - Waktu burst: ");
            burstTime[i] = input.nextInt();
        }

        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (arrivalTime[i] > currentTime) {
                currentTime = arrivalTime[i];
            }
            completionTime[i] = currentTime + burstTime[i];
            turnAroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnAroundTime[i] - burstTime[i];
            currentTime = completionTime[i];
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
