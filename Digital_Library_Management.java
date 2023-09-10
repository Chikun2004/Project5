package Task5;

import java.util.ArrayList;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Book {
    public String name;
    public String author;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

class Library {
    protected ArrayList<Book> books;
    protected ArrayList<User> users;
    protected User loggedInUser;

    public Library(ArrayList<Book> books, ArrayList<User> users) {
        this.books = books;
        this.users = users;
        this.loggedInUser = null;
    }

    // for Admin 

    public void addBook(Book book) {
        if (loggedInUser != null && loggedInUser instanceof Admin) {
            System.out.println("The book has been added to the library");
            this.books.add(book);
        } else {
            System.out.println("You don't have permission to add a book. Please login as Admin.");
        }
    }

    public void removeBook(Book book) {
        if (loggedInUser != null && loggedInUser instanceof Admin) {
            System.out.println("The book has been removed from the library");
            this.books.remove(book);
        } else {
            System.out.println("You don't have permission to remove a book. Please login as Admin.");
        }
    }

    public void updateBook(Book book, String newName, String newAuthor) {
        if (loggedInUser != null && loggedInUser instanceof Admin) {
            book.name = newName;
            book.author = newAuthor;
            System.out.println("The book details have been updated");
        } else {
            System.out.println("You don't have permission to update a book. Please login as Admin.");
        }
    }

    // for User module 

    public void loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful. Welcome, " + username + "!");
                return;
            }
        }
        System.out.println("Invalid username or password. Login failed.");
    }

    public void searchBook(String bookName) 
    {
        if (loggedInUser != null) 
        {  
            System.out.println("Results for book: " + bookName);
          
        } else {
            System.out.println("You are not logged in. Please login first.");
        }
    }

    public void issueBook(Book book) 
    {
        if (loggedInUser != null && loggedInUser instanceof User) 
        {
            System.out.println("The book " + book.name + " has been issued to " + loggedInUser.getUsername());
            books.remove(book);
        } else {
            System.out.println("You are not logged in as a user. Please login as a user.");
        }
    }

    public void returnBook(Book book) 
    {
        if (loggedInUser != null && loggedInUser instanceof User)
        {
            System.out.println("The book " + book.name + " has been returned");
            books.add(book);
        } else {
            System.out.println("You are not logged in as a user. Please login as a user.");
        }
    }

    public void logout()
    {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }
}

class Admin extends User 
{
    public Admin(String username, String password) 
    {
        super(username, password);
    }
}

public class Digital_Library_Management 
{
    public static void main(String[] args) 
    {
        ArrayList<Book> books = new ArrayList<>();
        Book b1 = new Book("Algorithms", "Roman Reigns");
        books.add(b1);
        Book b2 = new Book("Head First Java", "Seth ROllins");
        books.add(b2);
        Book b3 = new Book("Elegant Objects", "Dean Ambrose");
        books.add(b3);

        ArrayList<User> users = new ArrayList<>();
        User admin = new Admin("admin", "admin@123");
        users.add(admin);
        User user = new User("user", "user@123");
        users.add(user);

        Library library = new Library(books, users);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) 
        {
            System.out.println("---- Digital Library Management ----");
            System.out.println("1. Admin Login");
            System.out.println("2. User Login");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Add Book (Only for Admin)");
            System.out.println("7. Update Book (Only for Admin)");
            System.out.println("8. Remove Book (Only for Admin)");
            System.out.println("9. Logout");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) 
            {
                case 1:
                    System.out.print("Enter admin username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    library.loginUser(adminUsername, adminPassword);
                    break;
                case 2:
                    System.out.print("Enter user username: ");
                    String userUsername = scanner.nextLine();
                    System.out.print("Enter user password: ");
                    String userPassword = scanner.nextLine();
                    library.loginUser(userUsername, userPassword);
                    break;
                case 3:
                    System.out.print("Enter book name: ");
                    String bookName = scanner.nextLine();
                    library.searchBook(bookName);
                    break;
                case 4:
                    if (library.loggedInUser != null && library.loggedInUser instanceof User) 
                    {
                        System.out.print("Enter the name of the book to be issued: ");
                        String bookToIssue = scanner.nextLine();
                        for (Book book : books) 
                        {
                            if (book.name.equalsIgnoreCase(bookToIssue)) 
                            {
                                library.issueBook(book);
                                break;
                            }
                        }
                    } else {
                        System.out.println("~!!You are not logged in as a user. Please login as a user.");
                    }
                    break;
                case 5:
                    if (library.loggedInUser != null && library.loggedInUser instanceof User) {
                        System.out.print("Enter the name of the book to be returned: ");
                        String bookToReturn = scanner.nextLine();
                        for (Book book : books) {
                            if (book.name.equalsIgnoreCase(bookToReturn))
                            {
                                library.returnBook(book);
                                break;
                            }
                        }
                    }
                    else 
                    {
                        System.out.println("~!!You are not logged in as a user. Please login as a user.");
                    }
                    break;
                case 6:
                    if (library.loggedInUser != null && library.loggedInUser instanceof Admin) 
                    {
                        System.out.print("Enter the name of the book to add: ");
                        String bookToAdd = scanner.nextLine();
                        System.out.print("Enter the author of the book: ");
                        String authorToAdd = scanner.nextLine();
                        library.addBook(new Book(bookToAdd, authorToAdd));
                    } else {
                        System.out.println("~!!You are not logged in as an admin. Please login as an admin.");
                    }
                    break;
                case 7:
                    if (library.loggedInUser != null && library.loggedInUser instanceof Admin) 
                    {
                        System.out.print("Enter the name of the book to update: ");
                        String bookToUpdate = scanner.nextLine();
                        System.out.print("Enter the new name of the book: ");
                        String newBookName = scanner.nextLine();
                        System.out.print("Enter the new author of the book: ");
                        String newAuthorName = scanner.nextLine();
                        for (Book book : books) 
                        {
                            if (book.name.equalsIgnoreCase(bookToUpdate)) 
                            {
                                library.updateBook(book, newBookName, newAuthorName);
                                break;
                            }
                        }
                    } else {
                        System.out.println("~!!You are not logged in as an admin. Please login as an admin.");
                    }
                    break;
                case 8:
                    if (library.loggedInUser != null && library.loggedInUser instanceof Admin) 
                    {
                        System.out.print("Enter the name of the book to remove: ");
                        String bookToRemove = scanner.nextLine();
                        for (Book book : books) {
                            if (book.name.equalsIgnoreCase(bookToRemove)) 
                            {
                                library.removeBook(book);
                                break;
                            }
                        }
                    } else {
                        System.out.println("~!!You are not logged in as an admin. Please login as an admin.");
                    }
                    break;
                case 9:
                    library.logout();
                    break;
                case 10:
                    exit = true;
                    break;
                default:
                    System.out.println("~~!! Invalid choice. Please try again !!~~");
                    break;
            }
        }

        scanner.close();
    }
}