package demoLowPersistenceWithDAO.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import demoLowPersistenceWithDAO.TestDataUtil;
import demoLowPersistenceWithDAO.dao_actual.BookDaoActual;
import demoLowPersistenceWithDAO.domain.Author;
import demoLowPersistenceWithDAO.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoActIntegrationTest {

	private BookDaoActual bookDaoActUnderTest;
	private AuthorDAO authorDAO;

	@Autowired
	public BookDaoActIntegrationTest(BookDaoActual bookDaoActUnderTest, AuthorDAO authorDAO) {
		this.bookDaoActUnderTest = bookDaoActUnderTest;
		this.authorDAO = authorDAO;
	}

	@Test
	public void testBookCreatedThenBeRead() {
		Author author = TestDataUtil.getTestAuthorA();
		Book testBook = TestDataUtil.getTestBookA();
		testBook.setAuthorID(author.getId()); // Make sure test book is written by the author
		authorDAO.create(author);
		bookDaoActUnderTest.create(testBook);
		Optional<Book> result = bookDaoActUnderTest.findOne(testBook.getIsbn());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testBook);
	}

	@Test
	public void testMultipleBooksCreatedThenRead() {
		Author author = TestDataUtil.getTestAuthorA();
		Book testBookA = TestDataUtil.getTestBookA();
		testBookA.setAuthorID(author.getId());
		Book testBookB = TestDataUtil.getTestBookB();
		testBookB.setAuthorID(author.getId());
		Book testBookC = TestDataUtil.getTestBookC();
		testBookC.setAuthorID(author.getId());
		authorDAO.create(author);
		bookDaoActUnderTest.create(testBookA);
		bookDaoActUnderTest.create(testBookB);
		bookDaoActUnderTest.create(testBookC);

		List<Book> results = bookDaoActUnderTest.findAll();
		assertThat(results).hasSize(3).containsExactly(testBookA, testBookB, testBookC);
	}

	@Test
	public void testUpdateBookThenRead() {
		Book testBookA = TestDataUtil.getTestBookA();
		Book testBookB = TestDataUtil.getTestBookB();
		Author testAuthor = TestDataUtil.getTestAuthorA();
		authorDAO.create(testAuthor);
		testBookA.setAuthorID(testAuthor.getId()); // Make sure test author writes this book
		bookDaoActUnderTest.create(testBookA);
		bookDaoActUnderTest.update(testBookA.getIsbn(), testBookB);
		Optional<Book> result = bookDaoActUnderTest.findOne(testBookB.getIsbn());

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testBookB);
	}

	@Test
	public void testDeleteBookThenRead() {
		Author testAuthor = TestDataUtil.getTestAuthorA();
		authorDAO.create(testAuthor);
		Book testBook = TestDataUtil.getTestBookA();
		testBook.setAuthorID(testAuthor.getId());
		bookDaoActUnderTest.create(testBook);
		bookDaoActUnderTest.delete(testBook.getIsbn());
		Optional<Book> result = bookDaoActUnderTest.findOne(testBook.getIsbn());

		assertThat(result).isEmpty();
	}

}
