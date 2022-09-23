package com.example.demo.services;

import com.example.demo.model.TimePrice;

import java.util.List;
import java.util.Map;

public interface DataProvider {
    List<String> getAvailableCodes();
    Map<String, List<TimePrice>> getDataForCode(String code);
    Map<String, List<TimePrice>> getAllData();
}
