package com.example.webmvc.favouriteNumber

import com.example.webmvc.registration.User
import com.example.webmvc.registration.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import java.security.Principal
import java.util.*
import java.util.stream.Collectors

@Controller
class FavouriteNumberController(
    private val favouriteNumberService: FavouriteNumberService,
    private val userService: UserService) {

    @PostMapping("/saveNumber")
    fun saveNumber(
        principal: Principal,
        model: Model,
        @ModelAttribute number: FavouriteNumber?,
        result: BindingResult
    ): String {
        if (result.hasErrors()) {
            val list = result.allErrors
                .stream()
                .map { obj: ObjectError -> obj.defaultMessage }
                .collect(Collectors.toList())
            model.addAttribute("errors", list)
            return "error"
        }
        val maybeUser = userService.getUserByUsername(principal.name)
        if (maybeUser.isEmpty) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Your current user does not exist")
        }
        number!!.user = maybeUser.get()
        favouriteNumberService.saveNumber(number)
        return "redirect:listNumbers"
    }

    @GetMapping("/addNumber")
    fun addNumber(model: Model): String {
        model.addAttribute("favouriteNumber", FavouriteNumber())
        return "addNumber"
    }

    @GetMapping("/listNumbers")
    fun listNumbers(
        model: Model,
        @RequestParam page: Optional<Int>,
        @RequestParam result_count: Optional<Int>
    ): String {
        val validResultCounts = listOf(5, 10, 30, 100)
        val selectedResultCount = result_count.orElse(validResultCounts[0])
        if (!validResultCounts.contains(selectedResultCount)) {
            return "error"
        }
        val selectedPage = page.orElse(0)
        val maxPageCount = (favouriteNumberService.numberCount + selectedResultCount - 1) / selectedResultCount
        if (selectedPage >= maxPageCount || selectedPage < 0) {
            model.addAttribute(
                "errors",
                listOf(String.format("Requested page %d outside of Range 0 - %d", selectedPage, maxPageCount - 1))
            )
            return "error"
        }
        val numbers = favouriteNumberService.getPaginated(selectedResultCount, selectedPage)
        model.addAttribute("numbers", numbers)
        model.addAttribute("valid_result_counts", validResultCounts)
        model.addAttribute("selected_result_count", selectedResultCount)
        model.addAttribute("max_page", maxPageCount)
        model.addAttribute("selected_page", selectedPage)
        model.addAttribute("next_page", selectedPage + 1)
        model.addAttribute("back_page", selectedPage - 1)
        return "listNumbers"
    }
}