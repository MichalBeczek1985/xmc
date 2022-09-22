package com.example.demo.services;

import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.model.CryptoDtoNormalized;
import com.example.demo.model.CryptoDtoStats;
import com.example.demo.model.TimePrice;

import java.util.List;
import java.util.Map;

public interface CryptoService {
    List<String> getCryptoCodes();
    Map<String, List<TimePrice>> getAllPrices();
    List<CryptoDtoStats> getCryptoStats();
    CryptoDtoStats getCryptoStatsForCode(String code) throws CryptoCodeNotSupportedException;
    List<CryptoDtoNormalized> getCryptoNormalized();
    List<CryptoDtoNormalized> getCryptoNormalizedForDate(String date);
}
