USE delogic;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    city VARCHAR(50),
    state VARCHAR(50),
    email VARCHAR(150),
    phone VARCHAR(15),
    preference_sports VARCHAR(5) DEFAULT 'FALSE',
    preference_theatre VARCHAR(5) DEFAULT 'FALSE',
    preference_concerts VARCHAR(5) DEFAULT 'FALSE',
    preference_jazz VARCHAR(5) DEFAULT 'FALSE',
    preference_classical VARCHAR(5) DEFAULT 'FALSE',
    preference_opera VARCHAR(5) DEFAULT 'FALSE',
    preference_rock VARCHAR(5) DEFAULT 'FALSE',
    preference_vegas VARCHAR(5) DEFAULT 'FALSE',
    preference_broadway VARCHAR(5) DEFAULT 'FALSE',
    preference_musicals VARCHAR(5) DEFAULT 'FALSE'
);

CREATE TABLE Venues (
    venue_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_name VARCHAR(100) NOT NULL,
    city VARCHAR(50),
    state VARCHAR(50),
    seating_capacity INT
);

CREATE TABLE Categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_group VARCHAR(50),
    category_name VARCHAR(50),
    category_description TEXT
);

CREATE TABLE Dates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    calendar_date DATE NOT NULL,
    day_date VARCHAR(2),
    week_date INT,
    month_date VARCHAR(12),
    quarter_date INT,
    year_date INT,
    holiday_flag VARCHAR(5) DEFAULT 'FALSE'
);


CREATE TABLE Events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    venue_id INT,
    category_id INT,
    date_id INT,
    event_name VARCHAR(100),
    event_start_time TIME,
    FOREIGN KEY (venue_id) REFERENCES Venues(venue_id),
    FOREIGN KEY (category_id) REFERENCES Categories(category_id),
    FOREIGN KEY (date_id) REFERENCES Dates(id)
);

CREATE TABLE Listings (
    listing_id INT AUTO_INCREMENT PRIMARY KEY,
    seller_id INT,
    event_id INT,
    date_id INT,
    number_of_tickets INT,
    price_per_ticket DECIMAL(10, 2),
    total_price DECIMAL(10, 2),
    listing_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (seller_id) REFERENCES Users(user_id),
    FOREIGN KEY (event_id) REFERENCES Events(event_id),
    FOREIGN KEY (date_id) REFERENCES Dates(id)
);

CREATE TABLE Sales (
    sale_id INT AUTO_INCREMENT PRIMARY KEY,
    listing_id INT,
    seller_id INT,
    buyer_id INT,
    event_id INT,
    date_id INT,
    quantity_sold INT,
    price_paid DECIMAL(10, 2),
    commission_amount DECIMAL(10, 2),
    sale_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (listing_id) REFERENCES Listings(listing_id),
    FOREIGN KEY (seller_id) REFERENCES Users(user_id),
    FOREIGN KEY (buyer_id) REFERENCES Users(user_id),
    FOREIGN KEY (event_id) REFERENCES Events(event_id),
    FOREIGN KEY (date_id) REFERENCES Dates(id)
);



