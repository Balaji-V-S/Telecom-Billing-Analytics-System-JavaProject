package com.tabs.ui;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.tabs.dao.*;
import com.tabs.exceptions.*;
import com.tabs.models.*;
import com.tabs.services.*;
import com.tabs.utility.PlanConfig;

public class TABSapp {
    private static final Scanner scanner = new Scanner(System.in);

    // --- DAOs and Services are managed directly here ---
    private static final CustomerDAO customerDAO = new CustomerDAOImpl();
    private static final SubscriptionDAO subscriptionDAO = new SubscriptionDAOImpl();
    private static final UsageDAO usageDAO = new UsageDAOImpl();
    private static final BillingDAO billingDAO = new BillingDAOImpl();

    private static final CustomerService customerService = new CustomerServiceImpl(customerDAO, subscriptionDAO, billingDAO);
    private static final SubscriptionService subscriptionService = new SubscriptionServiceImpl(subscriptionDAO, customerDAO);
    private static final UsageService usageService = new UsageServiceImpl(usageDAO, subscriptionDAO, customerDAO);
    private static final BillingService billingService = new BillingServiceImpl(usageDAO, subscriptionDAO, customerDAO, billingDAO);
    private static final AnalyticsService analyticsService = new AnalyticsServiceImpl(usageDAO, billingDAO, subscriptionDAO, customerDAO);

    // --- Session & Security ---
    private static Customer loggedInCustomer = null;
    private static final String ADMIN_PASSWORD = "root@123";

    public static void main(String[] args) {
        try {
            InitializeUsers();
            System.out.println("\n=======================================================");
            System.out.println(" Welcome to the Telecom Billing & Analytics System");
            System.out.println("=======================================================");
            showMainMenu();
        } catch (Exception e) {
            System.err.println("A critical error occurred during startup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showMainMenu() throws SubscriptionNotFoundException, CustomerNotFoundException {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Register New Customer");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": showAdminLogin(); break;
                case "2": loginUser(); break;
                case "3": registerNewCustomer(); break;
                case "4":
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default: System.err.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerNewCustomer() throws SubscriptionNotFoundException, CustomerNotFoundException {
        if (loggedInCustomer != null) {
            System.out.println("Please log out first before registering a new customer.");
            return;
        }
        System.out.println("\n--- New Customer Registration ---");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        String newCustId = "C-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        Customer newCustomer = new Customer(newCustId, name, email);
        customerService.addCustomer(newCustomer);

        System.out.println("\nRegistration successful! Your new Customer ID is: " + newCustId);
        System.out.println("Please use this ID to log in in the future.");

        loggedInCustomer = newCustomer;
        System.out.println("You have been automatically logged in. Welcome, " + name + "!");
        showUserSessionMenu();
    }

    private static void showAdminLogin() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine();
        if (ADMIN_PASSWORD.equals(password)) {
            System.out.println("Admin login successful.");
            showAdminMenu();
        } else {
            System.err.println("Incorrect password. Access denied.");
        }
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Customer (Manual)");
            System.out.println("2. View Customers");
            System.out.println("3. Delete Customer");
            System.out.println("4. Add Subscription");
            System.out.println("5. Add Usage Record");
            System.out.println("6. Generate Invoices for a Month");
            System.out.println("7. View All Invoices");
            System.out.println("8. Run Credit Control Check");
            System.out.println("9. Reports & Analytics");
            System.out.println("10. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addCustomer(); break;
                case "2": viewCustomers(); break;
                case "3": deleteCustomer(); break;
                case "4": addSubscription(); break;
                case "5": addUsage(); break;
                case "6": generateInvoices(); break;
                case "7": viewAllInvoices(); break;
                case "8": customerService.runCreditControlCheck(); break;
                case "9": showReportsMenu(); break;
                case "10": return;
                default: System.err.println("Invalid choice.");
            }
        }
    }

