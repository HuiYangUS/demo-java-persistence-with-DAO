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
import demoLowPersistenceWithDAO.dao_actual.BookDaoActual;
import demoLowPersistenceWithDAO.domain.Book;

@ExtendWith(MockitoExtension.class)
public class BookDaoActTests {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private BookDaoActual bookDaoActUnderTest;

	@Test
	public void testCreateBookSQL() {
		String targetSQL = BookDaoActual.getCreateBookSQL();
		Book testBook = TestDataUtil.getTestBookA();
		bookDaoActUnderTest.create(testBook);
		verify(jdbcTemplate).update(eq(targetSQL), eq("978-3-16-148410-0"), eq("Long Walk on the Beach"), eq(1L));
	}

	@Test
	public void testFindOneBookSQL() {
		String targetSQL = BookDaoActual.getFindOneSQL();
		String testISBN = "978-3-16-148410-0";
		bookDaoActUnderTest.findOne(testISBN);
		verify(jdbcTemplate).query(eq(targetSQL), ArgumentMatchers.any(BookDaoActual.BookRowMapper.class),
				eq(testISBN));
	}

	@Test
	public void testFindAllBookSQL() {
		String targetSQL = BookDaoActual.getFindAllSQL();
		bookDaoActUnderTest.findAll();
		verify(jdbcTemplate).query(eq(targetSQL), ArgumentMatchers.any(BookDaoActual.BookRowMapper.class));
	}

	@Test
	public void testUpdateBookSQL() {
		String targetSQL = BookDaoActual.getUpdateSQL();
		Book testBook = TestDataUtil.getTestBookA();
		bookDaoActUnderTest.update("978-3-16-148410-0", testBook);
		verify(jdbcTemplate).update(eq(targetSQL), eq(testBook.getIsbn()), eq(testBook.getTitle()),
				eq(testBook.getAuthorID()), eq("978-3-16-148410-0"));
	}

	@Test
	public void testDeleteBookSQL() {
		String targetSQL = BookDaoActual.getDeleteSQL();
		Book testBook = TestDataUtil.getTestBookA();
		bookDaoActUnderTest.delete(testBook.getIsbn());
		verify(jdbcTemplate).update(eq(targetSQL), eq(testBook.getIsbn()));
	}

}
