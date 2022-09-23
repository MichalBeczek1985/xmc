package com.example.demo.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Crypto {
    @CsvBindByName(column = "timestamp")
    private long timestamp;
    @CsvBindByName(column = "symbol")
    private String code;
    @CsvBindByName(column = "price")
    private double price;
}
