package com.flightreservation.service;

import com.flightreservation.dao.BookingDAO;
import com.flightreservation.dao.TicketDAO;
import com.flightreservation.model.Booking;
import com.flightreservation.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private BookingDAO bookingDAO;

    public List<Ticket> getTicketsByBookingId(Integer bookingId) {
        logger.info("Fetching tickets for booking ID: {}", bookingId);
        List<Ticket> tickets = ticketDAO.findByBookingId(bookingId);

        for (Ticket ticket : tickets) {
            Booking booking = bookingDAO.findById(ticket.getBookingId());
            ticket.setBooking(booking);
        }

        return tickets;
    }

    public Ticket findById(Integer ticketId) {
        return ticketDAO.findById(ticketId);
    }
}
