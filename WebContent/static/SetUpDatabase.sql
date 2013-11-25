DROP TABLE IF EXISTS quizzes, friends, users, friendships, multiple_choice, fill_in_the_blank, picture_response, question_response, messages;
/* remove table if it already exists and start from scratch */

CREATE TABLE quizzes (
    id int AUTO_INCREMENT,
    title CHAR(64),
    description TEXT,
    creator TINYTEXT,
    dateCreated DATETIME,
    numPages INT,
    PRIMARY KEY(id)
);

CREATE TABLE scores (
    pID int AUTO_INCREMENT,
    username varchar(255),
	quizID int,
	time BIGINT,
	score DOUBLE,
	dateTaken DATETIME,
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
	imageURL TINYTEXT,
	answer CHAR(64),
	PRIMARY KEY(id)
);

CREATE TABLE question_response (
    id int AUTO_INCREMENT,
	quizID int,
	question TINYTEXT,
	answer CHAR(64),
	PRIMARY KEY(id)
);

/* stores all users */
CREATE TABLE users (
    id int AUTO_INCREMENT,
    username varchar(255),
    passwordhash varchar(255),
    picturefile varchar(255),
    PRIMARY KEY(id)
);

/* stores friendships between users */
CREATE TABLE friendships (
    id int AUTO_INCREMENT,
    user1 int,
    user2 int,
    status varchar(255),
    PRIMARY KEY(id)
);

CREATE TABLE messages (
    pID int AUTO_INCREMENT,
    fromUser varchar(255),
    toUser varchar(255), 
    mType int,
    message TEXT,
    seen TINYINT(1),
    quizID int,
    PRIMARY KEY(pID)
);