package com.example.demo.services;

import com.example.demo.model.TimePrice;

import java.util.List;
import java.util.Map;

public interface DataProvider {
    Map<String, List<TimePrice>> getData();
}
