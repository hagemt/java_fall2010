import java.util.Scanner;

public class RandomMaintApp
{
    // declare class variable
    private static Scanner sc = null;

    public static void main(String args[])
    {
        // display a welcome message
        System.out.println("Welcome to the Random-Access File Maintenance application\n");

        // set the class variable
        sc = new Scanner(System.in);

        // display the command menu
        displayMenu();

        // perform 1 or more actions
        String action = "";
        while (!action.equalsIgnoreCase("exit"))
        {
            // get the input from the user
            action = Validator.getString(sc,
                "Enter a command: ");
            System.out.println();

            if (action.equalsIgnoreCase("commit"))
                commitDeletions();
            else if (action.equalsIgnoreCase("help") || action.equalsIgnoreCase("menu"))
                displayMenu();
            else if (action.equalsIgnoreCase("exit"))
                System.out.println("Bye.\n");
            else
                System.out.println("Error! Not a valid command.\n");
        }
    }

    public static void displayMenu()
    {
        System.out.println("COMMAND MENU");
        System.out.println("commit  - Make deletions permanent");
        System.out.println("help    - Show this menu");
        System.out.println("exit    - Exit this application\n");
    }

    public static void commitDeletions()
    {
    }
}