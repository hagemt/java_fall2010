import java.sql.*;
import java.util.*;

public class MurachDB
{
    private static Connection connection = null;

    private static void setDbDirectory()
    {
        String dbDirectory = "c:/java1.6/ch22/DBTester";
        System.setProperty("derby.system.home", dbDirectory);
    }

    public static Connection connect()
    {
        // Add the derby.jar file to the class path or build path
        // C:\Program Files\Java\jdk1.6.0\db\lib\derby.jar

        // the statement that follows isn't necessary
        // if the database is going to be stored
        // in the same directory as the application

        // setDbDirectory();

        try
        {
            // set the db url string
            String dbUrl = "jdbc:derby:MurachDB;create=true";

			// create a Properties object with username and password
			Properties properties = new Properties();
			properties.put("user", "");
			properties.put("password", "");

			// create and return the connection
			Connection connection = DriverManager.getConnection(dbUrl, properties);
            return connection;
        }
        catch (SQLException e)
        {
			for (Throwable t : e)
            	t.printStackTrace();
            return null;
        }
    }

    public static boolean disconnect()
    {
        try
        {
            // On a successful shutdown, this throws an exception
            String shutdownURL = "jdbc:derby:;shutdown=true";
            DriverManager.getConnection(shutdownURL);
        }
        catch (SQLException e)
        {
            if (e.getMessage().equals("Derby system shutdown."))
                return true;
        }
        return false;
    }

    public static boolean productsTableExists(Connection connection)
    {
        String selectProductsString =
              "SELECT COUNT(ProductCode) FROM Products";

        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectProductsString);
            rs.next();
            statement.close();
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    public static boolean createProductsTable(Connection connection)
    {
        String createProductsString =
              "CREATE TABLE Products ( "
            + "ProductCode VARCHAR(10), "
            + "Description VARCHAR(50), "
            + "Price DOUBLE "
            + ")";

        try
        {
            Statement statement = connection.createStatement();
            statement.execute(createProductsString);
            statement.close();
            // System.out.println("Products table was created.\n");
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertProducts(Connection connection)
    {
        String insertProductString1 =
              "INSERT INTO Products "
            + "VALUES ('java', 'Murach''s Beginning Java 2', 49.50)";

        String insertProductString2 =
              "INSERT INTO Products "
            + "VALUES ('jsps', 'Murach''s Java Servlets and JSP', 49.50)";

        try
        {
            Statement statement = connection.createStatement();
            statement.execute(insertProductString1);
            statement.execute(insertProductString2);
            statement.close();
            // System.out.println("2 products were inserted.\n");
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean dropProductsTable(Connection connection)
    {
        String dropProductsString =
              "DROP TABLE Products";

        try
        {
            Statement statement = connection.createStatement();
            statement.execute(dropProductsString);
            // System.out.println("Products table was dropped.\n");
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

}