package com.example.webmvc.favouriteNumber;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteNumberRepository extends JpaRepository<FavouriteNumber, Long> {
}
