CREATE DATABASE crop_advisory_db;

USE crop_advisory_db;

CREATE TABLE User (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    phone VARCHAR(20)
);

CREATE TABLE FarmerProfile (
    profile_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    address VARCHAR(255),
    experience INT,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Farm (
    farm_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    location VARCHAR(255),
    area DOUBLE,
    soil_type VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);

CREATE TABLE Crop (
    crop_id INT PRIMARY KEY AUTO_INCREMENT,
    crop_name VARCHAR(100) NOT NULL,
    season VARCHAR(50),
    soil_requirement VARCHAR(255),
    description VARCHAR(500)
);

CREATE TABLE Advisory (
    advisory_id INT PRIMARY KEY AUTO_INCREMENT,
    crop_id INT NOT NULL,
    officer_id INT NOT NULL,
    title VARCHAR(150),
    content VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (crop_id) REFERENCES Crop(crop_id),
    FOREIGN KEY (officer_id) REFERENCES User(user_id)
);

CREATE TABLE AdvisoryRequest (
    request_id INT PRIMARY KEY AUTO_INCREMENT,
    farmer_id INT NOT NULL,
    crop_id INT NOT NULL,
    advisory_id INT,
    question VARCHAR(1000),
    status VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farmer_id) REFERENCES User(user_id),
    FOREIGN KEY (crop_id) REFERENCES Crop(crop_id),
    FOREIGN KEY (advisory_id) REFERENCES Advisory(advisory_id)
);

CREATE TABLE WeatherData (
    weather_id INT PRIMARY KEY AUTO_INCREMENT,
    farm_id INT NOT NULL,
    temperature DOUBLE,
    humidity DOUBLE,
    rainfall DOUBLE,
    recorded_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES Farm(farm_id)
);