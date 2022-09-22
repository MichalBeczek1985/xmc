package com.example.demo.services;

import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.model.CryptoDto;
import com.example.demo.model.CryptoDtoNormalized;
import com.example.demo.model.CryptoDtoStats;

import java.util.List;

public interface CryptoService {
    List<String> getCryptoCodes();
    List<CryptoDto> getAllPrices();
    List<CryptoDtoStats> getCryptoStats();
    CryptoDtoStats getCryptoStatsForCode(String code) throws CryptoCodeNotSupportedException;
    List<CryptoDtoNormalized> getCryptoNormalized();
    List<CryptoDtoNormalized> getCryptoNormalizedForDate(String date);
}
