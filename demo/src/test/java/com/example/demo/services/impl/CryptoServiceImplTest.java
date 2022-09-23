package com.example.demo.services.impl;

import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.exceptions.CryptoDataNotFoundException;
import com.example.demo.model.Crypto;
import com.example.demo.model.CryptoDtoNormalized;
import com.example.demo.model.CryptoDtoStats;
import com.example.demo.model.TimePrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CryptoServiceImplTest {

    @Mock
    DataProviderImpl dataProvider;

    @InjectMocks
    CryptoServiceImpl cryptoService;

    Map<String, List<TimePrice>> dataMap = new HashMap<>();
    @BeforeEach
    void setup() throws IOException {
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
        dataMap.put("LTC",List.of(
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
        List<String> crypto = List.of("BTC", "DOGE", "ETH", "LTC");
        when(dataProvider.getAvailableCodes()).thenReturn(crypto);

        List<String> availableCodes = cryptoService.getCryptoCodes();
        assertThat(availableCodes.size()).isEqualTo(4);
    }

    @Test
    void getAllPrices() {
        when(dataProvider.getAllData()).thenReturn(dataMap);

        Map<String, List<TimePrice>> allPrices = cryptoService.getAllPrices();

        assertThat(allPrices.size()).isEqualTo(3);
    }

    @Test
    void getCryptoStats() {
        when(dataProvider.getAllData()).thenReturn(dataMap);

        List<CryptoDtoStats> cryptoStats = cryptoService.getCryptoStats();

        assertThat(cryptoStats.size()).isEqualTo(3);

    }

    @ParameterizedTest
    @MethodSource("paramsProvider")
    void getCryptoStatsForCode(String code, LocalDateTime maxDate,LocalDateTime minDate, double minVal, double maxVal) throws CryptoDataNotFoundException, CryptoCodeNotSupportedException {
        List<String> crypto = List.of("BTC", "DOGE", "ETH", "LTC");
        when(dataProvider.getAvailableCodes()).thenReturn(crypto);
        when(dataProvider.getDataForCode(code)).thenReturn(dataMap);

        CryptoDtoStats cryptoStatsForCode = cryptoService.getCryptoStatsForCode(code);

        assertThat(cryptoStatsForCode.getMinPrice().getPrice()).isEqualTo(minVal);
        assertThat(cryptoStatsForCode.getMaxPrice().getPrice()).isEqualTo(maxVal);
        assertThat(cryptoStatsForCode.getOldest().getDateTime()).isEqualTo(minDate);
        assertThat(cryptoStatsForCode.getNewest().getDateTime()).isEqualTo(maxDate);

    }

    @Test
    void getCryptoNormalized() {
        when(dataProvider.getAllData()).thenReturn(dataMap);

        List<CryptoDtoNormalized> cryptoNormalized = cryptoService.getCryptoNormalized();

        assertThat(cryptoNormalized.size()).isEqualTo(3);
    }

    @Test
    void getCryptoNormalizedForDate() {
        when(dataProvider.getAllData()).thenReturn(dataMap);

        List<CryptoDtoNormalized> cryptoNormalized = cryptoService.getCryptoNormalizedForDate("2022-01-01");

        assertThat(cryptoNormalized.size()).isEqualTo(3);
    }


    private static Stream<Arguments> paramsProvider() {
        return Stream.of(
                Arguments.of("BTC", LocalDateTime.of(2022,1,3,17,0,0),LocalDateTime.of(2022,1,1,11,0,0),0.7113,0.7727),
                Arguments.of("ETH", LocalDateTime.of(2022,1,3,17,0,0),LocalDateTime.of(2022,1,1,11,0,0),0.7113,0.7727),
                Arguments.of("LTC", LocalDateTime.of(2022,1,3,17,0,0),LocalDateTime.of(2022,1,1,11,0,0),0.7113,0.7727)
        );
    }
}