
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PathFileManager {

    public PathFileManager(Scanner sc) {
        this.InScan = sc;
    }

    private final Scanner InScan;
    private final static String LOG_PATH = "LibManagerLogs.txt";

    public Library ReadLogfile() {
        Library BookLibrary = new Library();
        if (IsFileExists(LOG_PATH)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(LOG_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] temp = line.split(" ### "); // Info in Logfile stores as: Title ### Author ### ReleaseYear ### Genre ### Path
                    Book book = new Book(temp[0], temp[1], Integer.parseInt(temp[2]), temp[3], temp[4]);
                    // Asking for new Path
                    if (!IsFileExists(book.GetPath()) && !book.GetPath().equals("Lost")) {
                        System.out.println("Book \"" + book.PrintBook() + "\" has lost, please update Path or type 0 to ignore...");
                        try {
                            String NewPath = Input();
                            if (NewPath.equals("0") || !IsFileExists(NewPath)) {
                                System.out.println("You can edit Path anytime in Edit Menu");
                                NewPath = "Lost";
                            } 
                            book.SetPath(NewPath);
                        } catch (Exception e) {
                            System.out.println("Something went wrong! You can edit Path anytime in Edit Menu");
                            book.SetPath("Lost");
                        }
                    }
                    BookLibrary.AddBook(book);
                }
            } catch (IOException e) {
                System.out.println("IO Exception | " + e);
            }
        }
        return BookLibrary;
    }

    public void WriteLogFile(Library BookLibrary) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_PATH))) {
            for (Book book : BookLibrary.GetBookList()) {
                writer.write(book.FormatToLogfile());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("IO Exception | " + e);
        }
    }

    public boolean IsFileExists(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.getName().toLowerCase().endsWith(".txt");
    }
    public String Input() {
        return InScan.nextLine();
    }
}
