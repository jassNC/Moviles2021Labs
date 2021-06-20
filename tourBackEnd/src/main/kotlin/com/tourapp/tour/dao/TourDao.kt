package com.tourapp.tour.dao

import com.tourapp.tour.model.*
import java.sql.*

import java.util.*
import java.util.Date
import kotlin.collections.ArrayList


object TourDao {
    private val username = "root"
    private val password = "root"
    private val host = "127.0.0.1"
    private val port = "3306"
    private var conn: Connection? = this.getConnection()

    @JvmStatic fun main(args: Array<String>) {
        getConnection()
    }


    fun getCategoryById(id: Int): Category{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = Category()

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.CATEGORY WHERE ID = ${id};")

            if(resultset!!.next()) {
                result = Category(resultset.getInt("ID"),resultset.getString("NAME"))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getActivities(): ArrayList<Activity> {
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<Activity>()

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.ACTIVITY;")

            while (resultset!!.next()) {
                result.add(Activity(resultset.getInt("ID"),resultset.getString("BODY")))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getActivitiesByTourId(tourId: Int):ArrayList<Activity>{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<Activity>()

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.TOUR_ACTIVITIES WHERE TOUR_ID = ${tourId};")

            while(resultset!!.next()) {
                result.add(Activity(resultset.getInt("AC_ID"),resultset.getString("AC_BODY")))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getTours(): ArrayList<Tour>{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<Tour>()

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.TOUR")

            while(resultset!!.next()) {
                result.add(Tour(resultset.getInt("ID"), resultset.getString("NAME"),
                                resultset.getString("DESCRIPTION"), resultset.getInt("RATING"),
                                resultset.getString("LEAVE_DATE"), resultset.getString("RETURN_DATE"),
                                resultset.getFloat("DURATION"),
                                resultset.getDouble("PRICE"), resultset.getInt("SEATS"),
                                resultset.getString("CITY"), resultset.getString("IMAGE"),
                                this.getCategoryById(resultset.getInt("CATEGORY_FK")),
                                this.getActivitiesByTourId(resultset.getInt("ID")),
                    this.getReviewsByTourId(resultset.getInt("ID")))
                            )
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getToursFiltered(tour: Tour): ArrayList<Tour>{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<Tour>()
        if(tour.checkIn == ""){
            tour.checkIn= "1999-01-01"
        }
        if(tour.checkOut == ""){
            tour.checkOut = "3000-01-01"
        }

        val a = "SELECT * FROM tourdb.TOUR T"+
                " WHERE T.LEAVE_DATE > STR_TO_DATE('${tour.checkIn}','%Y-%m-%d')" +
                "AND T.RETURN_DATE < STR_TO_DATE('${tour.checkOut}','%Y-%m-%d') AND"+
                " UPPER(T.CITY) LIKE UPPER('%${tour.city}%')"
        println(a)

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.TOUR T"+
                    " WHERE T.LEAVE_DATE > STR_TO_DATE('${tour.checkIn}','%Y-%m-%d')" +
                    "AND T.RETURN_DATE < STR_TO_DATE('${tour.checkOut}','%Y-%m-%d') AND"+
                    " UPPER(T.CITY) LIKE UPPER('%${tour.city}%')")

            while(resultset!!.next()) {
                result.add(Tour(resultset.getInt("ID"), resultset.getString("NAME"),
                    resultset.getString("DESCRIPTION"), resultset.getInt("RATING"),
                    resultset.getString("LEAVE_DATE"), resultset.getString("RETURN_DATE"),
                    resultset.getFloat("DURATION"),
                    resultset.getDouble("PRICE"), resultset.getInt("SEATS"),
                    resultset.getString("CITY"),
                    resultset.getString("IMAGE"),
                    this.getCategoryById(resultset.getInt("CATEGORY_FK")),
                    this.getActivitiesByTourId(resultset.getInt("ID")),
                    this.getReviewsByTourId(resultset.getInt("ID")))
                )
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getReviewsByTourId(id: Int): ArrayList<Review>{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<Review>()

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.REVIEW WHERE TOUR_FK = ${id};")

            while (resultset!!.next()) {
                result.add(Review(1,resultset.getString("BODY")))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getTourById(tour: Tour): Tour{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = Tour()
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.TOUR WHERE ID = ${tour.id}")

            if(resultset!!.next()) {
                result = (Tour(resultset.getInt("ID"), resultset.getString("NAME"),
                    resultset.getString("DESCRIPTION"), resultset.getInt("RATING"),
                    resultset.getString("LEAVE_DATE"), resultset.getString("RETURN_DATE"),
                    resultset.getFloat("DURATION"),
                    resultset.getDouble("PRICE"), resultset.getInt("SEATS"),
                    resultset.getString("CITY"),
                    resultset.getString("IMAGE"),
                    this.getCategoryById(resultset.getInt("CATEGORY_FK")),
                    this.getActivitiesByTourId(resultset.getInt("ID")),
                    this.getReviewsByTourId(resultset.getInt("ID")))
                )
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun putUser(user: User): Boolean{
        println("CALL tourdb.PUT_USER('${user.name}','${user.email}'," +
                "STR_TO_DATE('${user.birthDate}','%Y-%m-%d'),'${user.password}','${user.country}')")
        var stmt: Statement?

        try {
            stmt = conn!!.prepareCall("CALL tourdb.PUT_USER('${user.name}','${user.email}'," +
                                        "STR_TO_DATE('${user.birthDate}','%Y-%m-%d'),'${user.password}','${user.country}')")
            stmt!!.executeQuery()
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun getUser(user: User): User?{
        var stmt: Statement?
        var resultset: ResultSet?
        var result: User? = null
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.user WHERE EMAIL = '${user.email}' AND PASSWORD = '${user.password}';")
            if(resultset!!.next()) {
                result = User(resultset.getInt("ID"),resultset.getString("NAME"),
                    resultset.getString("EMAIL"), resultset.getString("BIRTH_DATE"),
                    resultset.getString("PASSWORD"),
                    resultset.getString("COUNTRY")
                )
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getUsers(): ArrayList<User>{
        var result = ArrayList<User>()
        var stmt: Statement?
        var resultset: ResultSet?
        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.user;")
            while(resultset!!.next()) {
                result.add(User(resultset.getInt("ID"),resultset.getString("NAME"),
                    resultset.getString("EMAIL"), resultset.getString("BIRTH_DATE"),
                    resultset.getString("PASSWORD"),
                    resultset.getString("COUNTRY"))
                )
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun addFav(user: User): Boolean{
        var stmt: Statement?
        try {
            stmt = conn!!.prepareCall("CALL tourdb.ADD_TO_FAV(${user.id},${user.favs[0].id})")
            stmt!!.executeQuery()
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun removeFav(user: User): Boolean{
        var stmt: Statement?
        try {
            stmt = conn!!.prepareCall("CALL tourdb.REMOVE_FROM_FAV(${user.id},${user.favs[0].id})")
            stmt!!.executeQuery()
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun getFavs(user: User): ArrayList<Int>{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<Int>()

        println("SELECT * FROM tourdb.FAVORITE WHERE USER_ID = ${user.id};")

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.FAVORITE WHERE USER_ID = ${user.id};")

            while (resultset!!.next()) {
                result.add(resultset.getInt("TOUR_ID"))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun putReservation(reservation: Reservation): Boolean{
        var stmt: Statement?
        try {
            stmt = conn!!.prepareCall("CALL tourdb.PUT_RESERVATION(${reservation.seats}," +
                    "${reservation.user.id},${reservation.tour.id})")
            stmt!!.executeQuery()
        } catch (ex: SQLException) {
            ex.printStackTrace()
            return false
        }
        return true
    }

    fun getLinks(tour: Tour): ArrayList<String>{
        var stmt: Statement?
        var resultset: ResultSet?
        var result = ArrayList<String>()

        try {
            stmt = conn!!.createStatement()
            resultset = stmt!!.executeQuery("SELECT * FROM tourdb.IMAGE WHERE TOUR_FK = ${tour.id};")

            while (resultset!!.next()) {
                result.add(resultset.getString("LINK"))
            }
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return result;
    }

    fun getConnection():Connection? {
        val connectionProps = Properties()
        connectionProps.put("user", username)
        connectionProps.put("password", password)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
            return DriverManager.getConnection("jdbc:mysql://$host:$port/",connectionProps)
        }catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null;
    }

}
