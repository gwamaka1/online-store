
package com.pluralsight;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Starter code for the Online Store workshop.
 * Students will complete the TODO sections to make the program work.
 */
public class Store {

    public static void main(String[] args) {

        // Create lists for inventory and the shopping cart
        ArrayList<Product> inventory = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();

        // Load inventory from the data file (pipe-delimited: id|name|price)
        loadInventory("products.csv", inventory);

        // Main menu loop
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 3) {
            System.out.println("\nWelcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Please enter 1, 2, or 3.");
                scanner.nextLine();                 // discard bad input
                continue;
            }
            choice = scanner.nextInt();
            scanner.nextLine();                     // clear newline

            switch (choice) {
                case 1 -> displayProducts(inventory, cart, scanner);
                case 2 -> displayCart(cart, scanner);
                case 3 -> System.out.println("Thank you for shopping with us!");
                default -> System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }

    /**
     * Reads product data from a file and populates the inventory list.
     * File format (pipe-delimited):
     * id|name|price
     * <p>
     * Example line:
     * A17|Wireless Mouse|19.99
     */
    public static void loadInventory(String fileName, ArrayList<Product> inventory) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null) {
                String [] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                Product p = new Product(id,name,price);
                inventory.add(p);



            }
    } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Displays all products and lets the user add one to the cart.
     * Typing X returns to the main menu.
     */
    public static void displayProducts(ArrayList<Product> inventory,
                                       ArrayList<Product> cart,
                                       Scanner scanner) {
        System.out.printf("%-12s | %-12s | %10.2s\n", "id","name","price");
        for(Product p: inventory){
            System.out.println(p);
        }
        System.out.println("Type in an id you want to add to cart or else type X to return to main menu");
        String input = scanner.nextLine().trim();

       cart.add(findProductById(input,inventory));




        }




        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart


    /**
     * Shows the contents of the cart, calculates the total,
     * and offers the option to check out.
     */
    public static void displayCart(ArrayList<Product> cart, Scanner scanner) {
        // TODO:
        //   • list each product in the cart
        //   • compute the total cost
        //   • ask the user whether to check out (C) or return (X)
        //   • if C, call checkOut(cart, totalAmount, scanner)
        System.out.printf("%24s\n","CART");
        double totalCost = 0;
        for(Product p: cart){
            System.out.println(p);
           totalCost += p.getPrice();


        }
        System.out.printf("$%.2f\n",totalCost);
        boolean running = true;

        while(running) {
            Scanner scan = new Scanner(System.in);
            System.out.println("do you want  to checkout(c)? or return(x)");
            String input = scan.nextLine().trim();
            if (input.equalsIgnoreCase("c")) {
                checkOut(cart, totalCost, scanner);
                running = false;
            } else if (input.equalsIgnoreCase("x")) {
                System.out.println("Returning.....");
                running = false;
                return;

            } else {
                System.out.println("Invalid option.");

            }
        }
    }

    /**
     * Handles the checkout process:
     * 1. Confirm that the user wants to buy.
     * 2. Accept payment and calculate change.
     * 3. Display a simple receipt.
     * 4. Clear the cart.
     */
    public static void checkOut(ArrayList<Product> cart, double totalAmount, Scanner scanner) {
        System.out.println("Confirm if you want to checkout (c), or anything else to cancel:");
        String input = scanner.nextLine().trim();

        if (!input.equalsIgnoreCase("c")) {
            System.out.println("Checkout cancelled. Returning...");
            return;
        }

        System.out.println("PAYMENT:\nENTER AMOUNT:");
        double amountPaid = Double.parseDouble(scanner.nextLine().trim()); // avoid nextDouble() newline bug

        if (amountPaid < totalAmount) {
            System.out.printf("Amount entered ($%.2f) is less than total due ($%.2f).\n", amountPaid, totalAmount);
            return;
        }

        double change = amountPaid - totalAmount;
        System.out.printf("Your change is $%.2f\n", change);
        System.out.printf("%24s\n", "RECEIPT");

        for (Product p : cart) {
            System.out.println(p);
        }

        cart.clear();
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        for (Product p : inventory) {
            if (id.equalsIgnoreCase(p.getId())) {
                System.out.println("We found the product " + p.getName() + ".... adding to cart");
                return p;
            }
        }

        System.out.println("Couldn't find the product");
        return null;
    }
    }

