DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
	id BIGINT DEFAULT NEXTVAL('authors_id_seq') NOT NULL,
	name TEXT,
	date_of_birth DATE,
	CONSTRAINT authors_pkey PRIMARY KEY (id)
);

CREATE TABLE books (
	isbn TEXT NOT NULL,
	title TEXT,
	author_id BIGINT,
	CONSTRAINT books_pkey PRIMARY KEY (isbn),
	CONSTRAINT fkey_author FOREIGN KEY (author_id) 	REFERENCES authors(id)
);
