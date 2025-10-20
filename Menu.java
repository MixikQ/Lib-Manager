
import java.util.ArrayList;

public class Menu {

    public Menu(Library l, PathFileManager pfm) {
        this.CurrentPage = 99;
        this.BookList = l;
        this.PFManager = pfm;
    }

    private int CurrentPage;
    private Library BookList;
    private final PathFileManager PFManager;

    public int GetCurrentPage() {
        return CurrentPage;
    }
    public void SetCurrentPage(int x) {
        CurrentPage = x;
    }

    private void PrintAvailCommands() {
        System.out.println("Available commands:");
        System.out.println("\s\s1 - Show list of your books");
        System.out.println("\s\s2 - Add new book");
        System.out.println("\s\s3 - Edit book");
        System.out.println("\s\s4 - Remove book");
        System.out.println("\n\s\s0 - Exit...");
    }
    private void Exit() {
        System.exit(0);
    }
    
    public void PrintBookList(ArrayList<Book> books) {
        System.out.println("\nHere is your books:");
        if (!books.isEmpty()) {
            for (int i = 0; i < books.size(); ++i) {
                System.out.println("(" + (i + 1) + ") " + books.get(i).PrintBook() + " (Path: " + books.get(i).GetPath() + ")");
            }
        } else {
            System.out.println("\s\sIt's empty here :(");
        }
        System.out.println("===================");
    }

    public void PrintListPage() {
        PrintBookList(BookList.GetBookList());
        PrintAvailCommands();
        System.out.println("Type number of needed command:");
        SetCurrentPage(Integer.parseInt(PFManager.Input()));
    }

    public void PrintStartPage() {
        System.out.println("\tWelcome to Lib Manager!");
        PrintListPage();
    }

    // Add Book with validating
    public Library PrintAddPage() {
        String Path;
        String Title;
        String Author;
        int ReleaseYear;
        String Genre;
        System.out.println("To add a book type path to your book (only .txt supports)");
        System.out.println("Type 0 to return to list page...");
        Path = PFManager.Input();
        while (!PFManager.IsFileExists(Path) && !Path.equals("0") && !Path.equals("Lost")) {
            System.out.println("Wrong path! Type correct path (\"Lost\" to add without path) or type 0 to cancel...");
            Path = PFManager.Input();
        }
        if (Path.equals("0")) {
            SetCurrentPage(1);
            return BookList;
        }
        System.out.println("Type book's Title:");
        Title = PFManager.Input();
        System.out.println("Type book's Author:");
        Author = PFManager.Input();
        System.out.println("Type book's Release Year:");
        System.out.println("If unknown, type negative number");
        while (true) {
            try {
            ReleaseYear = Integer.parseInt(PFManager.Input());
            break;
            } catch (NumberFormatException e) {
                System.err.println("Number Format Exception | " + e);
                System.out.println("Not a number, type year again:");
            }
        }
        System.out.println("Type book Genre:");
        Genre = PFManager.Input();
        Book book = new Book(Title, Author, ReleaseYear, Genre, Path);
        BookList.AddBook(book);
        System.out.println("Book \"" + book.PrintBook() + "\" added to the library!");
        System.out.println("\nYou can add another book below");
        return BookList;
    }

