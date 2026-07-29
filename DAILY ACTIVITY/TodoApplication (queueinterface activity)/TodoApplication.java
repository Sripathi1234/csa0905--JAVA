import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TodoApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PriorityQueue<String> pendingTasks = new PriorityQueue<>();
        ArrayList<String> completedTasks = new ArrayList<>();

        int choice;

        do {

            System.out.println("\n===== TO-DO APPLICATION =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Next Task");
            System.out.println("3. Complete Task");
            System.out.println("4. View Pending Tasks");
            System.out.println("5. View Completed Tasks");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Priority (1-High, 2-Medium, 3-Low): ");
                    int priority = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Task Name: ");
                    String task = sc.nextLine();

                    pendingTasks.offer(priority + " - " + task);

                    System.out.println("Task Added Successfully!");
                    break;

                case 2:

                    if (pendingTasks.isEmpty()) {
                        System.out.println("No Pending Tasks.");
                    } else {
                        System.out.println("Next Task: " + pendingTasks.peek());
                    }

                    break;

                case 3:

                    if (pendingTasks.isEmpty()) {
                        System.out.println("No Tasks to Complete.");
                    } else {

                        String completed = pendingTasks.poll();
                        completedTasks.add(completed);

                        System.out.println("Completed: " + completed);
                    }

                    break;

                case 4:

                    if (pendingTasks.isEmpty()) {
                        System.out.println("No Pending Tasks.");
                    } else {
                        System.out.println("\nPending Tasks:");
                        System.out.println(pendingTasks);
                    }

                    break;

                case 5:

                    if (completedTasks.isEmpty()) {
                        System.out.println("No Completed Tasks.");
                    } else {
                        System.out.println("\nCompleted Tasks:");
                        System.out.println(completedTasks);
                    }

                    break;

                case 6:

                    System.out.println("Thank You!");
                    break;

                default:

                    System.out.println("Invalid Choice!");

            }

        } while (choice != 6);

        sc.close();
    }
}