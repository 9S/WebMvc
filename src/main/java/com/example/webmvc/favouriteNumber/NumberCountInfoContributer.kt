package com.example.webmvc.favouriteNumber

import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.InfoContributor
import org.springframework.stereotype.Component

@Component
class NumberCountInfoContributor(
    private val favouriteNumberRepository: FavouriteNumberRepository
): InfoContributor {
    override fun contribute(builder: Info.Builder?) {
        if (builder == null) return

        val numberCount = favouriteNumberRepository.count();
        val countMap = HashMap<String, Any>()
        countMap["count"] = numberCount;
        builder.withDetail("number-stats", countMap)
    }
}