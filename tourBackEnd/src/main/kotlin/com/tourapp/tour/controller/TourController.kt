package com.tourapp.tour.controller


import com.tourapp.tour.model.Reservation
import com.tourapp.tour.model.Tour
import com.tourapp.tour.model.User
import com.tourapp.tour.service.TourService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody

import org.springframework.web.bind.annotation.RequestMethod

import org.springframework.web.bind.annotation.RequestMapping

@CrossOrigin
@RestController
@RequestMapping("/tourApi")
class TourController {
    val service = TourService()

    @RequestMapping(value =["/users"], method = [RequestMethod.GET])
    fun getUsers():ResponseEntity<ArrayList<User>>{
        return ResponseEntity.ok(service.getUsers())
    }

    @RequestMapping(value =["/users"], method = [RequestMethod.POST])
    fun getUser(@RequestBody user: User): ResponseEntity<User>{
        return ResponseEntity.ok(service.getUser(user))
    }

    @RequestMapping(value = ["/users"], method = [RequestMethod.PUT])
    fun putUser(@RequestBody user: User):ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.registerUser(user));
    }

    @RequestMapping(value = ["/links"], method = [RequestMethod.POST])
    fun getLinks(@RequestBody tour: Tour):ResponseEntity<ArrayList<String>> {
        return ResponseEntity.ok(service.getLinks(tour));
    }

    @RequestMapping(value = ["/reservations"], method = [RequestMethod.PUT])
    fun putReservation(@RequestBody reservation: Reservation):ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.putReservation(reservation));
    }

    @RequestMapping(value = ["/favotites"], method = [RequestMethod.POST])
    fun addFav(@RequestBody user: User):ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.addFav(user));
    }

    @RequestMapping(value = ["/favorites"], method = [RequestMethod.DELETE])
    fun removeFav(@RequestBody user: User):ResponseEntity<Boolean> {
        return ResponseEntity.ok(service.removeFav(user));
    }

    @RequestMapping(value = ["/favorites"], method = [RequestMethod.POST])
    fun getFavs(@RequestBody user: User): ResponseEntity<ArrayList<Int>>{
        return ResponseEntity.ok(service.getFavs(user));
    }


    @RequestMapping(value = ["/tours"], method = [RequestMethod.GET])
    fun getTours():ResponseEntity<ArrayList<Tour>> {
        return ResponseEntity.ok(service.getTours())
    }

    @RequestMapping(value = ["/tours"], method = [RequestMethod.POST])
    fun getToursFiltered(@RequestBody tour: Tour):ResponseEntity<ArrayList<Tour>> {
        println(tour.checkIn)
        println(tour.checkOut)
        return ResponseEntity.ok(service.getToursFiltered(tour))
    }

    /*
    @RequestMapping(value = ["/tours"], method = [RequestMethod.POST])
    fun getTourById(@RequestBody tour: Tour):ResponseEntity<Tour> {
        return ResponseEntity.ok(service.getTourById(tour))
    }*/



}