    // Edit Book with validating
    public void PrintEditPage() {
        PrintBookList(BookList.GetBookList());
        System.out.println("To edit book, type it's ID or type 0 to return to List page:");
        int In;
        while (true) {
            try {
            In = Integer.parseInt(PFManager.Input());
            if (In < 0 || In > BookList.GetSize()) {
                System.out.println("Wrong number, type ID again:");
                continue;
            }
            if (In == 0) {
                SetCurrentPage(1);
                return;
            }
            break;
            } catch (NumberFormatException e) {
                System.err.println("Number Format Exception | " + e);
                System.out.println("Not a number, type ID again:");
            }
        }
        Book book = BookList.GetBookList().get(In - 1);
        while (GetCurrentPage() != 1) {
            System.out.println("\nChosen book:");
            System.out.println(book.PrintBook() + " (" + book.GetPath() + ")");
            System.out.println("===================");
            System.out.println("Available parametrs to change:");
            System.out.println("\s\s1 - Title");
            System.out.println("\s\s2 - Author");
            System.out.println("\s\s3 - Release Year");
            System.out.println("\s\s4 - Genre");
            System.out.println("\s\s5 - Path");
            System.out.println("\n\s\s0 - Return to list page");
            System.out.println("Type Parametr number:");
            while (true) {
                try {
                In = Integer.parseInt(PFManager.Input());
                if (In < 0 || In > 5) {
                    System.out.println("Wrong number, type Parametr number again:");
                    continue;
                }
                break;
                } catch (NumberFormatException e) {
                    System.err.println("Number Format Exception | " + e);
                    System.out.println("Not a number, type Parametr number again:");
                }
            }
            switch (In) {
                case 0 -> {
                    SetCurrentPage(1);
                }
                case 1 -> {
                    System.out.println("Type new title:");
                    String Title = PFManager.Input();
                    book.SetTitle(Title);
                }
                case 2 -> {
                    System.out.println("Type new author");
                    String Author = PFManager.Input();
                    book.SetAuthor(Author);
                }
                case 3 -> {
                    System.out.println("Type new release year:");
                    int ReleaseYear;
                    while (true) {
                        try {
                        ReleaseYear = Integer.parseInt(PFManager.Input());
                        if (ReleaseYear < 0) {
                            System.out.println("Release year set to \"Unknown\"");
                        } 
                        break;
                        } catch (NumberFormatException e) {
                            System.err.println("Number Format Exception | " + e);
                            System.out.println("Not a number, type Release Year again:");
                        }
                    }
                    book.SetReleaseYear(ReleaseYear);
                }
                case 4 -> {
                    System.out.println("Type new genre:");
                    String Genre = PFManager.Input();
                    book.SetGenre(Genre);
                }
                case 5 -> {
                    System.out.println("Type new path or \"Lost\" to remove path:");
                    String Path = PFManager.Input();
                    while (!PFManager.IsFileExists(Path) && !Path.equals("Lost")) {
                        System.out.println("Wrong Path! Type Path again or type \"Lost\" to remove path:");
                        Path = PFManager.Input();
                    }
                    book.SetPath(Path);
                }
            }
        }
    }

    // Remove Book with confirmation
    public Library PrintRemovePage() {
        PrintBookList(BookList.GetBookList());
        System.out.println("Type book ID you want to remove or type 0 to return to list page:");
        int In;
        while (true) {
            try {
            In = Integer.parseInt(PFManager.Input());
            if (In < 0 || In > BookList.GetSize()) {
                System.out.println("Wrong number, type ID again:");
                continue;
            }
            break;
            } catch (NumberFormatException e) {
                System.err.println("Number Format Exception | " + e);
                System.out.println("Not a number, type ID again:");
            }
        }
        if (In == 0) {
            SetCurrentPage(1);
            return BookList;
        }
        BookList.RemoveBook(In - 1);
        return BookList;
    }

    // public void SortLibrary() {
    //     BookList.GetBookList().sort();
    // } 

    public void PrintCurrentPage() {
        switch (CurrentPage) {
            case 0 -> { // Exit
                Exit();
            }
            case 1 -> { // List
                PrintListPage();
            }
            case 2 -> { // Add
                BookList = PrintAddPage();
                PFManager.WriteLogFile(BookList);
            }
            case 3 -> { // Edit
                PrintEditPage();
                PFManager.WriteLogFile(BookList);
            }
            case 4 -> { // Remove
                BookList = PrintRemovePage();
                PFManager.WriteLogFile(BookList);
            }
            case 99 -> { // Start
                PrintStartPage();
            }
            default -> {
                PrintListPage();
            }
        }
    }
}
