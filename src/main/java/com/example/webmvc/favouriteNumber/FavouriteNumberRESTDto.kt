package com.example.webmvc.favouriteNumber

data class FavouriteNumberRESTDto(
    var id: Long?,
    var number: Int,
    var username: String?,
) {
    constructor(id: Long, number: Int) : this(id, number, null)
    constructor(number: Int) : this(null, number, null)
    constructor(): this(null, 0, null)
}