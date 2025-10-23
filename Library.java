
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

    public void SortLibrary(Sortings e) {
        switch (e) {
            case TITLE -> {
                BookList.sort((Book o1, Book o2) -> o1.GetTitle().compareToIgnoreCase(o2.GetTitle()));
            }
            case REVERSED_TITLE -> {
                BookList.sort((Book o1, Book o2) -> o2.GetTitle().compareToIgnoreCase(o1.GetTitle()));
            }
            case AUTHOR -> {
                BookList.sort((Book o1, Book o2) -> o1.GetAuthor().compareToIgnoreCase(o2.GetAuthor()));
            }
            case REVERSED_AUTHOR -> {
                BookList.sort((Book o1, Book o2) -> o2.GetAuthor().compareToIgnoreCase(o1.GetAuthor()));
            }
            case YEAR -> {
                BookList.sort((Book o1, Book o2) -> o2.GetReleaseYear() - o1.GetReleaseYear());
            }
            case REVERSED_YEAR -> {
                BookList.sort((Book o1, Book o2) -> o1.GetReleaseYear() - o2.GetReleaseYear());
            }
            case GENRE -> {
                BookList.sort((Book o1, Book o2) -> o1.GetGenre().compareToIgnoreCase(o2.GetGenre()));
            }
            case REVERSED_GENRE -> {
                BookList.sort((Book o1, Book o2) -> o2.GetGenre().compareToIgnoreCase(o1.GetGenre()));
            }
        }
        
    } 

    public ArrayList<Integer> FindBook(String s) {
        ArrayList<Integer> FoundBooks = new ArrayList<>();
        for (int i = 0; i < BookList.size(); ++i) {
            Book TempBook = BookList.get(i);
            if (TempBook.GetTitle().contains(s) || TempBook.GetAuthor().contains(s) || String.valueOf(TempBook.GetReleaseYear()).contains(s) || TempBook.GetGenre().contains(s) || TempBook.GetPath().contains(s)) {
                FoundBooks.add(i);
            }
        }
        return FoundBooks;
    }
}
