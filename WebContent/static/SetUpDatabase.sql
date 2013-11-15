USE c_cs108_lucyanne;

DROP TABLE IF EXISTS quizzes, friends, users, friendships;
/* remove table if it already exists and start from scratch */

CREATE TABLE quizzes (
    id int AUTO_INCREMENT,
    title CHAR(64),
    description TEXT,
    creator TINYTEXT,
    dateCreated DATETIME,
    PRIMARY KEY(id)
);

/*Each question will have the quizID of the quiz it is associated with*/
CREATE TABLE multiple_choice (
    id int AUTO_INCREMENT,
	quizID int,
	question TINYTEXT,
	choice1 CHAR(64),
	choice2 CHAR(64),
	choice3 CHAR(64),
	choice4 CHAR(64),
	answer CHAR(64),
	PRIMARY KEY(id)

);

CREATE TABLE fill_in_the_blank (
    id int AUTO_INCREMENT,
	quizID int,
	question TINYTEXT,
	answer CHAR(64),
	PRIMARY KEY(id)
);

CREATE TABLE picture_response (
    id int AUTO_INCREMENT,
	quizID int,
	imagefile TINYTEXT,
	answer CHAR(64),
	PRIMARY KEY(id)
);

CREATE TABLE question_response (
    id int AUTO_INCREMENT,
	quizID int,
	imagefile TINYTEXT,
	answer CHAR(64),
	PRIMARY KEY(id)
);

/* stores all friendships and friend requests */
CREATE TABLE friends (
    productid CHAR(6),
    qname CHAR(64),
    imagefile CHAR(64),
    price DECIMAL(6,2)
);

/* stores all users */
CREATE TABLE users (
    pID int AUTO_INCREMENT,
    username varchar(255),
    passwordhash varchar(255),
    picturefile varchar(255),
    PRIMARY KEY(pID)
);

/* stores friendships between users */
CREATE TABLE friendships (
    pID int AUTO_INCREMENT,
    user1 varchar(255),
    user2 varchar(255),
    status varchar(255),
    PRIMARY KEY(pID)
);