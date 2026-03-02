package com.flightreservation.controller;

import com.flightreservation.model.Flight;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private FlightService flightService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        logger.info("Home page accessed");
        model.addAttribute("flight", new Flight());

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("userRole", session.getAttribute("userRole"));
        }

        return "home";
    }

    @PostMapping("/searchFlights")
    public String searchFlights(@RequestParam("source") String source,
                                @RequestParam("destination") String destination,
                                Model model, HttpSession session) {
        logger.info("Searching flights from {} to {}", source, destination);

        if (source == null || source.trim().isEmpty()) {
            model.addAttribute("error", "Source is required");
            model.addAttribute("flight", new Flight());
            return "home";
        }

        if (destination == null || destination.trim().isEmpty()) {
            model.addAttribute("error", "Destination is required");
            model.addAttribute("flight", new Flight());
            return "home";
        }

        List<Flight> flights = flightService.searchFlights(source.trim(), destination.trim());

        if (flights.isEmpty()) {
            model.addAttribute("message", "No flights found for the selected route");
        }

        model.addAttribute("flights", flights);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("userRole", session.getAttribute("userRole"));
        }

        return "search-results";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("User logged out");
        session.invalidate();
        return "redirect:/";
    }
}
