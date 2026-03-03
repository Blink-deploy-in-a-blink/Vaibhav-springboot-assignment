package com.flightreservation.service;
import java.time.*;
import com.flightreservation.dao.BookingDAO;
import com.flightreservation.dao.FlightDAO;
import com.flightreservation.dao.TicketDAO;
import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;
import com.flightreservation.model.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingDAO bookingDAO;

    @Autowired
    private FlightDAO flightDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private FlightService flightService;

    @Transactional
    public Booking createBooking(Booking booking, List<Ticket> tickets) {
        logger.info("Creating booking for user ID: {} for flight ID: {}", booking.getUserId(), booking.getFlightId());

        Flight flight = flightDAO.findById(booking.getFlightId());
        if (flight == null) {
            throw new RuntimeException("Flight not found");
        }

        if (flight.getAvailableSeats() < booking.getNumberOfPassengers()) {
            throw new RuntimeException("Not enough seats available. Available seats: " + flight.getAvailableSeats());
        }

        BigDecimal totalAmount = flight.getPrice().multiply(new BigDecimal(booking.getNumberOfPassengers()));
        booking.setTotalAmount(totalAmount);

        Booking savedBooking = bookingDAO.save(booking);
        logger.info("Booking created with ID: {}", savedBooking.getBookingId());

        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            ticket.setBookingId(savedBooking.getBookingId());
            ticket.setSeatNumber(generateSeatNumber(flight, i));
            ticketDAO.save(ticket);
        }

        int newAvailableSeats = flight.getAvailableSeats() - booking.getNumberOfPassengers();
        flightService.updateAvailableSeats(flight.getFlightId(), newAvailableSeats);

        return savedBooking;
    }

//    public Booking findById(Integer bookingId) {
//        return bookingDAO.findById(bookingId);
//    }
    
    

    public List<Booking> getBookingsByUserId(Integer userId) {
        logger.info("Fetching bookings for user ID: {}", userId);
        List<Booking> bookings = bookingDAO.findByUserId(userId);

        for (Booking booking : bookings) {
            Flight flight = flightDAO.findById(booking.getFlightId());
            booking.setFlight(flight);
        }

        return bookings;
    }

    public List<Booking> getAllBookingsWithFlights() {
        logger.info("Fetching all bookings with flight details");
        List<Booking> bookings = bookingDAO.findAll();
        logger.info("Total bookings found: {}", bookings.size());

        for (Booking booking : bookings) {
            Flight flight = flightDAO.findById(booking.getFlightId());
            booking.setFlight(flight);
        }

        return bookings;
    }
    
//    public List<Booking> getAllBookingsWithFlights() {
//
//        logger.info("Fetching all bookings with flight details");
//        List<Booking> bookings = bookingDAO.findAll();
//        logger.info("Total bookings found: {}", bookings.size());
//
//        LocalDateTime now = LocalDateTime.now();
//
//        for (Booking booking : bookings) {
//
//            Flight flight = flightDAO.findById(booking.getFlightId());
//            booking.setFlight(flight);
//
//            // 🔥 Combine journey_date + arrival_time
//            LocalDate journeyDate = booking.getJourneyDate();
//            LocalTime arrivalTime = flight.getArrivalTime();
//
//            LocalDateTime arrivalDateTime =
//                    LocalDateTime.of(journeyDate, arrivalTime);
//
//            if (arrivalDateTime.isBefore(now)) {
//                booking.setBookingStatus("COMPLETED");
//            } else {
//                booking.setBookingStatus("UPCOMING");
//            }
//        }
//
//        return bookings;
//    }

    @Transactional
    public void cancelTicket(Integer ticketId) {
        logger.info("Cancelling ticket ID: {}", ticketId);

        Ticket ticket = ticketDAO.findById(ticketId);
        if (ticket == null) {
            throw new RuntimeException("Ticket not found");
        }

        if (!"CONFIRMED".equals(ticket.getTicketStatus())) {
            throw new RuntimeException("Ticket is already cancelled");
        }

        ticketDAO.updateStatus(ticketId, "CANCELLED");

        Booking booking = bookingDAO.findById(ticket.getBookingId());
        Flight flight = flightDAO.findById(booking.getFlightId());

        int newAvailableSeats = flight.getAvailableSeats() + 1;
        flightService.updateAvailableSeats(flight.getFlightId(), newAvailableSeats);

        int confirmedTickets = ticketDAO.countConfirmedTicketsByBookingId(booking.getBookingId());
        if (confirmedTickets == 0) {
            bookingDAO.updateStatus(booking.getBookingId(), "CANCELLED");
        }

        logger.info("Ticket cancelled successfully. Released seat for flight ID: {}", flight.getFlightId());
    }

    private String generateSeatNumber(Flight flight, int index) {
        int totalSeats = flight.getTotalSeats();
        int bookedSeats = totalSeats - flight.getAvailableSeats();
        int seatNumber = bookedSeats + index + 1;
        return String.format("%02d", seatNumber);
    }
}
