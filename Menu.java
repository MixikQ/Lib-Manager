
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public Menu(ArrayList<String> l, Scanner sc) {
        this.CurrentPage = 99;
        this.BookList = l;
        this.InScan = sc;
    }

    private int CurrentPage;
    private ArrayList<String> BookList;
    final private Scanner InScan;

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
    private String Input() {
        return InScan.nextLine();
    }
    
    public void PrintBookList(ArrayList<String> books) {
        System.out.println("Here is your books:");
        if (!books.isEmpty()) {
            for (int i = 0; i < books.size(); ++i) {
                System.out.println("(" + (i + 1) + ") " + books.get(i));
            }
        } else {
            System.out.println("\s\sIt's empty here :(");
        }
    }
    public void PrintStartPage() {
        System.out.println("\tWelcome to Lib Manager!");
        PrintBookList(BookList);
        PrintAvailCommands();
        System.out.println("Type number of needed command:");
        SetCurrentPage(Integer.parseInt(Input()));
    }
    public void PrintListPage() {
        PrintBookList(BookList);
        PrintAvailCommands();
        System.out.println("Type number of needed command:");
        SetCurrentPage(Integer.parseInt(Input()));
    }
    public ArrayList<String> PrintAddPage() {
        System.out.println("To add a book type path to your book");
        System.out.println("Type 0 to cancel...");
        String In = Input();
        if (In.equals("0")) {
            SetCurrentPage(1);
            return BookList;
        }
        ///
        return BookList;
    }
    public void PrintEditPage() {


    }
    public ArrayList<String> PrintRemovePage() {
        PrintBookList(BookList);
        System.out.println("Type book number you want to remove");
        System.out.println("Type 0 to return to list page...");
        int In;
        try {
            In = Integer.parseInt(Input());
        } catch (NumberFormatException e) {
            System.out.println("Something went wrong, book won't be removed");
            System.err.println("Number Format Exception | " + e);
            In = 0;
            try {
                System.in.read();
            } catch (IOException ex) {}
        }
        if (In <= 0) {
            SetCurrentPage(1);
            return BookList;
        }
        if (In > BookList.size()) {
            System.out.println("\n\tWrong number, book won't be removed\n");
            return BookList;
        }
        BookList.remove(In - 1);
        return BookList;
    }

    public void PrintCurrentPaage() {
        switch (CurrentPage) {
            case 0 -> { // Exit
                Exit();
            }
            case 1 -> { // List
                PrintListPage();
            }
            case 2 -> { // Add
                BookList = PrintAddPage();
            }
            case 3 -> { // Edit
                PrintEditPage();
            }
            case 4 -> { // Remove
                BookList = PrintRemovePage();
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
