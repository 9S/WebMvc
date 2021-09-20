package com.example.webmvc.favouriteNumber;

import com.example.webmvc.registration.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FavouriteNumber {
    @Id
    @GeneratedValue
    private Long id;

    private int number = 0;

    @ManyToOne
    private User user;

    public FavouriteNumber(int number, Long id) {
        this.number = number;
        this.id = id;
    }

    public FavouriteNumber(int number) {
        this.number = number;
    }

    public FavouriteNumber() {
    }

    public FavouriteNumber(FavouriteNumber number) {
        this(number.getNumber(), number.getId());
        this.setUser(number.getUser());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
