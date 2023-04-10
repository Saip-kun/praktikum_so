import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class RoundRobinGUI extends JFrame implements ActionListener {

    // Component variables
    private JLabel titleLabel, numProcessLabel, quantumLabel;
    private JTextField numProcessField, quantumField;
    private JButton submitButton;
    private JTextArea outputArea;

    // Constructor
    public RoundRobinGUI() {
        // Set up window
        setTitle("Round Robin Scheduler");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up layout
        setLayout(new BorderLayout());

        // Create components
        titleLabel = new JLabel("Round Robin Scheduler");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        numProcessLabel = new JLabel("Number of processes:");
        quantumLabel = new JLabel("Quantum time:");
        numProcessField = new JTextField(10);
        quantumField = new JTextField(10);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        // Add components to panel
        JPanel topPanel = new JPanel();
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));
        inputPanel.add(numProcessLabel);
        inputPanel.add(numProcessField);
        inputPanel.add(quantumLabel);
        inputPanel.add(quantumField);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.EAST);

        // Show window
        setVisible(true);
    }

    // Action listener for submit button
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Get input values
            int n = Integer.parseInt(numProcessField.getText());
            int quantum = Integer.parseInt(quantumField.getText());

            // Run Round Robin algorithm
            String output = runRoundRobin(n, quantum);

            // Display output
            outputArea.setText(output);
        }
    }

    // Method to run Round Robin algorithm
    public String runRoundRobin(int n, int quantum) {
        Scanner input = new Scanner(System.in);

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];

        int currentTime = 0;
        int completed = 0;
        int[] remainingTime = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter data for process " + (i + 1) + ":");
            System.out.print("  - Arrival time: ");
            arrivalTime[i] = input.nextInt();
            System.out.print("  - Burst time: ");
            burstTime[i] = input.nextInt();
            remainingTime[i] = burstTime[i];
        }

        String output = "";
        while (completed != n) {
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] > 0) {
                    if (remainingTime[i] <= quantum) {
                        currentTime += remainingTime[i];
                        completionTime[i] = currentTime;
                        turnAroundTime[i] = completionTime[i] - arrivalTime[i];
                        waitingTime[i] = turnAroundTime[i] - waitingTime[i] - burstTime[i];
                        remainingTime[i] = 0;
                        completed++;
                    } else {
                        currentTime += quantum;
                        remainingTime[i] -= quantum;
                    }
                }
            }
        }
        // Calculate averages
        int totalTurnAroundTime = 0;
        int totalWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            totalTurnAroundTime += turnAroundTime[i];
            totalWaitingTime += waitingTime[i];
        }
        double avgTurnAroundTime = (double) totalTurnAroundTime / n;
        double avgWaitingTime = (double) totalWaitingTime / n;

        // Build output string
        output += "Process\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time\n";
        for (int i = 0; i < n; i++) {
            output += (i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" + completionTime[i] + "\t\t"
                    + turnAroundTime[i] + "\t\t" + waitingTime[i] + "\n";
        }
        output += "Average Turnaround Time: " + avgTurnAroundTime + "\n";
        output += "Average Waiting Time: " + avgWaitingTime + "\n";

        return output;
    }

    // Main method to create GUI
    public static void main(String[] args) {
        RoundRobinGUI gui = new RoundRobinGUI();
    }
}
