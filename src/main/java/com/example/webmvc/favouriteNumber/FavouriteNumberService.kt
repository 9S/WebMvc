package com.example.webmvc.favouriteNumber

import org.springframework.stereotype.Service
import java.util.*

@Service
interface FavouriteNumberService {
    val all: List<FavouriteNumber>?
    val numberCount: Long

    fun saveNumber(number: FavouriteNumber): FavouriteNumber?
    fun getPaginated(results_per_page: Int, page: Int): List<FavouriteNumber>?
    fun findByID(id: Long): Optional<FavouriteNumber>?
    fun delete(number: FavouriteNumber?)
    fun getById(id: Long): FavouriteNumber?
}