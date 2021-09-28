package com.example.webmvc.favouriteNumber

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api")
class FavouriteNumberRESTController(
    private val favouriteNumberService: FavouriteNumberService,
    private val favouriteNumberRESTDtoConverter: FavouriteNumberRESTDtoConverter
) {
    @GetMapping("/")
    fun getPaginated(
        @RequestParam result_count: Optional<Int>,
        @RequestParam page: Optional<Int>
    ): List<FavouriteNumberRESTDto> {
        val result = favouriteNumberService.getPaginated(result_count.orElse(30), page.orElse(0))
        return result!!.stream().map { number: FavouriteNumber? -> favouriteNumberRESTDtoConverter.convertToDto(number!!) }
            .collect(Collectors.toList())
    }

    @GetMapping("/{id}")
    fun getByID(@PathVariable("id") id: Long): FavouriteNumberRESTDto {
        val maybeNumber = favouriteNumberService.findByID(id)
        if (maybeNumber != null && maybeNumber.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Number not found")
        }
        return favouriteNumberRESTDtoConverter.convertToDto(maybeNumber!!.get())
    }

    @DeleteMapping("/{id}")
    fun deleteByID(@PathVariable("id") id: Long) {
        val maybeNumber = favouriteNumberService.findByID(id)
        if (maybeNumber != null) {
            favouriteNumberService.delete(maybeNumber.orElseThrow {
                ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Number not found"
                )
            })
        }
    }

    @PutMapping("/{id}")
    fun put(@PathVariable id: Long, @RequestBody number: FavouriteNumberRESTDto): FavouriteNumberRESTDto {
        val favouriteNumber = favouriteNumberService.getById(id)
        if (favouriteNumber != null) {
            favouriteNumber.number = number.number
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Number not found")
        }
        return favouriteNumberRESTDtoConverter.convertToDto(favouriteNumberService.saveNumber(favouriteNumber)!!)
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody number: FavouriteNumberRESTDto?): FavouriteNumberRESTDto {
        if (number == null) throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        val favouriteNumber = favouriteNumberRESTDtoConverter.convertFromDto(number)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Not found") }
        return favouriteNumberRESTDtoConverter.convertToDto(favouriteNumberService.saveNumber(favouriteNumber)!!)
    }
}