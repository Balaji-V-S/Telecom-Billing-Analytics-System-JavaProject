package com.tabs.ui;

import com.tabs.models.*;
import com.tabs.services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TABSapp {

    private static final Scanner scanner = new Scanner(System.in);

    // Services
    private static final CustomerService customerService = new CustomerServiceImpl();
    private static final SubscriptionService subscriptionService = new SubscriptionServiceImpl();
    private static final BillingService billingService = new BillingServiceImpl();
    private static final AnalyticsService analyticsService = new AnalyticsServiceImpl();

    // Stub for plan fetching - implement PlanService or similar in real app
    private static Plan getPlanById(String planId) {
        // TODO: Replace this stub with actual PlanService lookup
        Plan p = new Plan();
        p.setPlanId(planId);
        p.setPlanName("Basic Plan");
        p.setMonthlyRental(50.0);
        p.setDataAllowanceGB(100.0);
        p.setVoiceAllowedMins(500.0);
        p.setSmsAllowed(100);
        p.setDataOverageRatePerGB(10.0);
        p.setVoiceOverageRatePerMin(0.5);
        p.setSmsOveragePerSMS(0.2);
        return p;
    }

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
                case "1": showAdminMenu(); break;
                case "2": showUserMenu(); break;
                case "3":
                    System.out.println("Exiting application. Goodbye!");
                    return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

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
                case "1": addCustomerUI(); break;
                case "2": viewCustomersUI(); break;
                case "3": System.out.println("[TODO] Add Plans"); break; // Not implemented yet
                case "4": viewSubscriptionsUI(); break;
                case "5": addSubscriptionUI(); break;
                case "6": addUsageUI(); break;
                case "7": viewUsageUI(); break;
                case "8": generateInvoiceUI(); break;
                case "9": viewInvoicesUI(); break;
                case "10": markInvoicePaidUI(); break;
                case "11": showReportsMenu(); break;
                case "12":
                    System.out.println("Logging out from Admin Menu...");
                    return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

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
                case "1": topNDataUsersUI(); break;
                case "2": arpuByPlanUI(); break;
                case "3": overageAnalysisUI(); break;
                case "4": creditRiskReportUI(); break;
                case "5": System.out.println("[TODO] Plan Recommendations"); break;
                case "6": return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

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
                case "1": viewCustomersUI(); break; // reuse admin method
                case "2": viewSubscriptionsUI(); break;
                case "3": addUsageUI(); break;
                case "4": viewUsageUI(); break;
                case "5": viewInvoicesUI(); break;
                case "6": markInvoicePaidUI(); break;
                case "7":
                    System.out.println("Logging out from User Menu...");
                    return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCustomerUI() {
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer();
        customer.setCustId(custId);
        customer.setName(name);
        customer.setEmail(email);

        customerService.addCustomer(customer);
        System.out.println("Customer added successfully!");
    }

    private static void viewCustomersUI() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void addSubscriptionUI() {
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();
        System.out.print("Enter Subscription ID: ");
        String subId = scanner.nextLine();
        System.out.print("Enter Plan ID: ");
        String planId = scanner.nextLine();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        Subscription subscription = new Subscription();
        subscription.setCustId(custId);
        subscription.setSubscriptionId(subId);
        subscription.setPlanId(planId);
        subscription.setPhoneNumber(phone);
        subscription.setSubsStartDate(LocalDateTime.now());

        subscriptionService.addSubscription(subscription);

        // Add phone number to customer if not already present
        Customer customer = customerService.getCustomerById(custId);
        if (customer != null && !customer.getPhoneNumbers().contains(phone)) {
            customer.getPhoneNumbers().add(phone);
            customerService.updateCustomer(customer);
        }

        System.out.println("Subscription assigned successfully!");
    }

    private static void viewSubscriptionsUI() {
        List<Subscription> subs = subscriptionService.getAllSubscriptions();
        if (subs.isEmpty()) {
            System.out.println("No subscriptions found.");
        } else {
            subs.forEach(System.out::println);
        }
    }

    private static void addUsageUI() {
        System.out.print("Enter Subscription ID: ");
        String subId = scanner.nextLine();

        Usage usage = new Usage();
        usage.setSubscriptionId(subId);
        usage.setUsageStartTime(LocalDateTime.now());

        System.out.print("Enter Data Used (GB): ");
        usage.setDataUsedGB(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter Voice Used (Mins): ");
        usage.setVoiceUsedMins(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter SMS Used: ");
        usage.setSmsUsed(Integer.parseInt(scanner.nextLine()));

        Customer subOwner = customerService.getAllCustomers().stream()
                .filter(c -> subscriptionService.getSubscriptionsByCustomer(c.getCustId()).stream()
                        .anyMatch(s -> s.getSubscriptionId().equals(subId)))
                .findFirst()
                .orElse(null);

        if (subOwner != null) {
            String phone = subscriptionService.getSubscriptionById(subId).getPhoneNumber();
            customerService.addUsage(subOwner.getCustId(), phone, usage);
            System.out.println("Usage recorded successfully!");
        } else {
            System.out.println("Subscription not found!");
        }
    }

    private static void viewUsageUI() {
        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        List<Usage> usageList = customerService.getUsageByNumber(custId, phone);
        if (usageList.isEmpty()) {
            System.out.println("No usage found.");
        } else {
            usageList.forEach(System.out::println);
        }
    }

    private static void generateInvoiceUI() {
        System.out.print("Enter Invoice ID: ");
        String invId = scanner.nextLine();

        System.out.print("Enter Customer ID: ");
        String custId = scanner.nextLine();

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        Subscription sub = subscriptionService.getSubscriptionByPhoneNumber(phone);
        if (sub == null) {
            System.out.println("Subscription not found for phone number.");
            return;
        }

        Plan plan = getPlanById(sub.getPlanId());

        List<Usage> usages = customerService.getUsageByNumber(custId, phone);
        double totalDataUsed = usages.stream().mapToDouble(Usage::getDataUsedGB).sum();
        double totalVoiceUsed = usages.stream().mapToDouble(Usage::getVoiceUsedMins).sum();
        int totalSmsUsed = usages.stream().mapToInt(Usage::getSmsUsed).sum();

        double dataOverage = Math.max(0, totalDataUsed - plan.getDataAllowanceGB()) * plan.getDataOverageRatePerGB();
        double voiceOverage = Math.max(0, totalVoiceUsed - plan.getVoiceAllowedMins()) * plan.getVoiceOverageRatePerMin();
        double smsOverage = Math.max(0, totalSmsUsed - plan.getSmsAllowed()) * plan.getSmsOveragePerSMS();

        double baseFare = plan.getMonthlyRental() != null ? plan.getMonthlyRental() : 0.0;
        double overageFare = dataOverage + voiceOverage + smsOverage;

        double subTotal = baseFare + overageFare;
        double gst = subTotal * 0.18; // example GST 18%
        double grandTotal = subTotal + gst;

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invId);
        invoice.setCustId(custId);
        invoice.setSubscriptionId(sub.getSubscriptionId());
        invoice.setPhoneNumber(phone);
        invoice.setBillingDate(LocalDate.now());
        invoice.setBaseFare(baseFare);
        invoice.setOverageFare(overageFare);
        invoice.setRoamingCharges(0.0); // add roaming logic if needed
        invoice.setDiscount(0.0);
        invoice.setSubTotal(subTotal);
        invoice.setGst(gst);
        invoice.setGrandTotal(grandTotal);
        invoice.setPaymentStatus(Invoice.PaymentStatus.PENDING);

        billingService.addInvoice(invoice);
        customerService.addInvoice(custId, phone, invoice);

        System.out.println("Invoice generated!");
    }

    private static void viewInvoicesUI() {
        List<Invoice> invoices = billingService.getAllInvoices();
        if (invoices.isEmpty()) {
            System.out.println("No invoices found.");
        } else {
            invoices.forEach(System.out::println);
        }
    }

    private static void markInvoicePaidUI() {
        System.out.print("Enter Invoice ID: ");
        String invId = scanner.nextLine();

        Invoice invoice = billingService.getInvoiceById(invId);
        if (invoice != null) {
            invoice.setPaymentStatus(Invoice.PaymentStatus.PAID);
            billingService.updateInvoice(invoice);
            System.out.println("Invoice marked as PAID.");
        } else {
            System.out.println("Invoice not found!");
        }
    }

    // Reports UI

    private static void topNDataUsersUI() {
        System.out.print("Enter N: ");
        int n = Integer.parseInt(scanner.nextLine());

        List<Usage> allUsage = customerService.getAllCustomers().stream()
                .flatMap(c -> c.getUsageByNumber().values().stream().flatMap(List::stream))
                .toList();

        List<String> topUsers = analyticsService.getTopNDataUsers(allUsage, n);
        System.out.println("Top " + n + " Data Users: " + topUsers);
    }

    private static void arpuByPlanUI() {
        Map<String, Double> arpu = analyticsService.getArpuByPlan(billingService.getAllInvoices());
        arpu.forEach((plan, value) -> System.out.println("Plan/Subscription: " + plan + " | ARPU: " + value));
    }

    private static void overageAnalysisUI() {
        Map<String, Double> overages = analyticsService.getOverageCharges(billingService.getAllInvoices());
        overages.forEach((cust, value) -> System.out.println("Customer: " + cust + " | Overage Charges: " + value));
    }

    private static void creditRiskReportUI() {
        List<String> riskyCustomers = analyticsService.getCreditRiskCustomers(billingService.getAllInvoices());
        System.out.println("Customers at Credit Risk: " + riskyCustomers);
    }
}
