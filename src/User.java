import java.util.ArrayList;

public abstract class User {
    protected int id;
    protected String name;
    protected ArrayList<Book> borrowedBooks;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Abstract methods to implement in subclasses
    public abstract boolean borrowBook(Book book);
    public abstract boolean returnBook(Book book);

    @Override
    public String toString() {
        return "[" + id + "] " + name + " | Borrowed: " + borrowedBooks.size() + " books";
    }
}
