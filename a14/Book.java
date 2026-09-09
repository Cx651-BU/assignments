/**
 * Book.java — CX651 Assignment 14
 *
 * A simple record class representing one book returned by the BookDAO.
 * Fields match the columns selected in BookDAO.findByGenre().
 */
public record Book(int bookId, String title, String author, String genre, double price) {

    /**
     * Serialize this book to a JSON object string.
     * Uses manual construction to avoid external library dependencies.
     */
    public String toJson() {
        return String.format(
            "{\"book_id\":%d,\"title\":\"%s\",\"author\":\"%s\",\"genre\":\"%s\",\"price\":%.2f}",
            bookId,
            title.replace("\"", "\\\""),
            author.replace("\"", "\\\""),
            genre.replace("\"", "\\\""),
            price
        );
    }
}
