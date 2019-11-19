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
    book_isbn VARCHAR(70)
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



insert into 510fp.snaik_users (first_name,last_name,email,password,phone,is_admin)
values('admin','admin','admin@example.com','password','3331213344','yes');

insert into 510fp.snaik_users (first_name,last_name,email,password,phone,is_admin)
values('siddhi','naik','snaik@hawk.iit.edu','password123','3331213344','no');

insert into 510fp.snaik_users (first_name,last_name,email,password,phone,is_admin)
values('infi','hwl','infi@example.com','password','3331213344','no');
