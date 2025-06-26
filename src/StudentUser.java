public class StudentUser extends User {

    private static final int MAX_BOOKS = 2;

    public StudentUser(int id, String name) {
        super(id, name);
    }

    @Override
    public boolean borrowBook(Book book) {
        if (borrowedBooks.size() >= MAX_BOOKS || book.isIssued()) {
            return false;
        }
        borrowedBooks.add(book);
        book.issueBook();
        return true;
    }

    @Override
    public boolean returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.returnBook();
            return true;
        }
        return false;
    }
}
