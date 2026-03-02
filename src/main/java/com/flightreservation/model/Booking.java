package com.flightreservation.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class Booking {
    private Integer bookingId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Flight ID is required")
    private Integer flightId;

    @NotNull(message = "Journey date is required")
    private String journeyDate;

    @NotNull(message = "Number of passengers is required")
    @Positive(message = "Number of passengers must be positive")
    private Integer numberOfPassengers;

    private BigDecimal totalAmount;
    private String bookingStatus;
    private String bookingDate;

    private Flight flight;
    private String userEmail;

    public Booking() {
        this.bookingStatus = "CONFIRMED";
    }

    public Booking(Integer bookingId, Integer userId, Integer flightId, String journeyDate,
                   Integer numberOfPassengers, BigDecimal totalAmount, String bookingStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.flightId = flightId;
        this.journeyDate = journeyDate;
        this.numberOfPassengers = numberOfPassengers;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Integer getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(Integer numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", flightId=" + flightId +
                ", journeyDate='" + journeyDate + '\'' +
                ", numberOfPassengers=" + numberOfPassengers +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
