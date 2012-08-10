import java.util.Scanner;

public class DAOMaintApp
{
    public static void main(String args[])
    {
        // display a welcome message
        System.out.println("Welcome to the DAO Maintenance application \n");

        // create a DAOFile object

        // perform 1 or more actions
        Scanner sc = new Scanner(System.in);
        String action = "y";
        while (action.equalsIgnoreCase("y"))
        {
            // display the name of the current DAO object

            // get the input from the user
            action = Validator.getString(sc,
                "Do you want to change this? (y/n): ");
            System.out.println(action);

            if (action.equalsIgnoreCase("y"))
            {
                String newDAOName = Validator.getString(sc,
                    "Enter the name of a valid ProductDAO class: ");

                // change the name of the current DAO object

                System.out.println(newDAOName);
            }
        }
    }
}