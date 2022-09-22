package com.example.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CryptoDtoStats {

    private String code;
    private TimePrice oldest;
    private TimePrice newest;
    private TimePrice maxPrice;
    private TimePrice minPrice;

}
