package demoLowPersistenceWithDAO.dao_actual;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import demoLowPersistenceWithDAO.dao.AuthorDAO;
import demoLowPersistenceWithDAO.domain.Author;

@Component
public class AuthorDaoActual implements AuthorDAO {

	private final JdbcTemplate jdbcTemplate;
	private static String createAuthorSQL = "INSERT INTO authors (id, name, date_of_birth) VALUES (?, ?, ?)";
	private static String findOneSQL = "SELECT * FROM authors WHERE id = ?";
	private static String findAllSQL = "SELECT * FROM authors";
	private static String updateSQL = "UPDATE authors SET id = ?, name = ?, date_of_birth = ? WHERE id = ?";
	private static String deleteSQL = "DELETE FROM authors WHERE id = ?";

	public AuthorDaoActual(final JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static class AuthorRowMapper implements RowMapper<Author> {

		@Override
		public Author mapRow(ResultSet dataSet, int rowNum) throws SQLException {
			return Author.builder().id(dataSet.getLong("id")).name(dataSet.getString("name"))
					.birthDate(dataSet.getDate("date_of_birth")).build();
		}

	}

	public static String getCreateAuthorSQL() {
		return createAuthorSQL;
	}

	@Override
	public void create(Author author) {
		jdbcTemplate.update(createAuthorSQL, author.getId(), author.getName(), author.getBirthDate());
	}

	public static String getFindOneSQL() {
		return findOneSQL;
	}

	@Override
	public Optional<Author> findOne(long authorID) {
		List<Author> results = jdbcTemplate.query(findOneSQL, new AuthorRowMapper(), authorID);
		return results.stream().findFirst();
	}

	public static String getFindAllSQL() {
		return findAllSQL;
	}

	@Override
	public List<Author> findAll() {
		return jdbcTemplate.query(findAllSQL, new AuthorRowMapper());
	}

	public static String getUpdateSQL() {
		return updateSQL;
	}

	@Override
	public void update(long searchID, Author author) {
		jdbcTemplate.update(updateSQL, author.getId(), author.getName(), author.getBirthDate(), searchID);
	}

	public static String getDeleteSQL() {
		return deleteSQL;
	}

	@Override
	public void delete(long searchID) {
		jdbcTemplate.update(deleteSQL, searchID);
	}

}
