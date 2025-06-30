package demoLowPersistenceWithDAO.dao;

import java.util.List;
import java.util.Optional;

import demoLowPersistenceWithDAO.domain.Book;

public interface BookDAO {

	void create(Book book);

	Optional<Book> findOne(String isbn);

	List<Book> findAll();

	void update(String searchISBN, Book book);

	void delete(String searchISBN);

}
