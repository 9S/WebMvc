package com.example.webmvc.favouriteNumber;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FavouriteNumberService {
    FavouriteNumber saveNumber(FavouriteNumber number);

    List<FavouriteNumber> getPaginated(int results_per_page, int page);
    List<FavouriteNumber> getAll();

    Optional<FavouriteNumber> findByID(long id);

    long getNumberCount();

    void delete(FavouriteNumber number);

    FavouriteNumber getById(long id);
}
