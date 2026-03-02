-- Create database
CREATE DATABASE IF NOT EXISTS flight_reservation_db;
USE flight_reservation_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Flights Table
CREATE TABLE IF NOT EXISTS flights (
    flight_id INT AUTO_INCREMENT PRIMARY KEY,
    flight_number VARCHAR(20) UNIQUE NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    total_seats INT DEFAULT 20,
    available_seats INT DEFAULT 20,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    journey_date DATE NOT NULL,
    number_of_passengers INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    booking_status VARCHAR(20) DEFAULT 'CONFIRMED',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES flights(flight_id) ON DELETE CASCADE
);

-- Tickets Table
CREATE TABLE IF NOT EXISTS tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    passenger_name VARCHAR(100) NOT NULL,
    passenger_age INT NOT NULL,
    passenger_gender VARCHAR(10) NOT NULL,
    seat_number VARCHAR(10),
    ticket_status VARCHAR(20) DEFAULT 'CONFIRMED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE
);

-- Insert sample flights
INSERT INTO flights (flight_number, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES
('AI101', 'Delhi', 'Mumbai', '06:00:00', '08:00:00', 20, 20, 5000.00),
('AI102', 'Mumbai', 'Delhi', '09:00:00', '11:00:00', 20, 20, 5000.00),
('AI103', 'Delhi', 'Bangalore', '10:00:00', '12:30:00', 20, 20, 6000.00),
('AI104', 'Bangalore', 'Delhi', '14:00:00', '16:30:00', 20, 20, 6000.00),
('AI105', 'Mumbai', 'Bangalore', '07:00:00', '09:00:00', 20, 20, 4500.00),
('AI106', 'Bangalore', 'Mumbai', '15:00:00', '17:00:00', 20, 20, 4500.00),
('AI107', 'Delhi', 'Chennai', '11:00:00', '13:30:00', 20, 20, 6500.00),
('AI108', 'Chennai', 'Delhi', '16:00:00', '18:30:00', 20, 20, 6500.00);

-- Insert sample admin user (password: admin123)
INSERT INTO users (email, password, role) VALUES
('admin@flight.com', 'admin123', 'ADMIN');
