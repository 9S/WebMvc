package com.example.webmvc.favouriteNumber

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavouriteNumberRepository : JpaRepository<FavouriteNumber, Long>