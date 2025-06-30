package demoLowPersistenceWithDAO.dao;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import demoLowPersistenceWithDAO.TestDataUtil;
import demoLowPersistenceWithDAO.dao_actual.AuthorDaoActual;
import demoLowPersistenceWithDAO.domain.Author;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoActTests {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private AuthorDaoActual authorDaoActUnderTest;

	@Test
	public void testCreateAuthorSQL() {
		String targetSQL = AuthorDaoActual.getCreateAuthorSQL();
		Author testAuthor = TestDataUtil.getTestAuthorA();
		authorDaoActUnderTest.create(testAuthor);
		verify(jdbcTemplate).update(eq(targetSQL), eq(testAuthor.getId()), eq(testAuthor.getName()),
				eq(testAuthor.getBirthDate()));
	}

	@Test
	public void testFindOneAuthorSQL() {
		String targetSQL = AuthorDaoActual.getFindOneSQL();
		long testID = 1L;
		authorDaoActUnderTest.findOne(testID);
		verify(jdbcTemplate).query(eq(targetSQL), ArgumentMatchers.any(AuthorDaoActual.AuthorRowMapper.class),
				eq(testID));
	}

	@Test
	public void testFindAllSQL() {
		String targetSQL = AuthorDaoActual.getFindAllSQL();
		authorDaoActUnderTest.findAll();
		verify(jdbcTemplate).query(eq(targetSQL), ArgumentMatchers.any(AuthorDaoActual.AuthorRowMapper.class));
	}

	@Test
	public void testUpdateAuthorSQL() {
		String targetSQL = AuthorDaoActual.getUpdateSQL();
		Author testAuthor = TestDataUtil.getTestAuthorA();
		authorDaoActUnderTest.update(3L, testAuthor);
		verify(jdbcTemplate).update(eq(targetSQL), eq(testAuthor.getId()), eq(testAuthor.getName()),
				eq(testAuthor.getBirthDate()), eq(3L));
	}

	@Test
	public void testDeleteAuthorSQL() {
		String targetSQL = AuthorDaoActual.getDeleteSQL();
		authorDaoActUnderTest.delete(3L);
		verify(jdbcTemplate).update(eq(targetSQL), eq(3L));
	}

}
