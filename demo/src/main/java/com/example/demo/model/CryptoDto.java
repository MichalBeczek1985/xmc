package com.example.demo.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CryptoDto {

    private String code;
    private List<TimePrice> timePrice;



}
