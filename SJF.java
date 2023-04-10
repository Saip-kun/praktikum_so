import java.util.Scanner;

public class SJF {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Masukkan jumlah proses: ");
        int n = input.nextInt();
        
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];
        boolean[] processed = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            System.out.println("Masukkan data untuk proses " + (i+1) + ":");
            System.out.print("  - Waktu kedatangan: ");
            arrivalTime[i] = input.nextInt();
            System.out.print("  - Waktu burst: ");
            burstTime[i] = input.nextInt();
            processed[i] = false;
        }
        
        int currentTime = 0;
        int completed = 0;
        while (completed != n) {
            int shortestJob = -1;
            int shortestTime = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!processed[i] && arrivalTime[i] <= currentTime && burstTime[i] < shortestTime) {
                    shortestJob = i;
                    shortestTime = burstTime[i];
                }
            }
            if (shortestJob == -1) {
                currentTime++;
            } else {
                completionTime[shortestJob] = currentTime + burstTime[shortestJob];
                turnAroundTime[shortestJob] = completionTime[shortestJob] - arrivalTime[shortestJob];
                waitingTime[shortestJob] = turnAroundTime[shortestJob] - burstTime[shortestJob];
                processed[shortestJob] = true;
                completed++;
                currentTime = completionTime[shortestJob];
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
