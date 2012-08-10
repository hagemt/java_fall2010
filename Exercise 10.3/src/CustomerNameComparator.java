import java.util.Comparator;

public class CustomerNameComparator implements Comparator<Customer> {
	@Override
	public int compare(Customer c1, Customer c2) {
		int last_name_comparison = c1.getLastName().compareToIgnoreCase(c2.getLastName());
		if (last_name_comparison == 0) {
			return c1.getFirstName().compareToIgnoreCase(c2.getFirstName());
		}
		return last_name_comparison;
	}
}
