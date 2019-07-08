package com.virtualpairprogrammers.theater.domain

import javax.persistence.*

@Entity
data class Performance(@Id @GeneratedValue (strategy=GenerationType.AUTO)
                        val id: Long,
                       val title: String) {

    @OneToMany(mappedBy = "performance")
    lateinit var bookings: List<Booking>
}