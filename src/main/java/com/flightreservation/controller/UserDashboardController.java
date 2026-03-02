package com.flightreservation.controller;

import com.flightreservation.model.Booking;
import com.flightreservation.model.Ticket;
import com.flightreservation.service.BookingService;
import com.flightreservation.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserDashboardController {

    private static final Logger logger = LoggerFactory.getLogger(UserDashboardController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        String userRole = (String) session.getAttribute("userRole");
        if (!"USER".equals(userRole)) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("userEmail", session.getAttribute("userEmail"));
        return "user-dashboard";
    }

    @GetMapping("/myBookings")
    public String showMyBookings(HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        model.addAttribute("bookings", bookings);
        model.addAttribute("userEmail", session.getAttribute("userEmail"));

        return "my-bookings";
    }

    @GetMapping("/viewTickets")
    public String viewTickets(@RequestParam("bookingId") Integer bookingId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Booking booking = bookingService.findById(bookingId);
        if (booking == null || !booking.getUserId().equals(userId)) {
            model.addAttribute("errorMessage", "Booking not found or access denied");
            return "error";
        }

        List<Ticket> tickets = ticketService.getTicketsByBookingId(bookingId);
        model.addAttribute("tickets", tickets);
        model.addAttribute("booking", booking);

        return "view-tickets";
    }

    @PostMapping("/cancelTicket")
    public String cancelTicket(@RequestParam("ticketId") Integer ticketId,
                               @RequestParam("bookingId") Integer bookingId,
                               HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        try {
            bookingService.cancelTicket(ticketId);
            model.addAttribute("successMessage", "Ticket cancelled successfully");
        } catch (RuntimeException e) {
            logger.error("Ticket cancellation failed: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/user/viewTickets?bookingId=" + bookingId;
    }
}
