package demoLowPersistenceWithDAO;

import java.sql.Date;
import java.time.LocalDate;

import demoLowPersistenceWithDAO.domain.Author;
import demoLowPersistenceWithDAO.domain.Book;

public final class TestDataUtil {

	private TestDataUtil() {
		// NA
	}

	public static Author getTestAuthorA() {
		return Author.builder().id(1L).name("Hui Yang").birthDate(Date.valueOf(LocalDate.of(1998, 11, 11))).build();
	}

	public static Author getTestAuthorB() {
		return Author.builder().id(2L).name("John Smith").birthDate(Date.valueOf(LocalDate.of(2000, 2, 12))).build();
	}

	public static Author getTestAuthorC() {
		return Author.builder().id(3L).name("Jane Doe").birthDate(Date.valueOf(LocalDate.of(1966, 6, 6))).build();
	}

	public static Book getTestBookA() {
		return Book.builder().isbn("978-3-16-148410-0").title("Long Walk on the Beach").authorID(1L).build();
	}

	public static Book getTestBookB() {
		return Book.builder().isbn("978-3-16-148410-2").title("Sunshine").authorID(1L).build();
	}

	public static Book getTestBookC() {
		return Book.builder().isbn("978-3-16-148410-4").title("My Name is Myra").authorID(2L).build();
	}

}
