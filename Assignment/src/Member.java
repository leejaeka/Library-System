import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Member {

    // This user's name
    private String username;
    // This user's checkout books
    private ArrayList<Time> checkedOut;
    // This user's books on waitlist
    private ArrayList<Book> waitlist;
    // This user's account balance
    private int balance;

    Member(String username) {
        this.username = username;
        checkedOut = new ArrayList<>();
        waitlist = new ArrayList<>();
        balance = 0;
    }

    // Check due dates and add penalty fees accordingly
    void automaticSetBalance(Date date) {
        for (Time time : checkedOut) {
            if (time.hasExpired()) {
                long payment = Main.date.getTime() - time.getExpiryDate().getTime();
                if (date != null) {
                    payment = Math.min(Main.date.getTime() - date.getTime(),
                            Main.date.getTime() - time.getExpiryDate().getTime());
                }
                long numDays = TimeUnit.DAYS.convert(payment, TimeUnit.MILLISECONDS);
                balance += numDays * 0.5;
            }
        }
    }

    // Check user's status
    void checkStatus() {
        // check for overdue
        if (!checkedOut.isEmpty()) {
            for (Time time : checkedOut) {
                if (time.hasExpired()) {
                    System.out.println("***You have an overdue title: " + time.getBook());
                } else if (time.expireIn() < 3) {
                    System.out.println("***You have a book title: " + time.getBook() + " due within 2 days");
                }
            }
        } else {
            System.out.println("***You have no items checked out");
        }
        // check for waitlist
        if (!waitlist.isEmpty()) {
            for (Book book : waitlist) {
                if (book.isAvailable()) {
                    System.out.println("***Cheers! '" + book + "' is now available!!");
                }
            }
        } else {
            System.out.println("***You have no items in waitlist");
        }
        // check balance
        System.out.println("***Your current balance is: $" + balance);
    }

    // Search for book in the library
    void search() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter the book title: ");
        String title = (reader.nextLine()).trim();

        // Get book of the title if it exists
        Book selectedBook = Main.library.getBook(title);

        // Book found
        if (selectedBook != null) {
            // AND book is available
            if (selectedBook.isAvailable()) {
                System.out.println(selectedBook.getnumCopiesAvailable() +
                        " copy/copies of \"" + selectedBook + "\" are available at " + selectedBook.getBranch() + " library");
                System.out.print("Would you like to checkout " + selectedBook + "? (1) Yes (0) No : ");

                String checkout = reader.nextLine();

                if (checkout.equals("1")) {
                    checkoutBook(selectedBook);
                }
            } // BUT book not available
            else {
                System.out.println("Unfortunately, " + selectedBook + " is currently unavailable");
                System.out.print("Would you like to add it on your waitlist? - (1) Yes (0) No : ");
                String answer;
                do {
                    answer = reader.next();
                } while (isNumeric(answer));
                if (Integer.valueOf(answer) == 1) {
                    waitlist.add(selectedBook);
                    System.out.println("You have been added to our waitlist for " + selectedBook);
                }
            }
        } //Book not found
        else {
            System.out.println("Nothing found for given title. Please try again");
        }
    }

    //Checkout book from system
    private void checkoutBook(Book book) {
        Time time = new Time(book, Main.date);
        book.setnumCopiesAvailable(book.getnumCopiesAvailable() - 1);
        this.checkedOut.add(time);
        System.out.println("Successfully checked out: '" + book + "' Due date is " + time.getExpiryDate());
    }

    // manage post-checkout options
    // (i.e. return/extend)
    void manageCheckouts() {
        Scanner reader = new Scanner(System.in);
        if (checkedOut.size() == 0) {
            System.out.println("You have no items checked out!");
        } else {
            int count = 1;
            for (Time time : checkedOut) {
                System.out.println("(" + count + ") " + time);
                count++;
            }

            System.out.print("Select the book you'd like to manage -(type the number beside the title): ");
            String selection;
            do {
                selection = reader.next();
            } while (isNumeric(selection));

            Time selectedtime = Integer.valueOf(selection) <= count ? checkedOut.get(Integer.valueOf(selection) - 1) : null;
            if (selectedtime == null) {
                System.out.println("Invalid option. Please try again");
            } else {
                if (selectedtime.extendable()) {
                    System.out.print("Select your interest- (1) Return the book (2) Extend the book: ");
                    do {
                        selection = reader.next();
                    } while (isNumeric(selection));
                    if (Integer.valueOf(selection) == 1) {
                        returnBook(selectedtime);
                    } else if (Integer.valueOf(selection) == 2) {
                        extendBook(selectedtime);
                    } else {
                        System.out.println("Invalid input. Please try again");
                    }
                } else {
                    System.out.print("Item is not extendable. Would you like to return - (1) Yes (0) No : ");
                    do {
                        selection = reader.next();
                    } while (isNumeric(selection));
                    if (Integer.valueOf(selection) == 1) {
                        returnBook(selectedtime);
                    }
                }
            }
        }
    }

    // Return a book
    private void returnBook(Time time) {
        Book book = time.getBook();
        book.setnumCopiesAvailable((book.getnumCopiesAvailable() + 1));
        this.checkedOut.remove(time);
        System.out.println("You have successfully return " + book + "!");
    }

    // Extend due date on a book
    private void extendBook(Time time) {
        time.setHasExtended();
        LocalDate newExpiryDate = time.getlocalDate().plusDays(Library.CHECKOUT_DURATION);
        Date newExpiryDate1 = java.sql.Date.valueOf(newExpiryDate);
        time.setExpiryDate(newExpiryDate1);
    }

    void waitlist() {
        Scanner reader = new Scanner(System.in);

        // No waitlist
        if (waitlist.size() == 0) {
            System.out.println("You have no items on waitlist");
        } else {

            int count = 1;
            for (Book book : waitlist) {
                String status = book.isAvailable() ? "Available" : "Unavailable";
                System.out.println("(" + count + ") " + " Title: " + book +
                        "\n     Branch: " + book.getBranch() + "\n     Status: " + status + "\n" +
                        "\n     Number of available copies: " + book.getnumCopiesAvailable());
                count++;
            }

            System.out.print("Select which book you'd like to manage - (type the number beside the title): ");
            String selection;
            do {
                selection = reader.next();
            } while (isNumeric(selection));
            Book selectedBook = Integer.valueOf(selection) <= count ? waitlist.get(Integer.valueOf(selection) - 1) : null;
            if (selectedBook == null) {
                System.out.println("Invalid selection. Please try again");
            } else {

                // If the selected book is available
                if (selectedBook.isAvailable()) {
                    System.out.print(selectedBook + " is available... select - (1) Checkout (2) Remove from waitlist : ");
                    selection = reader.next();

                    // Checkout and remove from waitlist
                    if (Integer.valueOf(selection) == 1) {
                        checkoutBook(selectedBook);
                        waitlist.remove(selectedBook);

                        // Remove from waitlist and do not checkout
                    } else if (Integer.valueOf(selection) == 2) {
                        waitlist.remove(selectedBook);

                    } else {
                        System.out.println("Invalid option. Please try again");
                    }

                } else {
                    System.out.print("All copies of " + selectedBook + " are in use. " +
                            "Would you like to remove yourself from waitlist - (1) Yes (2) No : ");

                    selection = reader.next();
                    if (Integer.valueOf(selection) == 1) {
                        waitlist.remove(selectedBook);

                    }
                }
            }
        }
    }

    // Setter
    void setBalance(int newBalance) {
        this.balance = newBalance;
    }

    // Getters
    ArrayList<Book> getWaitlist() {
        return waitlist;
    }

    String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username;
    }

    // Helper function to prevent crashes due to
    // users inputting not integers when asked
    private boolean isNumeric(String strNum) {
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return true;
        }
        return false;
    }
}