import java.util.ArrayList;

class Library {

    // Default time for due
    static int CHECKOUT_DURATION = 14;
    ArrayList<Member> members;
    ArrayList<Book> books;

    Library() {
        // all books
        books = new ArrayList<>();
        // all members
        members = new ArrayList<>();
    }

    // add book to library
    void addBooks(Book newBook) {
        books.add(newBook);
    }

    // add member to library
    void addMember(Member newMember) {
        members.add(newMember);
    }

    // get book given its title
    // returns null if not found
    Book getBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // get member given his/her name
    // return null if not found
    Member getMember(String username) {
        for (Member member : members) {
            if (member.getUsername().equals(username)) {
                return member;
            }
        }
        return null;
    }
}