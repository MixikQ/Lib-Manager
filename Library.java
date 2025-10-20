
import java.util.ArrayList;

public class Library {

    public Library(ArrayList<Book> b) {
        this.BookList = b;
    }
    public Library() {
        this.BookList = new ArrayList<>();
    }

    private final ArrayList<Book> BookList;
    public ArrayList<Book> GetBookList() {
        return BookList;
    }
    public void AddBook(Book book) {
        BookList.add(book);
    }
    public void RemoveBook(int id) {
        BookList.remove(id);
    }

    public int GetSize() {
        return BookList.size();
    }
}
