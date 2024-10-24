package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online com.pluralsight.Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.add(new Product(id, name, price));

                }
            }

        } catch (Exception e) {
            System.out.println("File Error");
            e.printStackTrace();
        }
    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {

        for (Product product : inventory) {
            System.out.println(product);
        }
        System.out.println("Would you like to add anything to the cart? yes or no?");
        String answer = scanner.nextLine().trim().toLowerCase();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("Enter Item id");
            String itemId = scanner.nextLine();
            boolean found = false;
            for (Product product : inventory) {
                if (product.getId().equals(itemId)) {
                    cart.add(product);
                    System.out.println("Product has been added to the cart!");
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Item not found.");
            }
        } else {
            System.out.println("Okay");
        }
    }


    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {

        for (Product product : cart) {
            System.out.println(product);
            totalAmount += product.getPrice();


        }
        //.isEmpty checks to see if array list has anything in it
        if (cart.isEmpty()) {
            System.out.println("Nothing in cart!");
            return;
        }
        System.out.printf("Cart Total: $%.2f%n", totalAmount);
        System.out.println("Would you like to remove items from cart? yes or no");
        String answer = scanner.nextLine().toLowerCase().trim();
        if (answer.equalsIgnoreCase("yes")) {
            System.out.println("What is the ID of the item you would like to remove?");
            String removeItem = scanner.nextLine();
            Product productToRemove = findProductById(removeItem, cart);
            //!= null because we set null if product id was not able to be found
            if (productToRemove != null) {
                cart.remove(productToRemove);
               // totalAmount -=productToRemove.getPrice();
                System.out.println("Product removed from cart.");

            } else {
                System.out.println("Item not found.");
            }
        } else if (answer.equalsIgnoreCase("no")) {
            System.out.println("No items removed from the cart.");
        }

        checkOut(cart, totalAmount, scanner);
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {


        //System.out.println(totalAmount);
        boolean yes = confirmPurchase(scanner);

        if (yes) {
            System.out.println("Purchase confirmed " + totalAmount + " has been deducted from account");
            cart.clear();
        } else {
            System.out.println("Purchased canceled");
        }

    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {


        //return product if the ids match
        for (Product product : inventory) {
            if (product.getId().equals(id)) {
                System.out.println(product);
                return product;

            }
        }

        System.out.println("Id not found!");
        return null;
    }

    public static boolean confirmPurchase(Scanner scanner) {
        //added method because there was no scanner in checkOut
        System.out.println("Would you like to confirm purchase? yes or no?");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equalsIgnoreCase("yes");
    }
}
