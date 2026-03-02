package com.flightreservation.dao;

import com.flightreservation.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FlightDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class FlightRowMapper implements RowMapper<Flight> {
        @Override
        public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flight flight = new Flight();
            flight.setFlightId(rs.getInt("flight_id"));
            flight.setFlightNumber(rs.getString("flight_number"));
            flight.setSource(rs.getString("source"));
            flight.setDestination(rs.getString("destination"));
            flight.setDepartureTime(rs.getString("departure_time"));
            flight.setArrivalTime(rs.getString("arrival_time"));
            flight.setTotalSeats(rs.getInt("total_seats"));
            flight.setAvailableSeats(rs.getInt("available_seats"));
            flight.setPrice(rs.getBigDecimal("price"));
            flight.setCreatedAt(rs.getString("created_at"));
            return flight;
        }
    }

    public Flight save(Flight flight) {
        String sql = "INSERT INTO flights (flight_number, source, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, flight.getFlightNumber(), flight.getSource(), flight.getDestination(),
                flight.getDepartureTime(), flight.getArrivalTime(), flight.getTotalSeats(),
                flight.getAvailableSeats(), flight.getPrice());
        return findByFlightNumber(flight.getFlightNumber());
    }

    public Flight findById(Integer flightId) {
        String sql = "SELECT * FROM flights WHERE flight_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new FlightRowMapper(), flightId);
        } catch (Exception e) {
            return null;
        }
    }

    public Flight findByFlightNumber(String flightNumber) {
        String sql = "SELECT * FROM flights WHERE flight_number = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new FlightRowMapper(), flightNumber);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Flight> findBySourceAndDestination(String source, String destination) {
        String sql = "SELECT * FROM flights WHERE source = ? AND destination = ?";
        return jdbcTemplate.query(sql, new FlightRowMapper(), source, destination);
    }

    public List<Flight> findAll() {
        String sql = "SELECT * FROM flights ORDER BY flight_number";
        return jdbcTemplate.query(sql, new FlightRowMapper());
    }

    public void updateAvailableSeats(Integer flightId, Integer availableSeats) {
        String sql = "UPDATE flights SET available_seats = ? WHERE flight_id = ?";
        jdbcTemplate.update(sql, availableSeats, flightId);
    }

    public boolean existsByFlightNumber(String flightNumber) {
        String sql = "SELECT COUNT(*) FROM flights WHERE flight_number = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, flightNumber);
        return count != null && count > 0;
    }
}
