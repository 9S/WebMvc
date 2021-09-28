package com.example.webmvc.favouriteNumber

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class FavouriteNumberServiceImpl(
    private val favouriteNumberRepository: FavouriteNumberRepository) :
    FavouriteNumberService {
    override fun saveNumber(number: FavouriteNumber): FavouriteNumber? {
        return favouriteNumberRepository.saveAndFlush(number)
    }

    override fun getPaginated(results_per_page: Int, page: Int): List<FavouriteNumber>? {
        return favouriteNumberRepository.findAll(PageRequest.of(page, results_per_page, Sort.by("number")))
            .content
    }

    override val all: List<FavouriteNumber>
        get() = favouriteNumberRepository.findAll()

    override fun findByID(id: Long): Optional<FavouriteNumber>? {
        return favouriteNumberRepository.findById(id)
    }

    override val numberCount: Long
        get() = favouriteNumberRepository.count()

    override fun delete(number: FavouriteNumber?) {
        if (number == null) return
        favouriteNumberRepository.delete(number)
    }

    override fun getById(id: Long): FavouriteNumber? {
        return favouriteNumberRepository.getById(id)
    }
}