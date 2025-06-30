package demoLowPersistenceWithDAO.dao_actual;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import demoLowPersistenceWithDAO.dao.BookDAO;
import demoLowPersistenceWithDAO.domain.Book;

@Component
public class BookDaoActual implements BookDAO {

	private final JdbcTemplate jdbcTemplate;
	private static String createBookSQL = "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)";
	private static String findOneSQL = "SELECT * FROM books WHERE isbn = ?";
	private static String findAllSQL = "SELECT * FROM books";
	private static String updateSQL = "UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?";
	private static String deleteSQL = "DELETE FROM books WHERE isbn = ?";

	public BookDaoActual(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static class BookRowMapper implements RowMapper<Book> {

		@Override
		public Book mapRow(ResultSet dataSet, int rowNum) throws SQLException {
			return Book.builder().isbn(dataSet.getString("isbn")).title(dataSet.getString("title"))
					.authorID(dataSet.getLong("author_id")).build();
		}

	}

	public static String getCreateBookSQL() {
		return createBookSQL;
	}

	@Override
	public void create(Book book) {
		jdbcTemplate.update(createBookSQL, book.getIsbn(), book.getTitle(), book.getAuthorID());
	}

	public static String getFindOneSQL() {
		return findOneSQL;
	}

	@Override
	public Optional<Book> findOne(String isbn) {
		List<Book> results = jdbcTemplate.query(findOneSQL, new BookRowMapper(), isbn);
		return results.stream().findFirst();
	}

	public static String getFindAllSQL() {
		return findAllSQL;
	}

	@Override
	public List<Book> findAll() {
		return jdbcTemplate.query(findAllSQL, new BookRowMapper());
	}

	public static String getUpdateSQL() {
		return updateSQL;
	}

	@Override
	public void update(String searchISBN, Book book) {
		jdbcTemplate.update(updateSQL, book.getIsbn(), book.getTitle(), book.getAuthorID(), searchISBN);
	}

	public static String getDeleteSQL() {
		return deleteSQL;
	}

	@Override
	public void delete(String searchISBN) {
		jdbcTemplate.update(deleteSQL, searchISBN);
	}

}
