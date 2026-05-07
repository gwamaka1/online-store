
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
        String input = scanner.nextLine();
        boolean found = false;
        for (Product p: inventory){
            if(input.equalsIgnoreCase(p.getId())){
                System.out.println("we found the product "+ p.getName()+".... adding to cart");
                cart.add(p);
                found = true;

            }

        }
        if(!found){
            System.out.println("couldnt find productu");
            return;
        }



        // TODO: show each product (id, name, price),
        //       prompt for an id, find that product, add to cart
    }

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
    public static void checkOut(ArrayList<Product> cart,
                                double totalAmount,
                                Scanner scanner) {
        System.out.println("confirm if you want");
    }

    /**
     * Searches a list for a product by its id.
     *
     * @return the matching Product, or null if not found
     */
    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // TODO: loop over the list and compare ids
        return null;
    }
}

