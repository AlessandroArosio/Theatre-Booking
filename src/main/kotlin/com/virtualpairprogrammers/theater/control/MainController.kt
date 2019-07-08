package com.virtualpairprogrammers.theater.control

import com.virtualpairprogrammers.theater.data.PerformanceRepository
import com.virtualpairprogrammers.theater.data.SeatRepository
import com.virtualpairprogrammers.theater.domain.Booking
import com.virtualpairprogrammers.theater.domain.Performance
import com.virtualpairprogrammers.theater.domain.Seat
import com.virtualpairprogrammers.theater.services.BookingService
import com.virtualpairprogrammers.theater.services.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView


@Controller
class MainController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage() : ModelAndView {
        val model = mapOf ("bean" to CheckAvailabilityBackingBean(),
                            "performances" to performanceRepository.findAll(),
                            "seatNums" to 1..36,
                            "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }


    @RequestMapping(value="checkAvailability", method= arrayOf(RequestMethod.POST))
    fun checkAvailability(bean : CheckAvailabilityBackingBean) : ModelAndView {
        val selectedSeat : Seat = bookingService.findSeat(bean.selectedSeatNum, bean.selectedSeatRow)!!
        val selectedPerformance = performanceRepository.findById(bean.selectedPerformance!!).get()
        bean.seat = selectedSeat
        bean.performance = selectedPerformance

        val result = bookingService.isSeatFree(selectedSeat, selectedPerformance)
        bean.available = result
        if (!result) {
            bean.booking = bookingService.findBooking(selectedSeat, selectedPerformance)
        }
        val model = mapOf ("bean" to bean,
                "performances" to performanceRepository.findAll(),
                "seatNums" to 1..36,
                "seatRows" to 'A'..'O')

        return ModelAndView("seatBooking", model)
    }


    @RequestMapping(value="booking", method= arrayOf(RequestMethod.POST))
    fun bookASeat(bean: CheckAvailabilityBackingBean) : ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

//    @RequestMapping("bootstrap")
//    fun createInitialData() : ModelAndView {
//        //create the data and save it to the database
//        val seats = theaterService.seats
//        seatRepository.saveAll(seats)
//        return homePage()
//    }

}



class CheckAvailabilityBackingBean() {
    var selectedSeatNum: Int = 1
    var selectedSeatRow: Char = 'A'
    var selectedPerformance : Long? = null
    var customerName: String = ""

    var available : Boolean? = null
    var seat : Seat? =null
    var performance: Performance? = null
    var booking : Booking? = null

}