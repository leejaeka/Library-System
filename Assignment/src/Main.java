import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static Date date;
    static Library library = new Library();

    // Main screen for staff member
    private static void staffLogin() {
        while (true) {
            System.out.print("\nSelect (1) Register new book (2) Update a book. (3) Pay penalties (4) Adjust balance (5) Check Library Status (0) Logout: ");
            Scanner reader = new Scanner(System.in);
            String option = reader.next();
            Staff staff = new Staff();

            switch (option) {
                case "1":
                    staff.addNewBook();
                    break;
                case "2":
                    staff.updateBook();
                    break;
                case "3":
                    staff.pay();
                    break;
                case "4":
                    staff.adjustBalance();
                    break;
                case "5":
                    staff.checkLibraryStatus();
                    break;
                case "0":
                    System.out.println("\nLogged out successfully\n\n");
                    return;
                default:
                    System.out.println("Invalid input. Please try again");
            }
        }
    }

    // Main screen for members once + log in
    private static void memberLogin() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = (reader.nextLine()).trim();
        Member currentMember = library.getMember(username);

        // If such username doesn't exist create new username with it + log in
        // Otherwise find the user from library and log them in
        if (currentMember != null) {
            System.out.println("Login success! Welcome back " + currentMember.getUsername() + "!");
        } else {
            currentMember = new Member(username);
            library.members.add(currentMember);
            System.out.println("Created user named '" + username + "'. Please use this username again for future login");
        }

        boolean flag = true;
        // Login complete.. List options for member user and take them to appropriate next step
        while (true) {
            // This flag prevents two operations below repeating every time we comeback to logged in screen
            if (flag) {
                // System will automatically penalize overdue on their balance
                currentMember.automaticSetBalance(date);
                // System will let user know important news
                currentMember.checkStatus();
                flag = false;
            }
            System.out.print("Select your interest (1) Search/Checkout a book (2) Check status (3) Return/extend book (4) Waitlist (0) Logout: ");
            String option = reader.next();
            switch (option) {
                case "1":
                    currentMember.search();
                    break;
                case "2":
                    currentMember.checkStatus();
                    break;
                case "3":
                    currentMember.manageCheckouts();
                    break;
                case "4":
                    currentMember.waitlist();
                    break;
                case "0":
                    System.out.println("\nLogged out successfully\n\n");
                    return;
                default:
                    System.out.println("Invalid input. Please try again");
            }
        }
    }


    public static void main(String[] args) {

        // Create some default books&members for the library. (for testing)
        // Book 'a' to 'j' with identical descriptions, title, branch,
        // number of copies available would be 0 to 10 respectively.
        // Member 'a' to 'j' (as username)
        // Comment it out or uncomment it out appropriately
        int j = 0;
        for (char i = 'a'; i < 'k'; i++) {
            Book newBook = new Book(Character.toString(i), Character.toString(i), j, Character.toString(i));
            Member newMember = new Member(Character.toString(i));
            library.addBooks(newBook);
            library.addMember(newMember);
            j++;
        }

        Scanner reader = new Scanner(System.in);
        //  this flag used so we can just ask the date once
        boolean flag = false;
        while (true) {
            // Begin interaction with user
            System.out.println("Welcome to our library!!");

            // Gather information about date once
            while (!flag) {
                System.out.print("Enter the date(yyyy mm dd): ");
                String inputDate = reader.nextLine();
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");

                try {
                    date = format.parse(inputDate);
                    flag = true;
                    break;
                } catch (ParseException e) {
                    System.out.println("Invalid input. Please try again");
                }
            }

            // Select user type.
            String answer;
            do {
                System.out.print("Please identify yourself (1) Member (2) Staff ||| (3) Exit Program: ");
                answer = reader.next();
                if (answer.equals("1") || answer.equals("2")) {
                    break;
                } else if (answer.equals("3")) {
                    return;
                }
                System.out.println("Invalid input. Please try again");
            } while (true);

            // This is where users login
            if (answer.equals("1")) {
                memberLogin();
            } else {
                staffLogin();
            }
        }
    }

}