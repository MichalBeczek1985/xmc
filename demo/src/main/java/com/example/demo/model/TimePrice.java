package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public class TimePrice{
        private LocalDateTime dateTime;
        private double price;
    }