    private static void showReportsMenu() {
        while (true) {
            System.out.println("\n--- Reports & Analytics ---");
            System.out.println("1. Top N Data Users");
            System.out.println("2. System-wide ARPU (Average Revenue Per User)");
            System.out.println("3. System Overage Analysis");
            System.out.println("4. Credit Risk Report (Customers with overdue invoices)");
            System.out.println("5. Plan Recommendations");
            System.out.println("6. Back to Admin Menu");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": showTopNDataUsers(); break;
                case "2": showSystemArpu(); break;
                case "3": showOverageDistribution(); break;
                case "4": showCreditRiskReport(); break;
                case "5": showPlanRecommendations(); break;
                case "6": return;
                default: System.err.println("Invalid choice.");
            }
        }
    }

    private static void loginUser() throws SubscriptionNotFoundException, CustomerNotFoundException {
        if (loggedInCustomer != null) {
            System.out.println("A user (" + loggedInCustomer.getName() + ") is already logged in.");
            showUserSessionMenu();
            return;
        }

        System.out.print("Please enter your Customer ID to log in (e.g., C001): ");
        String custId = scanner.nextLine();
        try {
            Customer customer = customerService.getCustomerById(custId);
            loggedInCustomer = customer;
            System.out.println("Welcome, " + loggedInCustomer.getName() + "!");
            showUserSessionMenu();
        } catch (CustomerNotFoundException | SubscriptionNotFoundException e) {
            System.err.println("Login Failed: " + e.getMessage());
        }
    }

    private static void showUserSessionMenu() throws SubscriptionNotFoundException, CustomerNotFoundException {
        while (loggedInCustomer != null) {
            System.out.println("\n--- User Menu (Logged in as " + loggedInCustomer.getName() + ") ---");
            System.out.println("1. View My Subscriptions");
            System.out.println("2. Add a New Subscription (Phone Number)");
            System.out.println("3. Add Usage to a Subscription");
            System.out.println("4. View My Usage for a Month");
            System.out.println("5. View My Invoices");
            System.out.println("6. Generate My Bill for a Month");
            System.out.println("7. Pay an Invoice");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": viewMySubscriptions(loggedInCustomer.getCustId()); break;
                case "2": addSubscription(loggedInCustomer.getCustId()); break;
                case "3": addUserUsage(loggedInCustomer); break;
                case "4": viewMyUsage(loggedInCustomer); break;
                case "5": viewMyInvoices(loggedInCustomer.getCustId()); break;
                case "6": generateMyBill(loggedInCustomer); break;
                case "7": markInvoicePaid(); break;
                case "8":
                    System.out.println("Logging out user " + loggedInCustomer.getName() + ".");
                    loggedInCustomer = null;
                    return;
                default: System.err.println("Invalid choice.");
            }
        }
    }

    // --- ROBUST INPUT HELPER METHODS ---
    private static int promptForInt(String message) {
        System.out.print(message);
        while (true) {
            try { return Integer.parseInt(scanner.nextLine()); }
            catch (NumberFormatException e) { System.err.print("Invalid input. Please enter a whole number: "); }
        }
    }
    private static double promptForDouble(String message) {
        System.out.print(message);
        while (true) {
            try { return Double.parseDouble(scanner.nextLine()); }
            catch (NumberFormatException e) { System.err.print("Invalid input. Please enter a number: "); }
        }
    }
    private static YearMonth promptForYearMonth(String message) {
        System.out.print(message);
        while (true) {
            try { return YearMonth.parse(scanner.nextLine()); }
            catch (DateTimeParseException e) { System.err.print("Invalid format. Please use YYYY-MM format: "); }
        }
    }

    // --- ADMIN METHODS ---
    private static void addCustomer() {
        System.out.print("Enter new customer ID (e.g., C004): ");
        String id = scanner.nextLine();
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        customerService.addCustomer(new Customer(id, name, email));
        System.out.println("Customer added successfully!");
    }
    private static void viewCustomers() {
        System.out.println("\n--- All Customers ---");
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(c -> System.out.printf("ID: %s, Name: %s, Email: %s, Blocked: %s\n", c.getCustId(), c.getName(), c.getEmail(), c.isCreditBlocked()));
    }
    private static void deleteCustomer() {
        System.out.print("Enter the ID of the customer to delete: ");
        String custId = scanner.nextLine();
        try {
            System.out.println("Attempting to delete customer " + custId + "...");
            customerService.deleteCustomer(custId);
            System.out.println("Operation completed.");
        } catch (CustomerNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
    // Admin method for adding a subscription
    private static void addSubscription() {
        System.out.print("Enter Customer ID to add subscription to: ");
        String custId = scanner.nextLine();
        addSubscription(custId);
    }
    private static void addUsage() {
        System.out.print("Enter subscription ID to add usage for: ");
        String subId = scanner.nextLine();
        try {
            double data = promptForDouble("Enter data used (GB): ");
            double voice = promptForDouble("Enter voice minutes used: ");
            Usage usage = new Usage(subId, LocalDateTime.now(), data, voice, 0, false, false);
            usageService.addUsage(usage);
            System.out.println("Usage record added successfully.");
        } catch (SubscriptionNotFoundException | CustomerNotFoundException | CreditBlockedException e) {
            System.err.println("ACTION FAILED: " + e.getMessage());
        }
    }
    private static void generateInvoices() {
        YearMonth billingPeriod = promptForYearMonth("Enter the billing month to generate invoices for (e.g., 2025-08): ");
        System.out.println("Generating invoices for all subscriptions for " + billingPeriod + "...");
        for (Subscription sub : subscriptionDAO.getAllSubscriptions()) {
            try {
                Invoice invoice = billingService.generateInvoiceForSubscription(sub.getSubscriptionId(), billingPeriod);
                if (invoice != null) {
                    System.out.println("  SUCCESS: Processed invoice " + invoice.getInvoiceId() + " for sub " + sub.getSubscriptionId());
                }
            } catch (InvoiceGenerationException e) {
                System.err.println("  ERROR for sub " + sub.getSubscriptionId() + ": " + e.getMessage());
            }
        }
    }
    private static void viewAllInvoices() {
        System.out.println("\n--- All System Invoices ---");
        billingDAO.getAllInvoices().forEach(inv -> System.out.printf("ID: %s, Cust: %s, Phone: %s, Total: %.2f, Status: %s\n", inv.getInvoiceId(), inv.getCustId(), inv.getPhoneNumber(), inv.getGrandTotal(), inv.getPaymentStatus()));
    }
    private static void markInvoicePaid() {
        System.out.print("Enter invoice ID to mark as paid: ");
        String invId = scanner.nextLine();
        billingService.markInvoiceAsPaid(invId);
        System.out.println("Invoice status updated.");
    }

    // --- REPORTS METHODS ---
    private static void showTopNDataUsers() {
        int n = promptForInt("Enter N for Top N users: ");
        System.out.println("\n--- Top " + n + " Data Users ---");
        analyticsService.getTopNDataUsers(n).forEach(entry -> System.out.printf("Subscription ID: %s, Data Used: %.2f GB\n", entry.getKey(), entry.getValue()));
    }
    private static void showSystemArpu() {
        System.out.printf("\nSystem-wide ARPU is: %.2f\n", analyticsService.getSystemArpu());
    }
    private static void showOverageDistribution() {
        System.out.println("\n--- System Overage Analysis ---");
        DoubleSummaryStatistics stats = analyticsService.getOverageDistribution();
        System.out.printf("Total Invoices with Overage: %d\n", stats.getCount());
        System.out.printf("Total Overage Billed: %.2f\n", stats.getSum());
        System.out.printf("Average Overage Charge: %.2f\n", stats.getAverage());
        System.out.printf("Highest Overage Charge: %.2f\n", stats.getMax());
    }
    private static void showCreditRiskReport() {
        System.out.println("\n--- Credit Risk Report ---");
        List<Customer> riskyCustomers = analyticsService.getCreditRiskCustomers();
        if(riskyCustomers.isEmpty()){
            System.out.println("No customers are currently a credit risk.");
        } else {
            riskyCustomers.forEach(c -> System.out.printf("  - ID: %s, Name: %s, Blocked: %s\n", c.getCustId(), c.getName(), c.isCreditBlocked()));
        }
    }
    private static void showPlanRecommendations() {
        System.out.println("\n--- Plan Recommendations ---");
        analyticsService.getPlanRecommendations().forEach((custName, recommendation) -> System.out.printf("For %s: %s\n", custName, recommendation));
    }

    // Shared logic for adding a subscription, used by admin and user
    private static void addSubscription(String custId) {
        System.out.print("Enter the new phone number: ");
        String phone = scanner.nextLine();
        try {
            Subscription newSub = subscriptionService.addSubscription(custId, phone);
            System.out.println("Successfully added subscription " + newSub.getSubscriptionId() + " for customer " + custId);
        } catch (CustomerNotFoundException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
    private static void addUserUsage(Customer customer) throws SubscriptionNotFoundException, CustomerNotFoundException {
        Subscription sub = selectSubscription(customer.getCustId());
        if (sub == null) return;

        System.out.println("Adding usage for phone number: " + sub.getPhoneNumber());
        try {
            double data = promptForDouble("Enter data used (GB): ");
            double voice = promptForDouble("Enter voice minutes used: ");
            Usage usage = new Usage(sub.getSubscriptionId(), LocalDateTime.now(), data, voice, 0, false, false);
            usageService.addUsage(usage);
            System.out.println("Usage record added successfully.");
        } catch (CreditBlockedException e) {
            System.err.println("ACTION FAILED: " + e.getMessage());
        }
    }
    private static void viewMyUsage(Customer customer) {
        Subscription sub = selectSubscription(customer.getCustId());
        if (sub == null) return;
        YearMonth period = promptForYearMonth("Enter the month to view usage for (YYYY-MM): ");
        List<Usage> usages = usageDAO.getUsageBySubscriptionIdAndPeriod(sub.getSubscriptionId(), period);
        System.out.println("\n--- Usage for " + sub.getPhoneNumber() + " for " + period + " ---");
        if (usages.isEmpty()) {
            System.out.println("No usage recorded for this period.");
        } else {
            usages.forEach(u -> System.out.printf("  - On %s, Data: %.2f GB, Voice: %.0f Mins\n", u.getUsageStartTime().toLocalDate(), u.getDataUsedGB(), u.getVoiceUsedMins()));
        }
    }
    private static void generateMyBill(Customer customer) {
        Subscription sub = selectSubscription(customer.getCustId());
        if (sub == null) return;
        YearMonth period = promptForYearMonth("Enter the month to generate the bill for (YYYY-MM): ");
        try {
            Invoice invoice = billingService.generateInvoiceForSubscription(sub.getSubscriptionId(), period);
            if (invoice != null) {
                System.out.println("  SUCCESS: Generated invoice " + invoice.getInvoiceId() + " for phone " + sub.getPhoneNumber());
                System.out.printf("  Total Amount: %.2f\n", invoice.getGrandTotal());
            }
        } catch (InvoiceGenerationException e) {
            System.err.println("  ERROR: " + e.getMessage());
        }
    }
    private static Subscription selectSubscription(String custId) {
        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByCustomer(custId);
        if (subscriptions.isEmpty()) {
            System.out.println("You have no active subscriptions.");
            return null;
        }
        if (subscriptions.size() == 1) {
            return subscriptions.get(0);
        }
        System.out.println("Please select one of your subscriptions:");
        for (int i = 0; i < subscriptions.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, subscriptions.get(i).getPhoneNumber());
        }
        int choice = promptForInt("Enter your choice: ") - 1;
        if (choice >= 0 && choice < subscriptions.size()) {
            return subscriptions.get(choice);
        } else {
            System.err.println("Invalid selection.");
            return null;
        }
    }
    private static void viewMySubscriptions(String custId) {
        System.out.println("\n--- Your Subscriptions ---");
        subscriptionDAO.getSubscriptionsByCustomer(custId).forEach(s -> System.out.printf("ID: %s, Phone: %s, Start Date: %s\n", s.getSubscriptionId(), s.getPhoneNumber(), s.getSubsStartDate().toLocalDate()));
    }
    private static void viewMyInvoices(String custId) {
        System.out.println("\n--- Your Invoices ---");
        billingService.getInvoicesByCustomer(custId).forEach(inv -> System.out.printf("ID: %s, Phone: %s, Bill Date: %s, Total: %.2f, Status: %s\n", inv.getInvoiceId(), inv.getPhoneNumber(), inv.getBillingDate(), inv.getGrandTotal(), inv.getPaymentStatus()));
    }

    // --- SEED DATA ---
    private static void InitializeUsers() throws CustomerNotFoundException {
        // Customer 1: Normal user
        Customer c1 = new Customer("C001", "sathwik", "sathwik@mail.com");
        customerService.addCustomer(c1);
        Subscription s1 = subscriptionService.addSubscription("C001", "9876543210");
        try {
            usageService.addUsage(new Usage(s1.getSubscriptionId(), LocalDateTime.of(2025, 8, 10, 14, 0), 110.0, 50.0, 10, false, false)); // Overage
        } catch(Exception e) { /* ignore in seed */ }

        // Customer 2: Overdue user for credit control test
        Customer c2 = new Customer("C002", "Rithvik", "Rithvik@mail.com");
        customerService.addCustomer(c2);
        Subscription s2 = subscriptionService.addSubscription("C002", "8765432109");
        try {
            usageService.addUsage(new Usage(s2.getSubscriptionId(), LocalDateTime.of(2025, 8, 15, 22, 0), 20.0, 500.0, 50, true, false));
        } catch(Exception e) { /* ignore in seed */ }
        Invoice oldInvoice = new Invoice();
        oldInvoice.setInvoiceId("OLD-001");
        oldInvoice.setCustId("C002");
        oldInvoice.setSubscriptionId(s2.getSubscriptionId());
        oldInvoice.setBillingDate(LocalDate.of(2025, 5, 1)); // More than 60 days ago
        oldInvoice.setGrandTotal(1200.0);
        oldInvoice.setPaymentStatus(Invoice.PaymentStatus.PENDING);
        billingDAO.addInvoice(oldInvoice);

        // Customer 3: Family plan user
        Customer c3 = new Customer("C003", "RJD", "rjd@mail.com");
        c3.setFamilyId("FAM1");
        customerService.addCustomer(c3);
        Subscription s3 = subscriptionService.addSubscription("C003", "7654321098");
        s3.setFamilyId("FAM1");
        subscriptionDAO.updateSubscription(s3);
        try {
            // This user will use a huge amount of data to trigger the family surcharge
            usageService.addUsage(new Usage(s3.getSubscriptionId(), LocalDateTime.of(2025, 8, 20, 11, 0), 150.0, 100.0, 20, false, false));
        } catch(Exception e) { /* ignore in seed */ }

        // Customer 4: Normal user
        Customer c4 = new Customer("C004", "ritesh", "ritesh@mail.com");
        customerService.addCustomer(c4);
        Subscription s4 = subscriptionService.addSubscription("C004", "9876543211");
        try {
            usageService.addUsage(new Usage(s4.getSubscriptionId(), LocalDateTime.of(2025, 8, 10, 14, 0), 210.0, 5030.0, 170, true, false)); // Overage
        } catch(Exception e) { /* ignore in seed */ }

        System.out.println("--- Initial data seeded for demo ---");
    }
}