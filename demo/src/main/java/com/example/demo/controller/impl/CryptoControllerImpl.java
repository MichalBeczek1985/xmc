package com.example.demo.controller.impl;

import com.example.demo.controller.CryptoController;
import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.exceptions.CryptoDataNotFoundException;
import com.example.demo.services.impl.CryptoServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CryptoControllerImpl implements CryptoController {
    private final CryptoServiceImpl cryptoService;

    @Override
    public ResponseEntity<Object> getCryptoCodes() {
        return ResponseEntity.ok().body( cryptoService.getCryptoCodes());
    }

    @Override
    public ResponseEntity<Object> getCryptoPrices() {
        return ResponseEntity.ok().body( cryptoService.getAllPrices());
    }

    @Override
    public ResponseEntity<Object> getCryptoNormalized() {
        return ResponseEntity.ok().body(cryptoService.getCryptoNormalized());
    }

    @Override
    public ResponseEntity<Object> getCryptoNormalizedForDate(String date) {
        return ResponseEntity.ok().body(cryptoService.getCryptoNormalizedForDate(date));
    }

    @Override
    public ResponseEntity<Object> getCryptoStats() {
        return ResponseEntity.ok().body(cryptoService.getCryptoStats());
    }

    @Override
    public ResponseEntity<Object> getCryptoStatsForCode(String cryptoCode) {
        try {
            return ResponseEntity.ok().body(cryptoService.getCryptoStatsForCode(cryptoCode));
        } catch (CryptoCodeNotSupportedException | CryptoDataNotFoundException  e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
