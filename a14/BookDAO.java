import java.sql.*;
import java.util.*;

/**
 * BookDAO.java — CX651 Assignment 14
 *
 * Data Access Object for the books table.
 * Uses a shared HikariCP connection pool (passed in via constructor).
 * All queries use PreparedStatement to prevent SQL injection.
 */
public class BookDAO {

    private final javax.sql.DataSource dataSource;

    public BookDAO(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Return all books matching the given genre, ordered by title.
     * Uses a PreparedStatement so that the genre parameter is never
     * concatenated into the SQL string.
     *
     * @param genre the genre to filter by (case-sensitive)
     * @return list of matching Book records, or an empty list if none
     * @throws SQLException if the query fails
     */
    public List<Book> findByGenre(String genre) throws SQLException {
        String sql = """
                SELECT b.book_id, b.title, a.name AS author,
                       b.genre, b.price
                FROM   books b
                JOIN   authors a ON a.author_id = b.author_id
                WHERE  b.genre = ?
                ORDER  BY b.title
                """;

        List<Book> result = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, genre);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getDouble("price")
                    ));
                }
            }
        }

        return result;
    }
}
