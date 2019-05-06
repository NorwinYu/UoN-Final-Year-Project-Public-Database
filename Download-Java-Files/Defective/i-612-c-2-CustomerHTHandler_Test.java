import usps.Customer;
import usps.CustomerHTHandler;
import java.util.Scanner;

public class CustomerHTHandler_Test {
    public static void main(String[] args) {
        String endOfDay = "SomeTypePassword"; // when entered, signals no more customers
        // boolean stillOpen = true;

        String userName;
        String userContact;
        int userTaskTime;
        int numCustomer = 1;


        Scanner reader = new Scanner(System.in);

        CustomerHTHandler hh = new CustomerHTHandler(); 

        while (true) {
            System.out.println("Enter name: ");
            userName = reader.nextLine();
            System.out.println("Enter phone or email: ");
            userContact = reader.nextLine();
            System.out.println("Choose your task (add # minutes for now): ");
            userTaskTime = reader.nextInt();

            if (userName == "SomeTypePassword")
            {
                break;
            }

            Customer newCustomer = new Customer(userName, userContact, 
                userTaskTime, numCustomer);

System.out.println("Customer object created");
            hh.addCustomer(newCustomer, hh);
System.out.println("Customer obj added to table");
            numCustomer++;
        }

    }
}