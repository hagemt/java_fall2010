import java.util.*;
import java.sql.*;

public class DBTesterApp
{
    private static Connection connection = null;

    public static void main(String args[])
    {
        // set the connection
        connection = MurachDB.connect();
        if (connection != null)
			System.out.println("Derby has been started.\n");

        // if necessary, create the Products table
        if (!MurachDB.productsTableExists(connection))
        {
	        MurachDB.createProductsTable(connection);
	        MurachDB.insertProducts(connection);
        }

        printProducts();
        printFirstProduct();
        printLastProduct();
        printProductByCode("java");

        Product p = new Product("test", "Test Product", 49.50);
        insertProduct(p);
        printProducts();
        deleteProduct(p);
        printProducts();

        // disconnect from the database
		if (MurachDB.disconnect())
        	System.out.println("Derby has been shut down.\n");
    }

    public static void printProducts()
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Products");
            Product p = null;

            System.out.println("Product list:");
            while(rs.next())
            {
                String code = rs.getString("ProductCode");
                String description = rs.getString("Description");
                double price = rs.getDouble("Price");

                p = new Product(code, description, price);

                printProduct(p);
            }
            System.out.println();
        }
        catch(SQLException e)
        {
            e.printStackTrace();  // for debugging
        }
    }

    public static void printFirstProduct()
    {
        Product p = new Product();

        // add code that prints the record for the first product in the Products table
        try {
        	Statement statement = connection.createStatement();
        	ResultSet rs = statement.executeQuery("SELECT * FROM Products");
        	if (!rs.isLast()) {
        		p.setCode(rs.getString("ProductCode"));
        		p.setDescription(rs.getString("Description"));
        		p.setPrice(new Integer(rs.getString("Price")));
        	}
        } catch (SQLException e) {
        	System.out.println(p.getCode() + " could not be processed!");
        }

        System.out.println("First product:");
        printProduct(p);
        System.out.println();
    }

    public static void printLastProduct()
    {
        Product p = new Product();

        // add code that prints the record for the last product in the Products table
        try {
        	Statement statement = connection.createStatement();
        	ResultSet rs = statement.executeQuery("SELECT * FROM Products");
        	while (!rs.isLast()) { rs.next(); }
        	p.setCode(rs.getString("ProductCode"));
        	p.setDescription(rs.getString("Description"));
        	p.setPrice(new Integer(rs.getString("Price")));
        } catch (SQLException e) {
        	System.out.println(p.getCode() + " could not be processed!");
        }

        System.out.println("Last product:");
        printProduct(p);
        System.out.println();
    }

    public static void printProductByCode(String productCode)
    {
        System.out.println("Product by code: " + productCode);
        Product p = new Product();

        // add code that prints the product with the specified code
        try {
        	Statement statement = connection.createStatement();
        	ResultSet rs = statement.executeQuery("SELECT * FROM Products WHERE ProductCode='" + productCode + "'");
        	while (rs.next()) {
        		if (rs.getString("ProductCode").equals(productCode)) {
            		p.setCode(productCode);
            		p.setDescription(rs.getString("Description"));
            		p.setPrice(new Integer(rs.getString("Price")));
        		}
        	}
        } catch (SQLException e) {
        	System.out.println(p.getCode() + " could not be processed!");
        }

        if (p != null) {
        	printProduct(p);
        } else {
        	System.out.println(productCode + " was not found!");
        }
        System.out.println();
    }

    public static void insertProduct(Product p)
    {
        System.out.println("Insert test: ");
        
        // add code that inserts the specified product into the database
        // if a product with the specifed code already exists, display an error message
        try {
            connection.createStatement().executeQuery(
            		"INSERT INTO Products (ProductCode, Description, Price) VALUES('" +
            		p.getCode() + "', '" + p.getDescription() + "', '" + p.getPrice() + "' ) '");
        } catch (SQLException e) {
        	System.out.println(p.getCode() + " could not be processed!");
        }

        printProduct(p);
        System.out.println();
    }

    private static void deleteProduct(Product p)
    {
    	System.out.println("Delete test: ");

        // add code that deletes the specified product from the database
        // if a product with the specified code doesn't exist, display an error message
    	try {
            connection.createStatement().executeQuery("DELETE FROM Products WHERE ProductCode='" + p.getCode() + "'");
        } catch (SQLException e) {
        	System.out.println(p.getCode() + " could not be processed!");
        }

        printProduct(p);
        System.out.println();
    }

    // use this method to print a Product object on a single line
    private static void printProduct(Product p)
    {
        String productString =
            StringUtils.padWithSpaces(p.getCode(), 8) +
            StringUtils.padWithSpaces(p.getDescription(), 44) +
            p.getFormattedPrice();

        System.out.println(productString);
    }
}