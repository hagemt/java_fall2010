import java.util.Arrays;

public class SortedCustomersApp {
    public static void main(String[] args) {
    	// Create array
    	Customer[] customers = new Customer[3];
    	customers[0] = new Customer("nordhr@rpi.edu", "Rebecca", "Nordhauser");
    	customers[1] = new Customer("hagemt@rpi.edu", "Tor", "Hagemann");
    	customers[2] = new Customer("mehtaa@rpi.edu", "Alok", "Mehta");

    	// Sort by natural ordering, by email
    	Arrays.sort(customers);
    	System.out.println("By email:");
    	for (Customer c : customers) {
    		System.out.println();
    		System.out.println("First name:\t" + c.getFirstName());
    		System.out.println("Last name:\t" + c.getLastName());
    		System.out.println("Email address:\t" + c.getEmail());
    	}
    	System.out.println();
    	
    	// Sort by name
    	Arrays.sort(customers, new CustomerNameComparator());
    	System.out.println("By name:");
    	for (Customer c : customers) {
    		System.out.println();
    		System.out.println("First name:\t" + c.getFirstName());
    		System.out.println("Last name:\t" + c.getLastName());
    		System.out.println("Email address:\t" + c.getEmail());
    	}
    	System.out.println();
    }
}
