package com.example.demo.services.impl;

import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.exceptions.CryptoDataNotFoundException;
import com.example.demo.model.CryptoDtoNormalized;
import com.example.demo.model.CryptoDtoStats;
import com.example.demo.model.TimePrice;
import com.example.demo.services.CryptoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final DataProviderImpl dataProvider;


    @Override
    public List<String> getCryptoCodes() {
        return dataProvider.getAvailableCodes();
    }

    @Override
    public Map<String, List<TimePrice>> getAllPrices() {
        return dataProvider.getAllData();
    }

    @Override
    public List<CryptoDtoStats> getCryptoStats() {
        List<CryptoDtoStats> dtoList = new ArrayList<>();
        Map<String, List<TimePrice>> data = dataProvider.getAllData();
        for (Map.Entry<String, List<TimePrice>> entries : data.entrySet()) {
            CryptoDtoStats dto = prepareDtoStats(entries.getKey(), entries.getValue());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public CryptoDtoStats getCryptoStatsForCode(String code) throws CryptoCodeNotSupportedException, CryptoDataNotFoundException {
        List<String> cryptoCodes = getCryptoCodes();
        if (!cryptoCodes.contains(code)) {
            throw new CryptoCodeNotSupportedException("Current code is not supported yet");
        }
        Map<String, List<TimePrice>> data = dataProvider.getDataForCode(code);
        if(data.isEmpty()){
            throw new CryptoDataNotFoundException("There is no data for provided code");
        }
        CryptoDtoStats dto = prepareDtoStats(code, data.get(code));
        return dto;
    }

    @Override
    public List<CryptoDtoNormalized> getCryptoNormalized() {
        List<CryptoDtoStats> cryptoStats = getCryptoStats();
        Comparator<CryptoDtoNormalized> valComparator = Comparator.comparing(CryptoDtoNormalized::getNormalized);
        List<CryptoDtoNormalized> normalizedList = cryptoStats.stream().map(stats ->
                        new CryptoDtoNormalized(stats.getCode(), (stats.getMaxPrice().getPrice() - stats.getMinPrice().getPrice()) / stats.getMinPrice().getPrice()))
                .sorted(valComparator.reversed()).collect(Collectors.toList());
        return normalizedList;
    }

    @Override
    public List<CryptoDtoNormalized> getCryptoNormalizedForDate(String date) {
        LocalDateTime givenDate = LocalDate.parse(date).atStartOfDay();
        Map<String, List<TimePrice>> allPrices = dataProvider.getAllData();
        List<CryptoDtoNormalized> normalizedList = new ArrayList<>();
        for (Map.Entry<String, List<TimePrice>> cd : allPrices.entrySet()) {
            TimePrice maxPrice = cd.getValue().stream().filter(getPredicate(givenDate)).max(getPriceComparator()).orElse(null);
            TimePrice minPrice = cd.getValue().stream().filter(getPredicate(givenDate)).min(getPriceComparator()).orElse(null);
            if (maxPrice != null && minPrice != null) {
                normalizedList.add(new CryptoDtoNormalized(cd.getKey(), (maxPrice.getPrice() - minPrice.getPrice()) / minPrice.getPrice()));
            }
        }
        return normalizedList;
    }

    private Comparator<TimePrice> getPriceComparator() {
        return Comparator.comparing(TimePrice::getPrice);
    }
    private Comparator<TimePrice> getTimeComparator() {
        return Comparator.comparing(TimePrice::getDateTime);
    }
    private Predicate<TimePrice> getPredicate(LocalDateTime givenDate){
        return time -> time.getDateTime().toLocalDate().equals(givenDate.toLocalDate());
    }

    private CryptoDtoStats prepareDtoStats(String code, List<TimePrice> list) {
        CryptoDtoStats dto = new CryptoDtoStats();
        dto.setCode(code);
        setComparedValuesToDto(list, dto);
        return dto;
    }

    private void setComparedValuesToDto(List<TimePrice> list, CryptoDtoStats dto) {
        dto.setNewest(list.stream().max(getTimeComparator()).get());
        dto.setOldest(list.stream().min(getTimeComparator()).get());
        dto.setMinPrice(list.stream().min(getPriceComparator()).get());
        dto.setMaxPrice(list.stream().max(getPriceComparator()).get());
    }
}
