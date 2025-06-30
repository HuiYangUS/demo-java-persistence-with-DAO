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
import demoLowPersistenceWithDAO.dao_actual.AuthorDaoActual;
import demoLowPersistenceWithDAO.domain.Author;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoActIntegrationTests {

	private AuthorDaoActual authorDaoActUnderTest;

	@Autowired
	public AuthorDaoActIntegrationTests(AuthorDaoActual authorDaoImpUnderTest) {
		this.authorDaoActUnderTest = authorDaoImpUnderTest;
	}

	@Test
	public void testAuthorCreatedThenBeRead() {
		Author testAuthor = TestDataUtil.getTestAuthorA();
		authorDaoActUnderTest.create(testAuthor);
		Optional<Author> result = authorDaoActUnderTest.findOne(testAuthor.getId());
		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testAuthor);
	}

	@Test
	public void testMultipleAuthorsCreatedThenRead() {
		Author testAuthorA = TestDataUtil.getTestAuthorA();
		Author testAuthorB = TestDataUtil.getTestAuthorB();
		Author testAuthorC = TestDataUtil.getTestAuthorC();
		authorDaoActUnderTest.create(testAuthorA);
		authorDaoActUnderTest.create(testAuthorB);
		authorDaoActUnderTest.create(testAuthorC);

		List<Author> results = authorDaoActUnderTest.findAll();
		assertThat(results).hasSize(3).containsExactly(testAuthorA, testAuthorB, testAuthorC);
	}

	@Test
	public void testUpdateAuthorThenRead() {
		Author testAuthorA = TestDataUtil.getTestAuthorA();
		Author testAuthorB = TestDataUtil.getTestAuthorB();
		authorDaoActUnderTest.create(testAuthorA);
		authorDaoActUnderTest.update(testAuthorA.getId(), testAuthorB);
		Optional<Author> result = authorDaoActUnderTest.findOne(testAuthorB.getId());

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testAuthorB);
	}

	@Test
	public void testDeleteAuthorThenRead() {
		Author testAuthor = TestDataUtil.getTestAuthorA();
		authorDaoActUnderTest.create(testAuthor);
		authorDaoActUnderTest.delete(testAuthor.getId());
		Optional<Author> result = authorDaoActUnderTest.findOne(testAuthor.getId());

		assertThat(result).isEmpty();
	}

}
