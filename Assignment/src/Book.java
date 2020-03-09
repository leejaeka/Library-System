public class Book {

    private String title;
    private String description;
    private int numCopiesAvailable;
    private String branch;


    Book(String title, String description, int numCopiesAvailable, String branch) {
        this.title = title;
        this.description = description;
        this.numCopiesAvailable = numCopiesAvailable;
        this.branch = branch;
    }

    // Getters
    String getTitle() {
        return title;
    }

    int getnumCopiesAvailable() {
        return numCopiesAvailable;
    }

    String getBranch() {
        return branch;
    }

    String getDescription() {
        return description;
    }

    //Setters
    void setTitle(String title) {
        this.title = title;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setnumCopiesAvailable(int numCopiesAvailable) {
        this.numCopiesAvailable = numCopiesAvailable;
    }

    void setBranch(String branch) {
        this.branch = branch;
    }

    //Check availability of this book
    boolean isAvailable() {
        return numCopiesAvailable > 0;
    }

    @Override
    public String toString() {
        return title;
    }

    //Check if this book is on hold for someone on wait list
    boolean isWaitlisted() {
        for (Member member : Main.library.members) {
            if (member.getWaitlist().contains(this)) {
                return true;
            }
        }
        return false;
    }
}