
import java.util.ArrayList;

public class Menu {

    public Menu(Library l, PathFileManager pfm) {
        this.CurrentPage = 99;
        this.BookLib = l;
        this.PFManager = pfm;
    }

    private int CurrentPage;
    private Library BookLib;
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
        System.out.println("\s\s5 - Sort library");
        System.out.println("\s\s6 - Find book in library");
        System.out.println("\n\s\s0 - Exit...");
    }

    private void Exit() {
        System.exit(0);
    }

    public int ChooseOption(int min, int max) {
        int In;
        while (true) {
            try {
            In = Integer.parseInt(PFManager.Input());
            if (In < min || In > max) {
                System.out.println("Wrong number, type option number again:");
                continue;
            }
            break;
            } catch (NumberFormatException e) {
                System.err.println("Number Format Exception | " + e);
                System.out.println("Not a number, type option number again:");
            }
        }
        return In;
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
        PrintBookList(BookLib.GetBookList());
        PrintAvailCommands();
        System.out.println("Type number of needed command:");
        SetCurrentPage(ChooseOption(0, 6));
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
        System.out.println("To add a book type path to your book (only .txt supports) or \"Lost\" to add book without path");
        System.out.println("Type 0 to return to list page...");
        Path = PFManager.Input();
        while (!PFManager.IsFileExists(Path) && !Path.equals("0") && !Path.equals("Lost")) {
            System.out.println("Wrong path! Type correct path (\"Lost\" to add without path) or type 0 to cancel...");
            Path = PFManager.Input();
        }
        if (Path.equals("0")) {
            SetCurrentPage(1);
            return BookLib;
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
            if (ReleaseYear < 0) {
                ReleaseYear = -1;
            }
            break;
            } catch (NumberFormatException e) {
                System.err.println("Number Format Exception | " + e);
                System.out.println("Not a number, type year again:");
            }
        }
        System.out.println("Type book Genre:");
        Genre = PFManager.Input();
        Book book = new Book(Title, Author, ReleaseYear, Genre, Path);
        BookLib.AddBook(book);
        System.out.println("Book \"" + book.PrintBook() + "\" added to the library!");
        System.out.println("\nYou can add another book below");
        return BookLib;
    }

    // Edit Book with validating
    public void PrintEditPage() {
        PrintBookList(BookLib.GetBookList());
        System.out.println("To edit book, type it's ID or type 0 to return to List page:");
        int In = ChooseOption(0, BookLib.GetSize());
        if (In == 0) {
            SetCurrentPage(1);
            return;
        }
        Book book = BookLib.GetBookList().get(In - 1);
        while (GetCurrentPage() != 1) {
            System.out.println("\nChosen book:");
            System.out.println(book.PrintBook() + " (" + book.GetPath() + ")");
            System.out.println("===================");
            System.out.println("Available option to change:");
            System.out.println("\s\s1 - Title");
            System.out.println("\s\s2 - Author");
            System.out.println("\s\s3 - Release Year");
            System.out.println("\s\s4 - Genre");
            System.out.println("\s\s5 - Path");
            System.out.println("\n\s\s0 - Return to list page");
            System.out.println("Type option number:");
            In = ChooseOption(0, 5);
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

    // Remove Book without confirmation
    public Library PrintRemovePage() {
        PrintBookList(BookLib.GetBookList());
        System.out.println("Type book ID you want to remove or type 0 to return to list page:");
        int In = ChooseOption(0, BookLib.GetSize()); 
        if (In == 0) {
            SetCurrentPage(1);
            return BookLib;
        }
        BookLib.RemoveBook(In - 1);
        return BookLib;
    }

    // Sort library with chosen method
    public void PrintSortingPage() {
        PrintBookList(BookLib.GetBookList());
        System.out.println("Availiable options to sort by:");
        System.out.println("\s\s1 - Title (A-Z)");
        System.out.println("\s\s2 - Author (A-Z)");
        System.out.println("\s\s3 - Release Year (Newest First)");
        System.out.println("\s\s4 - Genre (A-Z)");
        System.out.println("\s\s5 - Reversed Title (Z-A)");
        System.out.println("\s\s6 - Reversed Author (Z-A)");
        System.out.println("\s\s7 - Reversed Release Year (Unknown/Oldest First)");
        System.out.println("\s\s8 - Reversed Genre (Z-A)");
        System.out.println("\n\s\s0 - Return to list page");
        System.out.println("Type option number:");
        int In = ChooseOption(0, 8);
        switch (In) {
            case 1 -> {
                BookLib.SortLibrary(Sortings.TITLE);
            }
            case 2 -> {
                BookLib.SortLibrary(Sortings.AUTHOR);
            }
            case 3 -> {
                BookLib.SortLibrary(Sortings.YEAR);
            }
            case 4 -> {
                BookLib.SortLibrary(Sortings.GENRE);
            }
            case 5 -> {
                BookLib.SortLibrary(Sortings.REVERSED_TITLE);
            }
            case 6 -> {
                BookLib.SortLibrary(Sortings.REVERSED_AUTHOR);
            }
            case 7 -> {
                BookLib.SortLibrary(Sortings.REVERSED_YEAR);
            }
            case 8 -> {
                BookLib.SortLibrary(Sortings.REVERSED_GENRE);
            }
        }
        SetCurrentPage(1);
    }

    public void PrintFindingPage() {
        System.out.println("Type string you want to find or hit Enter to return to list page:");
        String In = PFManager.Input();
        if (In.equals("")) {
            SetCurrentPage(1);
            return;
        }
        ArrayList<Integer> FoundBooks = BookLib.FindBook(In);
        if (!FoundBooks.isEmpty()) {
            System.out.println("Found books:");
            for (int item : FoundBooks) {
                Book TempBook = BookLib.GetBookList().get(item);
                System.out.println("(" + (item + 1) + ") " + TempBook.PrintBook() + " (Path: " + TempBook.GetPath() + ")"); 
            }
        } else {
            System.out.println("Nothing is found :(");
        }
        System.out.println("Hit Enter to return to list page...");
        PFManager.Input();
        SetCurrentPage(1);

    }

    public void PrintCurrentPage() {
        switch (CurrentPage) {
            case 0 -> { // Exit
                Exit();
            }
            case 1 -> { // List
                PrintListPage();
            }
            case 2 -> { // Add
                BookLib = PrintAddPage();
                PFManager.WriteLogFile(BookLib);
            }
            case 3 -> { // Edit
                PrintEditPage();
                PFManager.WriteLogFile(BookLib);
            }
            case 4 -> { // Remove
                BookLib = PrintRemovePage();
                PFManager.WriteLogFile(BookLib);
            }
            case 5 -> { // Sorting
                PrintSortingPage();
                PFManager.WriteLogFile(BookLib);
            }
            case 6 -> {
                PrintFindingPage();
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
