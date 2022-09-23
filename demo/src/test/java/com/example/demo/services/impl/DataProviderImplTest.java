package com.example.demo.services.impl;

import com.example.demo.model.Crypto;
import com.example.demo.model.TimePrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DataProviderImplTest {
    private static final String SOURCE_PATH = "classpath*:testPrices/*.csv";
    private static final String SINGLE_FILE_SOURCE_PATH = "classpath*:prices/%s_values.csv";
    @Mock
    private ResourcePatternResolver patternResolver;

    @InjectMocks
    private DataProviderImpl dataProvider;


    Resource[] resources;
    @BeforeEach
    void setup() throws IOException {
        resources = getResources(SOURCE_PATH);
    }

    @Test
    void getAvailableCodes() throws IOException {
        when(patternResolver.getResources(anyString())).thenReturn(resources);

        List<String> availableCodes = dataProvider.getAvailableCodes();

        assertThat(availableCodes.size()).isEqualTo(4);
    }

    @Test
    void getDataForNonExistingCode() throws IOException {
        String code ="xxx";

        when(patternResolver.getResources(anyString())).thenReturn(getResources(String.format(SINGLE_FILE_SOURCE_PATH,code)));

        Map<String, List<TimePrice>> allData = dataProvider.getDataForCode(code);

        assertThat(allData.isEmpty());
    }
    @Test
    void getDataForExistingCode() throws IOException {
        String code ="BTC";

        when(patternResolver.getResources(anyString())).thenReturn(getResources(String.format(SINGLE_FILE_SOURCE_PATH,code)));

        Map<String, List<TimePrice>> allData = dataProvider.getDataForCode(code);

        assertThat(allData.size()).isEqualTo(1);
    }


    @Test
    void getAllData() throws IOException {
        when(patternResolver.getResources(anyString())).thenReturn(resources);

        Map<String, List<TimePrice>> allData = dataProvider.getAllData();

        assertThat(allData.size()).isEqualTo(4);

    }


    private Resource[] getResources(String sourcePath) throws IOException {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        return patternResolver.getResources(sourcePath);
    }
}