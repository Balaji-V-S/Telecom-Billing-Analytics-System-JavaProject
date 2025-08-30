package com.tabs.ui;
import java.util.Scanner;

public class TABSapp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Telecom Customer Billing System!");
        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAdminMenu();
                    break;
                case "2":
                    showUserMenu();
                    break;
                case "3":
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    // ADMIN MENU
    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Add Plans");
            System.out.println("4. View All Subscriptions");
            System.out.println("5. Assign Plans to Customers (Subscriptions)");
            System.out.println("6. Add Usage");
            System.out.println("7. View Usage");
            System.out.println("8. Generate Invoices");
            System.out.println("9. View Invoices");
            System.out.println("10. Mark Invoice Paid");
            System.out.println("11. Reports & Analytics");
            System.out.println("12. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": System.out.println("[TODO] Add Customer"); break;
                case "2": System.out.println("[TODO] View Customers"); break;
                case "3": System.out.println("[TODO] Add Plans"); break;
                case "4": System.out.println("[TODO] View All Subscriptions"); break;
                case "5": System.out.println("[TODO] Assign Plans to Customers"); break;
                case "6": System.out.println("[TODO] Add Usage"); break;
                case "7": System.out.println("[TODO] View Usage"); break;
                case "8": System.out.println("[TODO] Generate Invoices"); break;
                case "9": System.out.println("[TODO] View Invoices"); break;
                case "10": System.out.println("[TODO] Mark Invoice Paid"); break;
                case "11": showReportsMenu(); break;
                case "12":
                    System.out.println("Logging out from Admin Menu...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // REPORTS MENU (ADMIN)
    private static void showReportsMenu() {
        while (true) {
            System.out.println("\n--- Reports & Analytics ---");
            System.out.println("1. Top N Data Users");
            System.out.println("2. ARPU by Plan");
            System.out.println("3. Overage Analysis");
            System.out.println("4. Credit Risk Report");
            System.out.println("5. Plan Recommendations");
            System.out.println("6. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": System.out.println("[TODO] Top N Data Users"); break;
                case "2": System.out.println("[TODO] ARPU by Plan"); break;
                case "3": System.out.println("[TODO] Overage Analysis"); break;
                case "4": System.out.println("[TODO] Credit Risk Report"); break;
                case "5": System.out.println("[TODO] Plan Recommendations"); break;
                case "6": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    // USER MENU
    private static void showUserMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Profile");
            System.out.println("2. View Subscriptions");
            System.out.println("3. Add Usage");
            System.out.println("4. View Usage");
            System.out.println("5. View Invoices");
            System.out.println("6. Pay Invoice");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": System.out.println("[TODO] View Profile"); break;
                case "2": System.out.println("[TODO] View Subscriptions"); break;
                case "3": System.out.println("[TODO] Add Usage"); break;
                case "4": System.out.println("[TODO] View Usage"); break;
                case "5": System.out.println("[TODO] View Invoices"); break;
                case "6": System.out.println("[TODO] Pay Invoice"); break;
                case "7":
                    System.out.println("Logging out from User Menu...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}