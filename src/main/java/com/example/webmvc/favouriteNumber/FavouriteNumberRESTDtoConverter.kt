package com.example.webmvc.favouriteNumber

import com.example.webmvc.registration.UserService
import org.springframework.stereotype.Component
import java.util.*

@Component
class FavouriteNumberRESTDtoConverter(
    private val favouriteNumberService: FavouriteNumberService,
    private val userService: UserService
) {
    fun convertToDto(number: FavouriteNumber): FavouriteNumberRESTDto {
        val user = number.user
        return if (user != null) {
            FavouriteNumberRESTDto(number.id, number.number, user.username)
        } else {
            FavouriteNumberRESTDto(number.id!!, number.number)
        }
    }

    fun convertFromDto(dto: FavouriteNumberRESTDto): Optional<FavouriteNumber> {
        if (dto.id == null) return Optional.of(createNewNumber(dto.number, dto.username))
        val id: Long = dto.id!!

        val res: Optional<FavouriteNumber> = favouriteNumberService.findByID(id)!!
        return if (!res.isPresent) Optional.empty() else res
    }

    private fun createNewNumber(number: Int, username: String?): FavouriteNumber {
        val user = if (username != null) userService.getUserByUsername(username).orElse(null) else null
        val newNumber = FavouriteNumber(number)
        newNumber.user = user
        return newNumber
    }
}