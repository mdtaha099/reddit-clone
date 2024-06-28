create database reddit; 

use reddit;


CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    karma INT DEFAULT 0,
    authority VARCHAR(50)
);

CREATE TABLE subreddit (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    user_id INT,
    parent_id INT,
    subreddit_id INT,
    image_id INT,
    upvotes INT DEFAULT 0,
    downvotes INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_post BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (parent_id) REFERENCES post(id),
    FOREIGN KEY (subreddit_id) REFERENCES subreddit(id),
    FOREIGN KEY (image_id) REFERENCES image(id)
);

CREATE TABLE user_subreddit (
    user_id INT,
    subreddit_id INT,
    PRIMARY KEY (user_id, subreddit_id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (subreddit_id) REFERENCES subreddit(id)
);

CREATE TABLE image (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    location TEXT
);