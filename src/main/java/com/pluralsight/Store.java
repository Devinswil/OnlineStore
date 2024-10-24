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
        // This method should read a CSV file with product information and
        // populate the inventory ArrayList with com.pluralsight.Product objects. Each line
        // of the CSV file contains product information in the following format:
        //
        // id,name,price
        //
        // where id is a unique string identifier, name is the product name,
        // price is a double value representing the price of the product
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
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.

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
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
        totalAmount = 0;
        for (Product product : cart) {
            System.out.println(cart);
            checkOut(cart,totalAmount,scanner);
        }
        if (cart.isEmpty()){
            System.out.println("Nothing in cart!");
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
        totalAmount = 0;
        for (Product product : cart) {
            totalAmount += product.getPrice();

        }
        System.out.println(totalAmount);
        boolean yes = confirmPurchase(scanner);

        if (yes){
            System.out.println("Purchase confirmed");
            cart.clear();
        } else{
            System.out.println("Purchased canceled");
        }

    }

    public static void findProductById(String id, ArrayList<Product> inventory) {
        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding com.pluralsight.Product object. If
        // no product with the specified ID is found, the method should return
        // null.

        boolean found = false;

        for (Product product : inventory) {
            if (product.getId().equals(id)) {
                System.out.println(product);
                found = true;

            }
        }
        if (!found) {
            System.out.println("Id not found!");
        }
    }

    public static boolean confirmPurchase(Scanner scanner) {
        System.out.println("Would you like to confirm purchase? yes or no?");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equalsIgnoreCase("yes");
    }
}
