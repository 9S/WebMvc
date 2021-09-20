package com.example.webmvc.favouriteNumber;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FavouriteNumberRESTDto {
    private long id;
    private int number;
    private String username;

    public FavouriteNumberRESTDto(long id, int number) {
        this.id = id;
        this.number = number;
    }
    public FavouriteNumberRESTDto(int number) {
        this.number = number;
    }

}
