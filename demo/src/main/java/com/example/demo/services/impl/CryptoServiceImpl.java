package com.example.demo.services.impl;

import com.example.demo.exceptions.CryptoCodeNotSupportedException;
import com.example.demo.model.*;
import com.example.demo.services.CryptoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final DataProviderImpl dataProvider;


    @Override
    public List<String> getCryptoCodes() {
        return (List<String>) dataProvider.getData().keySet();
    }

    @Override
    public Map<String, List<TimePrice>> getAllPrices() {
        return dataProvider.getData();
    }

    @Override
    public List<CryptoDtoStats> getCryptoStats() {
//        List<CryptoDtoStats> dtoList = new ArrayList<>();
//        try {
//            Resource[] resources = getResources(SOURCE_PATH);
//            for (Resource r : resources) {
//                List<Crypto> beans = readLinesFromCsvFile(r);
//                CryptoDtoStats dto = prepareDtoStats(r, beans);
//                dtoList.add(dto);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public CryptoDtoStats getCryptoStatsForCode(String code) throws CryptoCodeNotSupportedException {
//        List<String> cryptoCodes = getCryptoCodes();
//        if (!cryptoCodes.contains(code)) {
//            throw new CryptoCodeNotSupportedException("Current code is not supported yet");
//        }
//        try {
//            Resource[] resources = getResources(String.format(SINGLE_FILE_SOURCE_PATH, code));
//            Resource r = resources[0];
//            List<Crypto> beans = readLinesFromCsvFile(r);
//            CryptoDtoStats dto = prepareDtoStats(r, beans);
//            return dto;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
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
//        LocalDateTime givenDate = LocalDate.parse(date).atStartOfDay();
//        List<CryptoDto> allPrices = getAllPrices();
//        List<CryptoDtoNormalized> normalizedList = new ArrayList<>();
//        for (CryptoDto cd : allPrices) {
//            TimePrice maxPrice = cd.getTimePrice().stream().filter(timePrice -> timePrice.getDateTime().toLocalDate().equals(givenDate.toLocalDate())).max(Comparator.comparing(TimePrice::getPrice)).orElse(null);
//            TimePrice minPrice = cd.getTimePrice().stream().filter(timePrice -> timePrice.getDateTime().toLocalDate().equals(givenDate.toLocalDate())).min(Comparator.comparing(TimePrice::getPrice)).orElse(null);
//            if (maxPrice != null && minPrice != null) {
//                normalizedList.add(new CryptoDtoNormalized(cd.getCode(), (maxPrice.getPrice() - minPrice.getPrice()) / minPrice.getPrice()));
//            }
//        }
        return Collections.emptyList();
    }


    private void prepareDtoStats(Resource r, List<Crypto> beans) {
//        CryptoDtoStats dto = new CryptoDtoStats();
//        dto.setCode(r.getFilename().substring(0, r.getFilename().indexOf(CODE_SEPARATOR)));
//        setComparedValuesToDto(beans, dto);
//        return dto;
    }

    private void setComparedValuesToDto(List<Crypto> beans, CryptoDtoStats dto) {
//        Comparator<TimePrice> timeComparator = Comparator.comparing(TimePrice::getDateTime);
//        Comparator<TimePrice> priceComparator = Comparator.comparing(TimePrice::getPrice);
//        dto.setNewest(beans.stream().map(cryptoTimePriceFunction()).max(timeComparator).get());
//        dto.setOldest(beans.stream().map(cryptoTimePriceFunction()).min(timeComparator).get());
//        dto.setMinPrice(beans.stream().map(cryptoTimePriceFunction()).min(priceComparator).get());
//        dto.setMaxPrice(beans.stream().map(cryptoTimePriceFunction()).max(priceComparator).get());
    }







}
