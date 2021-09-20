package com.example.webmvc.favouriteNumber;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FavouriteNumberRESTController {
    final FavouriteNumberService favouriteNumberService;
    final FavouriteNumberRESTDtoConverter favouriteNumberRESTDtoConverter;

    public FavouriteNumberRESTController(FavouriteNumberService favouriteNumberService, FavouriteNumberRESTDtoConverter favouriteNumberRESTDtoConverter) {
        this.favouriteNumberService = favouriteNumberService;
        this.favouriteNumberRESTDtoConverter = favouriteNumberRESTDtoConverter;
    }

    @GetMapping("/")
    public List<FavouriteNumberRESTDto> getPaginated(@RequestParam Optional<Integer> result_count, @RequestParam Optional<Integer> page) {
        var result = favouriteNumberService.getPaginated(result_count.orElse(30), page.orElse(0));
        return result.stream().map(favouriteNumberRESTDtoConverter::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FavouriteNumberRESTDto getByID(@PathVariable("id") long id) {
        Optional<FavouriteNumber> maybeNumber = favouriteNumberService.findByID(id);
        if (maybeNumber.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Number not found");
        }
        return favouriteNumberRESTDtoConverter.convertToDto(maybeNumber.get());
    }

    @DeleteMapping("/{id}")
    public void deleteByID(@PathVariable("id") long id) {
        Optional<FavouriteNumber> maybeNumber = favouriteNumberService.findByID(id);
        favouriteNumberService.delete(maybeNumber.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Number not found")));
    }

    @PutMapping("/{id}")
    public FavouriteNumberRESTDto put(@PathVariable long id, @RequestBody FavouriteNumberRESTDto number) {
        FavouriteNumber favouriteNumber = favouriteNumberService.getById(id);
        favouriteNumber.setNumber(number.getNumber());
        return favouriteNumberRESTDtoConverter.convertToDto(favouriteNumberService.saveNumber(favouriteNumber));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public FavouriteNumberRESTDto post(@RequestBody FavouriteNumberRESTDto number) {
        FavouriteNumber favouriteNumber = favouriteNumberRESTDtoConverter.convertFromDto(number).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
        return favouriteNumberRESTDtoConverter.convertToDto(favouriteNumberService.saveNumber(favouriteNumber));
    }
}
