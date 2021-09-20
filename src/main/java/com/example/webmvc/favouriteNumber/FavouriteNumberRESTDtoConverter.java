package com.example.webmvc.favouriteNumber;

import com.example.webmvc.registration.UserService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FavouriteNumberRESTDtoConverter {
    private final FavouriteNumberService favouriteNumberService;
    private final UserService userService;

    public FavouriteNumberRESTDtoConverter(FavouriteNumberService favouriteNumberService, UserService userService) {
        this.favouriteNumberService = favouriteNumberService;
        this.userService = userService;
    }

    public FavouriteNumberRESTDto convertToDto(FavouriteNumber number) {
        var user = number.getUser();
        if (user != null) {
            return new FavouriteNumberRESTDto(number.getId(), number.getNumber(), user.getUsername());
        } else {
            return new FavouriteNumberRESTDto(number.getId(), number.getNumber());
        }
    }

    public Optional<FavouriteNumber> convertFromDto(FavouriteNumberRESTDto dto) {
        if (favouriteNumberService.findByID(dto.getId()).isEmpty()) {
            var user = userService.getUserByUsername(dto.getUsername());
            var num = new FavouriteNumber(dto.getNumber(), dto.getId());
            num.setUser(user.orElse(null));
            return Optional.of(num);
        }
        return favouriteNumberService.findByID(dto.getId());
    }
}
