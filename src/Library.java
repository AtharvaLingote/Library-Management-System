import java.io.*;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    // ----------------- BOOK OPERATIONS ----------------
    public void addBook(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) return book;
        }
        return null;
    }

    // ---------------- USER OPERATIONS -----------------
    public void addUser(User user) {
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) return user;
        }
        return null;
    }

    public boolean issueBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);
        if (user == null || book == null) return false;
        return user.borrowBook(book);
    }

    public boolean returnBook(int userId, int bookId) {
        User user = findUserById(userId);
        Book book = findBookById(bookId);
        if (user == null || book == null) return false;
        return user.returnBook(book);
    }

    // ---------------- CSV: BOOKS ----------------------

    public void loadBooksFromCSV(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    boolean isIssued = Boolean.parseBoolean(parts[3].trim());

                    Book book = new Book(id, title, author);
                    if (isIssued) book.issueBook();
                    books.add(book);
                }
            }
            System.out.println("📥 Loaded books from CSV.");
        } catch (IOException e) {
            System.out.println("⚠️ Error reading books.csv: " + e.getMessage());
        }
    }

    public void saveBooksToCSV(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                bw.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.isIssued());
                bw.newLine();
            }
            System.out.println("📤 Saved books to CSV.");
        } catch (IOException e) {
            System.out.println("⚠️ Error writing books.csv: " + e.getMessage());
        }
    }

    // ---------------- CSV: USERS ----------------------

    public void loadUsersFromCSV(String filename) {
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String type = parts[2].trim();

                    User user = type.equals("Student") ? new StudentUser(id, name) : new StaffUser(id, name);

                    for (int i = 3; i < parts.length; i++) {
                        int bookId = Integer.parseInt(parts[i].trim());
                        Book book = findBookById(bookId);
                        if (book != null && !book.isIssued()) {
                            user.borrowBook(book);
                        }
                    }

                    users.add(user);
                }
            }
            System.out.println("📥 Loaded users from CSV.");
        } catch (IOException e) {
            System.out.println("⚠️ Error reading users.csv: " + e.getMessage());
        }
    }

    public void saveUsersToCSV(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                StringBuilder sb = new StringBuilder();
                sb.append(user.getId()).append(",").append(user.getName()).append(",");

                sb.append(user instanceof StudentUser ? "Student" : "Staff");

                for (Book book : user.getBorrowedBooks()) {
                    sb.append(",").append(book.getId());
                }

                bw.write(sb.toString());
                bw.newLine();
            }
            System.out.println("📤 Saved users to CSV.");
        } catch (IOException e) {
            System.out.println("⚠️ Error writing users.csv: " + e.getMessage());
        }
    }
}