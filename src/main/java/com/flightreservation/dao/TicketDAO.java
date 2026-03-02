package com.flightreservation.dao;

import com.flightreservation.model.Ticket;
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
public class TicketDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class TicketRowMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setTicketId(rs.getInt("ticket_id"));
            ticket.setBookingId(rs.getInt("booking_id"));
            ticket.setPassengerName(rs.getString("passenger_name"));
            ticket.setPassengerAge(rs.getInt("passenger_age"));
            ticket.setPassengerGender(rs.getString("passenger_gender"));
            ticket.setSeatNumber(rs.getString("seat_number"));
            ticket.setTicketStatus(rs.getString("ticket_status"));
            ticket.setCreatedAt(rs.getString("created_at"));
            return ticket;
        }
    }

    public Ticket save(Ticket ticket) {
        String sql = "INSERT INTO tickets (booking_id, passenger_name, passenger_age, passenger_gender, seat_number, ticket_status) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, ticket.getBookingId());
            ps.setString(2, ticket.getPassengerName());
            ps.setInt(3, ticket.getPassengerAge());
            ps.setString(4, ticket.getPassengerGender());
            ps.setString(5, ticket.getSeatNumber());
            ps.setString(6, ticket.getTicketStatus());
            return ps;
        }, keyHolder);

        ticket.setTicketId(keyHolder.getKey().intValue());
        return ticket;
    }

    public Ticket findById(Integer ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new TicketRowMapper(), ticketId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Ticket> findByBookingId(Integer bookingId) {
        String sql = "SELECT * FROM tickets WHERE booking_id = ?";
        return jdbcTemplate.query(sql, new TicketRowMapper(), bookingId);
    }

    public void updateStatus(Integer ticketId, String status) {
        String sql = "UPDATE tickets SET ticket_status = ? WHERE ticket_id = ?";
        jdbcTemplate.update(sql, status, ticketId);
    }

    public void delete(Integer ticketId) {
        String sql = "DELETE FROM tickets WHERE ticket_id = ?";
        jdbcTemplate.update(sql, ticketId);
    }

    public int countConfirmedTicketsByBookingId(Integer bookingId) {
        String sql = "SELECT COUNT(*) FROM tickets WHERE booking_id = ? AND ticket_status = 'CONFIRMED'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, bookingId);
        return count != null ? count : 0;
    }
}
