import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        // Load CSV data
        library.loadBooksFromCSV("data/books.csv");
        library.loadUsersFromCSV("data/users.csv");

        while (true) {
            System.out.println("\n📚 Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add User");
            System.out.println("3. View Books");
            System.out.println("4. View Users");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author Name: ");
                    String author = sc.nextLine();

                    Book book = new Book(id, title, author);
                    library.addBook(book);
                    System.out.println("✅ Book added.");
                }

                case 2 -> {
                    System.out.print("Enter User ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter User Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter User Type (1 = Student, 2 = Staff): ");
                    int type = sc.nextInt();

                    User user = (type == 1) ? new StudentUser(id, name) : new StaffUser(id, name);
                    library.addUser(user);
                    System.out.println("✅ User added.");
                }

                case 3 -> {
                    System.out.println("\n📚 Book List:");
                    for (Book book : library.getBooks()) {
                        System.out.println(book);
                    }
                }

                case 4 -> {
                    System.out.println("\n👤 User List:");
                    for (User user : library.getUsers()) {
                        System.out.println(user);
                        for (Book b : user.getBorrowedBooks()) {
                            System.out.println("   → " + b);
                        }
                    }
                }

                case 5 -> {
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();

                    boolean success = library.issueBook(userId, bookId);
                    System.out.println(success ? "✅ Book issued." : "❌ Failed to issue.");
                }

                case 6 -> {
                    System.out.print("Enter User ID: ");
                    int userId = sc.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = sc.nextInt();

                    boolean success = library.returnBook(userId, bookId);
                    System.out.println(success ? "✅ Book returned." : "❌ Failed to return.");
                }

                case 7 -> {
                    // Save CSVs on exit
                    library.saveBooksToCSV("data/books.csv");
                    library.saveUsersToCSV("data/users.csv");

                    System.out.println("👋 Exiting Library System...");
                    sc.close();
                    return;
                }

                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }
}
