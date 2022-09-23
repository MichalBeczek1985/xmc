package com.example.demo.services.impl;

import com.example.demo.model.Crypto;
import com.example.demo.model.TimePrice;
import com.example.demo.services.DataProvider;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataProviderImpl implements DataProvider {
    private static final String SOURCE_PATH = "classpath*:prices/*.csv";
    private static final String SINGLE_FILE_SOURCE_PATH = "classpath*:prices/%s_values.csv";
    private static final String CODE_SEPARATOR = "_";
    private final ResourcePatternResolver patternResolver;

    @Override
    public List<String> getAvailableCodes() {
        try {
            Resource[] resources = getResources(SOURCE_PATH);
            return Arrays.stream(resources).map(resource ->
                            Objects.requireNonNull(resource.getFilename()).substring(0, resource.getFilename().indexOf(CODE_SEPARATOR)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Map<String, List<TimePrice>> getDataForCode(String code) {
        Map<String, List<TimePrice>> dataMap = new HashMap<>();
        try {
            Resource[] resources = getResources(String.format(SINGLE_FILE_SOURCE_PATH,code));
            if(resources.length>0) {
                Resource r = resources[0];
                List<Crypto> csvDate = readLinesFromCsvFile(r);
                if (!csvDate.isEmpty()) {
                    List<TimePrice> collect = csvDate.stream().map(cryptoTimePriceFunction()).sorted(Comparator.comparing(TimePrice::getDateTime)).collect(Collectors.toList());
                    dataMap.put(csvDate.get(0).getCode(), collect);
                }
            }

            } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    @Override
    public Map<String, List<TimePrice>> getAllData() {
        Map<String, List<TimePrice>> dataMap = new HashMap<>();
        try {
            Resource[] resources = getResources(SOURCE_PATH);
            for (Resource r : resources) {
                List<Crypto> csvDate = readLinesFromCsvFile(r);
                if (!csvDate.isEmpty()) {
                    List<TimePrice> collect = csvDate.stream().map(cryptoTimePriceFunction()).sorted(Comparator.comparing(TimePrice::getDateTime)).collect(Collectors.toList());
                    dataMap.put(csvDate.get(0).getCode(), collect);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    private Resource[] getResources(String sourcePath) throws IOException {
        return patternResolver.getResources(sourcePath);
    }

    private List<Crypto> readLinesFromCsvFile(Resource r) throws IOException {
        return new CsvToBeanBuilder(new FileReader(r.getFile().getPath()))
                .withType(Crypto.class)
                .build()
                .parse();
    }

    private Function<Crypto, TimePrice> cryptoTimePriceFunction() {
        return crypto -> new TimePrice(
                convert(crypto.getTimestamp()), crypto.getPrice());
    }

    private LocalDateTime convert(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


}
