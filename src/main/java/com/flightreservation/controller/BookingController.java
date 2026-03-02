package com.flightreservation.controller;

import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;
import com.flightreservation.model.Ticket;
import com.flightreservation.service.BookingService;
import com.flightreservation.service.FlightService;
import com.flightreservation.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/book")
    public String showBookingForm(@RequestParam("flightId") Integer flightId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Flight flight = flightService.findById(flightId);
        if (flight == null) {
            model.addAttribute("errorMessage", "Flight not found");
            return "error";
        }

        Booking booking = new Booking();
        booking.setFlightId(flightId);
        booking.setUserId(userId);

        model.addAttribute("booking", booking);
        model.addAttribute("flight", flight);

        return "booking-form";
    }

    @PostMapping("/book")
    public String processBookingForm(@Valid @ModelAttribute("booking") Booking booking,
                                     BindingResult result, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Flight flight = flightService.findById(booking.getFlightId());
        model.addAttribute("flight", flight);

        if (result.hasErrors()) {
            return "booking-form";
        }

        if (!flightService.checkSeatAvailability(booking.getFlightId(), booking.getNumberOfPassengers())) {
            model.addAttribute("errorMessage", "Not enough seats available. Available seats: " + flight.getAvailableSeats());
            return "booking-form";
        }

        session.setAttribute("bookingData", booking);
        session.setAttribute("currentPassengerIndex", 0);
        session.setAttribute("passengers", new ArrayList<Ticket>());

        return "redirect:/booking/addPassenger";
    }

    @GetMapping("/addPassenger")
    public String showAddPassengerForm(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking bookingData = (Booking) session.getAttribute("bookingData");
        if (bookingData == null) {
            return "redirect:/";
        }

        Integer currentIndex = (Integer) session.getAttribute("currentPassengerIndex");
        Flight flight = flightService.findById(bookingData.getFlightId());

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("passengerNumber", currentIndex + 1);
        model.addAttribute("totalPassengers", bookingData.getNumberOfPassengers());
        model.addAttribute("flight", flight);
        model.addAttribute("booking", bookingData);

        return "add-passenger";
    }

    @PostMapping("/addPassenger")
    public String processAddPassenger(@Valid @ModelAttribute("ticket") Ticket ticket,
                                      BindingResult result, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking bookingData = (Booking) session.getAttribute("bookingData");
        Integer currentIndex = (Integer) session.getAttribute("currentPassengerIndex");
        Flight flight = flightService.findById(bookingData.getFlightId());

        if (result.hasErrors()) {
            model.addAttribute("passengerNumber", currentIndex + 1);
            model.addAttribute("totalPassengers", bookingData.getNumberOfPassengers());
            model.addAttribute("flight", flight);
            model.addAttribute("booking", bookingData);
            return "add-passenger";
        }

        List<Ticket> passengers = (List<Ticket>) session.getAttribute("passengers");
        passengers.add(ticket);

        currentIndex++;
        session.setAttribute("currentPassengerIndex", currentIndex);

        if (currentIndex < bookingData.getNumberOfPassengers()) {
            return "redirect:/booking/addPassenger";
        } else {
            return "redirect:/booking/confirm";
        }
    }

    @GetMapping("/confirm")
    public String showConfirmation(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking bookingData = (Booking) session.getAttribute("bookingData");
        List<Ticket> passengers = (List<Ticket>) session.getAttribute("passengers");

        if (bookingData == null || passengers == null) {
            return "redirect:/";
        }

        Flight flight = flightService.findById(bookingData.getFlightId());

        model.addAttribute("booking", bookingData);
        model.addAttribute("flight", flight);
        model.addAttribute("passengers", passengers);

        return "booking-confirmation";
    }

    @PostMapping("/completeBooking")
    public String completeBooking(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking bookingData = (Booking) session.getAttribute("bookingData");
        List<Ticket> passengers = (List<Ticket>) session.getAttribute("passengers");

        try {
            Booking savedBooking = bookingService.createBooking(bookingData, passengers);

            session.removeAttribute("bookingData");
            session.removeAttribute("passengers");
            session.removeAttribute("currentPassengerIndex");

            model.addAttribute("bookingId", savedBooking.getBookingId());
            model.addAttribute("successMessage", "Booking completed successfully!");

            return "booking-success";
        } catch (RuntimeException e) {
            logger.error("Booking failed: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
}
