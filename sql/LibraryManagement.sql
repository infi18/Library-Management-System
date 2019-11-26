CREATE TABLE IF NOT EXISTS 510fp.snaik_users
(   user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR(30),
	last_name VARCHAR(30),
	email VARCHAR(40) UNIQUE,
	password VARCHAR(30),
	phone VARCHAR(11),
	is_admin ENUM('yes', 'no'));


CREATE TABLE IF NOT EXISTS 510fp.snaik_books
(
    book_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_title VARCHAR(50),
    book_author VARCHAR(50),
    book_year INT,
    book_isbn VARCHAR(70) UNIQUE,
    book_quantity INT NOT NULL
);


CREATE TABLE IF NOT EXISTS 510fp.snaik_reviews
(
    review_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    review VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES snaik_users(user_id)  ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES  snaik_books(book_id)  ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS 510fp.snaik_checkout
(
    checkout_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES snaik_users(user_id)  ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES  snaik_books(book_id)  ON DELETE CASCADE
);

insert into 510fp.snaik_books (book_title,book_author,book_year,book_isbn,book_quantity)
values('Percy Jackson','Rick Riordan','2005','9780616541418','4');


insert into 510fp.snaik_books (book_title,book_author,book_year,book_isbn,book_quantity)
values('A Song of Ice and Fire','George R R Martin ','1996',' 9780553386790','2');
