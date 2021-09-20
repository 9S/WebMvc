package com.example.webmvc.favouriteNumber;

import com.example.webmvc.registration.User;
import com.example.webmvc.registration.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class FavouriteNumberController {
    private final FavouriteNumberService favouriteNumberService;
    private final UserService userService;

    public FavouriteNumberController(FavouriteNumberService favouriteNumberService, UserService userService) {
        this.favouriteNumberService = favouriteNumberService;
        this.userService = userService;
    }

    @PostMapping("/saveNumber")
    public String saveNumber(Principal principal, Model model, @ModelAttribute FavouriteNumber number, BindingResult result) {
        if (result.hasErrors()) {
            List<String> list = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", list);
            return "error";
        }

        Optional<User> maybeUser = userService.getUserByUsername(principal.getName());
        if (maybeUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your current user does not exist");
        }
        number.setUser(maybeUser.get());
        favouriteNumberService.saveNumber(number);
        return "redirect:listNumbers";
    }

    @GetMapping("/addNumber")
    public String addNumber(Model model) {
        model.addAttribute("favouriteNumber", new FavouriteNumber());
        return "addNumber";
    }

    @GetMapping("/listNumbers")
    public String listNumbers(Model model, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> result_count) {
        final var valid_result_counts = Arrays.asList(5, 10, 30, 100);
        var selected_result_count = result_count.orElse(valid_result_counts.get(0));

        if (!valid_result_counts.contains(selected_result_count)) {
            return "error";
        }

        int selected_page = page.orElse(0);
        var max_page_count = (favouriteNumberService.getNumberCount() + selected_result_count - 1) / selected_result_count;

        if (selected_page >= max_page_count || selected_page < 0) {
            model.addAttribute("errors", List.of(String.format("Requested page %d outside of Range 0 - %d", selected_page, max_page_count-1)));
            return "error";
        }

        var numbers = favouriteNumberService.getPaginated(selected_result_count, selected_page);

        model.addAttribute("numbers", numbers);
        model.addAttribute("valid_result_counts", valid_result_counts);
        model.addAttribute("selected_result_count", selected_result_count);
        model.addAttribute("max_page", max_page_count);
        model.addAttribute("selected_page", selected_page);
        model.addAttribute("next_page", selected_page + 1);
        model.addAttribute("back_page", selected_page - 1);
        return "listNumbers";
    }
}
