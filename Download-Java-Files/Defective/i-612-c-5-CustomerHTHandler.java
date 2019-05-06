package usps;

import java.util.*;
import usps.Customer;
import usps.WaitTime;

public class CustomerHTHandler {

    String keyCustomerID;
    Integer valTaskTime;
    Map<String, Integer> cht = new Hashtable<>();

    public CustomerHTHandler() {
        Map<String, Integer> cht = new Hashtable<>();
    }

    public Map<String, Integer> getCHT() {
        return cht;
    }

    public int addCustomer(Customer nextCust, CustomerHTHandler hh) {
      //  System.out.println("calling put for HT");
        keyCustomerID = nextCust.getName();
        valTaskTime = nextCust.getTaskTime();
      //  System.out.println("passing name: " + keyCustomerID);
      //  System.out.println("passing taskTime: " + valTaskTime);

        hh.cht.put(keyCustomerID, valTaskTime);
      //  System.out.println("customer added to CHT, returning time");
        return nextCust.getTaskTime();
    }

    public int removeCustomer(Customer processedCust) {
        cht.remove(processedCust.getName());
        return processedCust.getTaskTime();
    }

    public void endOfDay() {
        cht.clear();
    }

}