package demoLowPersistenceWithDAO.dao;

import java.util.List;
import java.util.Optional;

import demoLowPersistenceWithDAO.domain.Author;

public interface AuthorDAO {

	void create(Author author);

	Optional<Author> findOne(long id);

	List<Author> findAll();

	void update(long searchID, Author author);

	void delete(long searchID);

}
