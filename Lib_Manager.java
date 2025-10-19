
import java.util.ArrayList;
import java.util.Scanner;

public class Lib_Manager {
    public static void main(String[] args) {
        Scanner InputScanner = new Scanner(System.in);
        ArrayList<String> BookList = new ArrayList<>();
        BookList.add("hi");
        BookList.add("hello");
        Menu menu = new Menu(BookList, InputScanner);
        while (true) { 
            menu.PrintCurrentPaage();
        }
    }
}
