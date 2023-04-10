import java.util.Scanner;

public class SRT {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Masukkan jumlah proses: ");
        int n = input.nextInt();
        
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];
        int[] remainingTime = new int[n];
        
        for (int i = 0; i < n; i++) {
            System.out.println("Masukkan data untuk proses " + (i+1) + ":");
            System.out.print("  - Waktu kedatangan: ");
            arrivalTime[i] = input.nextInt();
            System.out.print("  - Waktu burst: ");
            burstTime[i] = input.nextInt();
            remainingTime[i] = burstTime[i];
        }
        
        int currentTime = 0;
        int completed = 0;
        boolean[] executed = new boolean[n];
        while (completed != n) {
            int shortestJob = -1;
            int shortestTime = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] < shortestTime && !executed[i]) {
                    shortestJob = i;
                    shortestTime = remainingTime[i];
                }
            }
            if (shortestJob == -1) {
                currentTime++;
            } else {
                remainingTime[shortestJob]--;
                if (remainingTime[shortestJob] == 0) {
                    completionTime[shortestJob] = currentTime + 1;
                    turnAroundTime[shortestJob] = completionTime[shortestJob] - arrivalTime[shortestJob];
                    waitingTime[shortestJob] = turnAroundTime[shortestJob] - burstTime[shortestJob];
                    executed[shortestJob] = true;
                    completed++;
                }
                currentTime++;
            }
        }
        
        double totalTurnAroundTime = 0;
        double totalWaitingTime = 0;
        System.out.println("\nHasil:");
        System.out.println("Proses\tWaktu Kedatangan\tWaktu Burst\tWaktu Selesai\tWaktu Tunggu\tWaktu Putar");
        for (int i = 0; i < n; i++) {
            System.out.println((i+1) + "\t\t" + arrivalTime[i] + "\t\t\t" + burstTime[i] + "\t\t\t" + completionTime[i] + "\t\t\t" + waitingTime[i] + "\t\t\t" + turnAroundTime[i]);
            totalTurnAroundTime += turnAroundTime[i];
            totalWaitingTime += waitingTime[i];
        }
        double averageTurnAroundTime = totalTurnAroundTime / n;
        double averageWaitingTime = totalWaitingTime / n;
        System.out.println("\nWaktu rata-rata putar: " + averageTurnAroundTime);
        System.out.println("Waktu rata-rata tunggu: " + averageWaitingTime);
    }
}
