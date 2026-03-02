package com.flightreservation.dao;

import com.flightreservation.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class BookingDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class BookingRowMapper implements RowMapper<Booking> {
        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            Booking booking = new Booking();
            booking.setBookingId(rs.getInt("booking_id"));
            booking.setUserId(rs.getInt("user_id"));
            booking.setFlightId(rs.getInt("flight_id"));
            booking.setJourneyDate(rs.getString("journey_date"));
            booking.setNumberOfPassengers(rs.getInt("number_of_passengers"));
            booking.setTotalAmount(rs.getBigDecimal("total_amount"));
            booking.setBookingStatus(rs.getString("booking_status"));
            booking.setBookingDate(rs.getString("booking_date"));
            return booking;
        }
    }

    public Booking save(Booking booking) {
        String sql = "INSERT INTO bookings (user_id, flight_id, journey_date, number_of_passengers, total_amount, booking_status) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getFlightId());
            ps.setString(3, booking.getJourneyDate());
            ps.setInt(4, booking.getNumberOfPassengers());
            ps.setBigDecimal(5, booking.getTotalAmount());
            ps.setString(6, booking.getBookingStatus());
            return ps;
        }, keyHolder);

        booking.setBookingId(keyHolder.getKey().intValue());
        return booking;
    }

    public Booking findById(Integer bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BookingRowMapper(), bookingId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Booking> findByUserId(Integer userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_date DESC";
        return jdbcTemplate.query(sql, new BookingRowMapper(), userId);
    }

    public List<Booking> findAll() {
        String sql = "SELECT * FROM bookings ORDER BY booking_date DESC";
        return jdbcTemplate.query(sql, new BookingRowMapper());
    }

    public void updateStatus(Integer bookingId, String status) {
        String sql = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";
        jdbcTemplate.update(sql, status, bookingId);
    }

    public void delete(Integer bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        jdbcTemplate.update(sql, bookingId);
    }
}
