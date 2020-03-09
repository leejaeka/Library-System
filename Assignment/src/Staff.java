import java.util.Scanner;

class Staff {

    // Staff can register new book to library
    void addNewBook() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the title: ");
        String title = (reader.nextLine()).trim();

        //check if book exists
        Book book = Main.library.getBook(title);

        // If book exists ask
        if (book != null) {
            while (true) {
                System.out.print("This book already exists. Would you like to update its fields? (1) Yes (0) No ");
                String update = reader.next();
                if (update.equals("1")) {
                    updateBook();
                    break;
                } else if (update.equals("2")) {
                    return;
                }
                System.out.println("Invalid input. Please try again");
            }
        } else {
            // Ask for information about the new book
            System.out.print("Enter the description: ");
            String description = (reader.nextLine()).trim();
            System.out.print("Enter the number of copies: ");
            String field;
            do {
                field = reader.next();
            } while (isNumeric(field));
            int numCopies = Integer.parseInt(field);
            reader.nextLine();
            System.out.print("Enter the branch: ");
            String branch = (reader.nextLine()).trim();
            Book newBook = new Book(title, description, numCopies, branch);
            Main.library.addBooks(newBook);
            System.out.println("You have successfully added " + title + " to the " + branch + " library");
        }
    }

    // update Book
    void updateBook() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the title of the book to update: ");
        String title = (reader.nextLine()).trim();
        Book book = Main.library.getBook(title);

        // First check if this book exists
        if (book == null) {
            System.out.print("The book does not exist. Please try again");
        } else {
            String field;
            System.out.print("Would you like to update - (1) Title. (2) Description (3) Number of Copies Available (4) Branch: ");
            do {
                field = reader.next();
            } while (isNumeric(field));
            switch (field) {
                case "1":
                    updateTitle(book);
                    break;
                case "2":
                    updateDescription(book);
                    break;
                case "3":
                    updateNumCopies(book);
                    break;
                default:
                    updateBranch(book);
                    break;
            }
        }
    }

    // Option (1) update title
    private void updateTitle(Book book) {
        Scanner reader = new Scanner(System.in);
        boolean conflict = false;
        while (true) {
            //check if it is same with any other book title
            if (conflict) {
                System.out.print("Conflict with existing book title. Please try a different name: ");
            }
            System.out.print("Enter the title: ");
            String title = (reader.nextLine()).trim();
            conflict = Main.library.getBook(title) != null;
            if (!conflict) {
                book.setTitle(title);
                System.out.println("Successfully changed the title to " + title);
                break;
            }
        }
    }

    // Option (2) update description
    private void updateDescription(Book book) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the description: ");
        String description = (reader.nextLine()).trim();
        book.setDescription(description);
        System.out.println("Successfully changed description!");
    }

    // Option (3) update number of copies available
    private void updateNumCopies(Book book) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the new number of copies available: ");
        String field;
        while(true) {
            do {
                field = reader.next();
            } while (isNumeric(field));
            int numCopies = Integer.parseInt(field);
            if (numCopies >= 0) {
                book.setnumCopiesAvailable(numCopies);
                System.out.println("Successfully changed number of copies to " + numCopies + "!");
                break;
            }
            System.out.println("Please input a number greater than or equal to 0");
        }
    }

    // option (4) updateBranch
    private void updateBranch(Book book) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the branch: ");
        String branch = (reader.nextLine()).trim();
        book.setBranch(branch);
    }

    // Pay for staff means setting any user's balance to 0
    // as they have already payed
    void pay() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the username that you'd like to pay penalties for: ");
        String username = reader.nextLine();
        boolean flag = false;
        for (Member member : Main.library.members) {
            if (member.getUsername().equals(username)) {
                // this member's account is now balanced to 0
                member.setBalance(0);
                System.out.println(member.getUsername() + "'s balance is now 0");
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Non-existing Username. Please try again");
        }
    }

    // Pay for staff means setting any user's balance to 0
    // as they have payed
    void adjustBalance() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the username that you'd like to adjust their balance: ");
        String username = reader.nextLine();
        System.out.print("Enter the new balance for " + username + ": ");
        String newBalance;
        do {
            newBalance = reader.next();
        } while (isNumeric(newBalance));
        boolean flag = false;
        for (Member member : Main.library.members) {
            if (member.getUsername().equals(username)) {
                // this member's account is now balanced to 0
                member.setBalance(Integer.valueOf(newBalance));
                System.out.println(member.getUsername() + "'s balance is now " + newBalance);
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Non-existing Username. Please try again");
        }
    }

    void checkLibraryStatus() {
        if (!Main.library.members.isEmpty()) {
            System.out.println("Here is the list of all the members");
            int i = 1;
            for (Member member : Main.library.members) {
                System.out.println("Member" + i + " : " + member);
                i++;
            }
        } else {
            System.out.println("Currently 0 members");
        }
        if (!Main.library.books.isEmpty()) {
            System.out.println("Here is the list of all the books");
            int i = 1;
            for (Book book : Main.library.books) {
                System.out.println("<" + i + "> : '" + book + "' - " + book.getDescription());
                System.out.println("     Number of copies available: " + book.getnumCopiesAvailable());
                System.out.println("     Location: " + book.getBranch());
                i++;
            }
        } else {
            System.out.println("Currently there are no book");
        }
    }

    // Helper function to prevent crashes due to
    // users inputting not integers when asked
    // cred. to stack overflow
    private boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return true;
        }
        return false;
    }
}