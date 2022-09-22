package com.example.demo.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CryptoDtoNormalized {

    private String code;
    private double normalized;
}
