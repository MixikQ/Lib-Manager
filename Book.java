public class Book {

    public Book(String Title, String Author, int ReleaseYear, String Genre, String Path) {
        this.Title = Title;
        this.Author = Author;
        this.ReleaseYear = ReleaseYear;
        this.Genre = Genre;
        this.Path = Path;
    }

    private String Title;
    private String Author;
    private int ReleaseYear; // < 0 == Unknown Year
    private String Genre;
    private String Path;

    public String GetTitle() {
        return Title;
    }
    public void SetTitle(String x) {
        this.Title = x;
    }

    public String GetAuthor() {
        return Author;
    }
    public void SetAuthor(String x) {
        this.Author = x;
    }
    
    public int GetReleaseYear() {
        return ReleaseYear;
    }
    public void SetReleaseYear(int x) {
        this.ReleaseYear = x;
    }

    public String GetGenre() {
        return Genre;
    }
    public void SetGenre(String x) {
        this.Genre = x;
    }

    public String GetPath() {
        return Path;
    }
    public void SetPath(String x) {
        this.Path = x;
    }

    public String PrintBook() {
        return GetTitle() + " - " + GetAuthor() + ", " + ((GetReleaseYear() < 0) ? "Unknown" : GetReleaseYear()) + " | " + GetGenre();
    }
    public String FormatToLogfile() {
        return GetTitle() + " ### " + GetAuthor() + " ### " + GetReleaseYear() + " ### " + GetGenre() + " ### " + GetPath();
    }
}
