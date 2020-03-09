import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;


class Time {

    private Book book;
    private LocalDate localDate;
    private Date expiryDate;
    private boolean hasExtended;

    Time(Book book, Date todayDate) {
        this.book = book;
        LocalDate todayDate1 = todayDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = todayDate1.plusDays(Library.CHECKOUT_DURATION);
        this.expiryDate = java.sql.Date.valueOf(localDate);
        this.hasExtended = false;
    }

    // Getters
    Book getBook() {
        return book;
    }

    Date getExpiryDate() {
        return expiryDate;
    }

    LocalDate getlocalDate() {
        return localDate;
    }

    // Setters
    void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    void setHasExtended() {
        this.hasExtended = true;
    }

    // To check availability for extension
    boolean extendable() {
        return !book.isWaitlisted() && !hasExtended;
    }

    // Returns time it takes until overdue
    long expireIn() {
        long expiredInMillie = expiryDate.getTime() - Main.date.getTime();
        return TimeUnit.DAYS.convert(expiredInMillie, TimeUnit.MILLISECONDS);
    }

    // Returns whether overdue
    boolean hasExpired() {
        return expireIn() <= 0;
    }

    @Override
    public String toString() {
        String expiry = hasExpired() ? "Expired" : String.valueOf(expireIn());
        String extension = extendable() ? "Extendable" : "Not extendable";
        return "Title: " + book.getTitle() + "\n    Expiratory date: " +
                getExpiryDate().toString() + "\n    Expiration: " + expiry + " days" +
                "\n    Extension: " + extension;
    }
}