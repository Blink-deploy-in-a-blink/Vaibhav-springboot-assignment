package com.flightreservation.service;

import com.flightreservation.dao.FlightDAO;
import com.flightreservation.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);

    @Autowired
    private FlightDAO flightDAO;

    public Flight addFlight(Flight flight) {
        logger.info("Adding new flight with number: {}", flight.getFlightNumber());

        if (flightDAO.existsByFlightNumber(flight.getFlightNumber())) {
            logger.warn("Flight with number {} already exists", flight.getFlightNumber());
            throw new RuntimeException("Flight number already exists");
        }

        if (flight.getAvailableSeats() == null) {
            flight.setAvailableSeats(flight.getTotalSeats());
        }

        Flight savedFlight = flightDAO.save(flight);
        logger.info("Flight added successfully with ID: {}", savedFlight.getFlightId());
        return savedFlight;
    }

    public Flight findById(Integer flightId) {
        return flightDAO.findById(flightId);
    }

    public List<Flight> searchFlights(String source, String destination) {
        logger.info("Searching flights from {} to {}", source, destination);
        return flightDAO.findBySourceAndDestination(source, destination);
    }

    public List<Flight> getAllFlights() {
        logger.info("Fetching all flights");
        return flightDAO.findAll();
    }

    public void updateAvailableSeats(Integer flightId, Integer seats) {
        logger.info("Updating available seats for flight ID: {} to {}", flightId, seats);
        flightDAO.updateAvailableSeats(flightId, seats);
    }

    public boolean checkSeatAvailability(Integer flightId, Integer requiredSeats) {
        Flight flight = flightDAO.findById(flightId);
        if (flight == null) {
            return false;
        }
        return flight.getAvailableSeats() >= requiredSeats;
    }
}
