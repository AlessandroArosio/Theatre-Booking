package com.virtualpairprogrammers.theater.services

import com.virtualpairprogrammers.theater.data.BookingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ReportingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    fun all_bookings() : String {
        val bookings = bookingRepository.findAll()
        val htmlBookings = bookings.map {"<tr><td>${it.performance.title}</td><td>${it.seat}</td><td>${it.customerName}</td></tr>"}
        val reportHeader = "<table><tr><th>Performance</th><th>Seat</th><th>Customer</th></tr>"
        val reportFooter = "</table>"
        return "${reportHeader}${htmlBookings.joinToString()}${reportFooter}"
    }

    fun premium_bookings() : String {
        val bookings = bookingRepository.findAll()
        val htmlBookings = bookings
                .filter {it.seat.price > BigDecimal(15) }
                .map {"<tr><td>${it.performance.title}</td><td>${it.seat}</td><td>${it.customerName}</td></tr>"}
        val reportHeader = "<table><tr><th>Performance</th><th>Seat</th><th>Customer</th></tr>"
        val reportFooter = "</table>"
        return "${reportHeader}${htmlBookings.joinToString()}${reportFooter}"
    }

}