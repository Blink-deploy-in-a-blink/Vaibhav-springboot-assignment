package com.flightreservation.controller;

import com.flightreservation.model.Booking;
import com.flightreservation.model.Flight;
import com.flightreservation.service.BookingService;
import com.flightreservation.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private FlightService flightService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!"ADMIN".equals(userRole)) {
            return "redirect:/user/dashboard";
        }

        model.addAttribute("userEmail", session.getAttribute("userEmail"));
        return "admin-dashboard";
    }

    @GetMapping("/addFlight")
    public String showAddFlightForm(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!"ADMIN".equals(userRole)) {
            return "redirect:/user/dashboard";
        }

        model.addAttribute("flight", new Flight());
        return "add-flight";
    }

    @PostMapping("/addFlight")
    public String addFlight(@Valid @ModelAttribute("flight") Flight flight, BindingResult result,
                            HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "add-flight";
        }

        try {
            flightService.addFlight(flight);
            model.addAttribute("successMessage", "Flight added successfully");
            model.addAttribute("flight", new Flight());
            return "add-flight";
        } catch (RuntimeException e) {
            logger.error("Failed to add flight: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "add-flight";
        }
    }

    @GetMapping("/viewFlights")
    public String viewFlights(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!"ADMIN".equals(userRole)) {
            return "redirect:/user/dashboard";
        }

        List<Booking> allBookings = bookingService.getAllBookingsWithFlights();
        List<Booking> upcomingBookings = new ArrayList<>();
        List<Booking> completedBookings = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (Booking booking : allBookings) {
            try {
                LocalDate journeyDate = LocalDate.parse(booking.getJourneyDate());
                if (journeyDate.isAfter(today) || journeyDate.isEqual(today)) {
                    upcomingBookings.add(booking);
                } else {
                    completedBookings.add(booking);
                }
            } catch (Exception e) {
                logger.warn("Invalid journey date format for booking ID: {}", booking.getBookingId());
            }
        }

        model.addAttribute("upcomingBookings", upcomingBookings);
        model.addAttribute("completedBookings", completedBookings);

        return "view-flights";
    }
}
