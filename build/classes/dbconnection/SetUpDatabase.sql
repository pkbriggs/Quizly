USE c_cs108_lucyanne;

DROP TABLE IF EXISTS ;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizzes (
	id INT AUTO_INCREMENT,
    name CHAR(64),
    imagefile CHAR(64),
    price DECIMAL(6,2),
    primary key(id)
);

-- stores all friendships and freidn requests
CREATE TABLE friends (
	productid CHAR(6),
    name CHAR(64),
    imagefile CHAR(64),
    price DECIMAL(6,2)
);

-- stores all users
CREATE TABLE users (
	id INT AUTO_INCREMENT,
	username VARCHAR,
	passwordhash VARCHAR,
	picturefile VARCHAR,
	primary key(id)
);

-- stores friendships between users (bidirectional)
CREATE TABLE friendships (
	id INT AUTO_INCREMENT,
	user1 VARCHAR,
	user2 VARCHAR,
	status VARCHAR,
	primary key(id)
);