
DROP TABLE IF EXISTS scores, quizzes, friends, users, friendships, multiple_choice, fill_in_the_blank, picture_response, question_response, messages, achievements, userAchievements, challenges;
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
	PRIMARY KEY(pID)
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
	id int AUTO_INCREMENT,
    fromUser varchar(255),
    toUser varchar(255), 
    message TEXT,
    title TEXT,
    dateCreated varchar(255),
    seen TINYINT(1),
    quizID int,
    PRIMARY KEY(id)
);

CREATE TABLE achievements (
	id int AUTO_INCREMENT,
	name varchar(255),
	description TEXT,
	imageUrl varchar(255),
	PRIMARY KEY(id)
);

CREATE TABLE userAchievements (
	id int AUTO_INCREMENT,
	username varchar(64),
	achievement varchar(64),
	dateCreated varchar(255),
	PRIMARY KEY(id)
);

CREATE TABLE challenges (
	id int AUTO_INCREMENT,
	username varchar(64), 
	challengedUser varchar(64),
	quizID int,
	quizName varchar(255),
	PRIMARY KEY(id)
);