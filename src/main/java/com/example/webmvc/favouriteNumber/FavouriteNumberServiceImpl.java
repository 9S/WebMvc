package com.example.webmvc.favouriteNumber;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteNumberServiceImpl implements FavouriteNumberService {
    private final FavouriteNumberRepository favouriteNumberRepository;

    public FavouriteNumberServiceImpl(FavouriteNumberRepository favouriteNumberRepository) {
        this.favouriteNumberRepository = favouriteNumberRepository;
    }

    @Override
    public FavouriteNumber saveNumber(FavouriteNumber number) {
        return favouriteNumberRepository.saveAndFlush(number);
    }

    @Override
    public List<FavouriteNumber> getPaginated(int results_per_page, int page) {
        return favouriteNumberRepository.findAll(PageRequest.of(page, results_per_page, Sort.by("number")))
                .getContent();
    }

    @Override
    public List<FavouriteNumber> getAll() {
        return favouriteNumberRepository.findAll();
    }

    @Override
    public Optional<FavouriteNumber> findByID(long id) {
        return favouriteNumberRepository.findById(id);
    }

    @Override
    public long getNumberCount() {
        return favouriteNumberRepository.count();
    }

    @Override
    public void delete(FavouriteNumber number) {
        favouriteNumberRepository.delete(number);
    }

    @Override
    public FavouriteNumber getById(long id) {
        return favouriteNumberRepository.getById(id);
    }
}
