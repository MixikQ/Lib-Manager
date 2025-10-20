
import java.util.Scanner;

public class LibManager {
    public static void main(String[] args) {
        Scanner InputScanner = new Scanner(System.in);
        PathFileManager PFManager = new PathFileManager(InputScanner);
        Library BookLibrary = PFManager.ReadLogfile();
        Menu menu = new Menu(BookLibrary, PFManager);
        while (true) { 
            menu.PrintCurrentPage();
        }
    }

}
