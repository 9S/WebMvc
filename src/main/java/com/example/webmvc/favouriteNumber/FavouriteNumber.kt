package com.example.webmvc.favouriteNumber

import com.example.webmvc.favouriteNumber.FavouriteNumber
import com.example.webmvc.registration.User
import org.hibernate.Hibernate
import javax.persistence.*

@Entity
data class FavouriteNumber(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var number: Int,
) {
    @ManyToOne
    var user: User? = null

    constructor(number: Int) : this(null, number)
    constructor() : this(null, 0)
    constructor(number: FavouriteNumber) : this(number.id, number.number)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FavouriteNumber

        return id != null && id == other.id
    }

    override fun hashCode(): Int = 0

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , number = $number , user = $user )"
    }
}