CREATE TABLE movie (
                         id INT PRIMARY KEY,
                         movie_id VARCHAR(255),
                         titleType VARCHAR(255),
                         primaryTitle VARCHAR(255),
                         originalTitle VARCHAR(255),
                         startYear INT,
                         runtimeMinutes INT,
                         genres VARCHAR(255)


);

CREATE TABLE rating (
                         id INT PRIMARY KEY,
                         movie_id VARCHAR(255),
                         rating DOUBLE,
                         ratingCount INT
);