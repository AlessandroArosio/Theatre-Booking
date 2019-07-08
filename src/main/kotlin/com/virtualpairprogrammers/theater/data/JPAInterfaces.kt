package com.virtualpairprogrammers.theater.data

import com.virtualpairprogrammers.theater.domain.Booking
import com.virtualpairprogrammers.theater.domain.Performance
import com.virtualpairprogrammers.theater.domain.Seat
import org.springframework.data.jpa.repository.JpaRepository

interface SeatRepository : JpaRepository<Seat, Long>

interface PerformanceRepository: JpaRepository<Performance, Long>

interface BookingRepository: JpaRepository<Booking, Long>