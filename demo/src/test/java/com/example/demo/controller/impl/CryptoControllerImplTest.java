package com.example.demo.controller.impl;

import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.exceptions.CryptoDataNotFoundException;
import com.example.demo.model.CryptoDtoNormalized;
import com.example.demo.model.TimePrice;
import com.example.demo.services.impl.CryptoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoControllerImplTest {

    @InjectMocks
    CryptoControllerImpl cryptoController;

    @Mock
    CryptoServiceImpl cryptoService;
    Map<String, List<TimePrice>> dataMap = new HashMap<>();
    @BeforeEach
    void setup(){
        dataMap.put("BTC",List.of(
                new TimePrice(LocalDateTime.of(2022,1,1,11,0,0), 0.7723),
                new TimePrice(LocalDateTime.of(2022,1,1,13,0,0), 0.7492),
                new TimePrice(LocalDateTime.of(2022,1,1,17,0,0),  0.7521),
                new TimePrice(LocalDateTime.of(2022,1,2,11,0,0), 0.7727),
                new TimePrice(LocalDateTime.of(2022,1,2,13,0,0), 0.7606),
                new TimePrice(LocalDateTime.of(2022,1,2,17,0,0), 0.73),
                new TimePrice(LocalDateTime.of(2022,1,3,11,0,0), 0.7556),
                new TimePrice(LocalDateTime.of(2022,1,3,13,0,0), 0.7226),
                new TimePrice(LocalDateTime.of(2022,1,3,17,0,0), 0.7113)
        ));
        dataMap.put("ETH",List.of(
                new TimePrice(LocalDateTime.of(2022,1,1,11,0,0), 0.7723),
                new TimePrice(LocalDateTime.of(2022,1,1,13,0,0), 0.7492),
                new TimePrice(LocalDateTime.of(2022,1,1,17,0,0),  0.7521),
                new TimePrice(LocalDateTime.of(2022,1,2,11,0,0), 0.7727),
                new TimePrice(LocalDateTime.of(2022,1,2,13,0,0), 0.7606),
                new TimePrice(LocalDateTime.of(2022,1,2,17,0,0), 0.73),
                new TimePrice(LocalDateTime.of(2022,1,3,11,0,0), 0.7556),
                new TimePrice(LocalDateTime.of(2022,1,3,13,0,0), 0.7226),
                new TimePrice(LocalDateTime.of(2022,1,3,17,0,0), 0.7113)
        ));
        dataMap.put("XRP",List.of(
                new TimePrice(LocalDateTime.of(2022,1,1,11,0,0), 0.7723),
                new TimePrice(LocalDateTime.of(2022,1,1,13,0,0), 0.7492),
                new TimePrice(LocalDateTime.of(2022,1,1,17,0,0),  0.7521),
                new TimePrice(LocalDateTime.of(2022,1,2,11,0,0), 0.7727),
                new TimePrice(LocalDateTime.of(2022,1,2,13,0,0), 0.7606),
                new TimePrice(LocalDateTime.of(2022,1,2,17,0,0), 0.73),
                new TimePrice(LocalDateTime.of(2022,1,3,11,0,0), 0.7556),
                new TimePrice(LocalDateTime.of(2022,1,3,13,0,0), 0.7226),
                new TimePrice(LocalDateTime.of(2022,1,3,17,0,0), 0.7113)
        ));
    }

    @Test
    void getCryptoCodes() {
        List<String> crypto = List.of("BTC", "DOGE", "ETH", "LTC", "XRP");

        when(cryptoService.getCryptoCodes()).thenReturn(crypto);

        ResponseEntity<Object> response = cryptoController.getCryptoCodes();
        List<String> body = (List<String>) response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(body.size()).isEqualTo(5);
    }

    @Test
    void getCryptoPrices() {
        when(cryptoService.getAllPrices()).thenReturn(dataMap);

        ResponseEntity<Object> response = cryptoController.getCryptoPrices();
        Map<String, List<TimePrice>> body =(Map<String, List<TimePrice>>) response.getBody();

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(body.size()).isEqualTo(3);
    }

    @Test
    void getCryptoStatsForCode() throws CryptoDataNotFoundException, CryptoCodeNotSupportedException {

        when(cryptoService.getCryptoStatsForCode("xxx")).thenThrow(new CryptoCodeNotSupportedException("Current code is not supported yet"));
        ResponseEntity<Object> response = cryptoController.getCryptoStatsForCode("xxx");
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(response.getBody().toString()).isEqualTo("Current code is not supported yet");
    }
}