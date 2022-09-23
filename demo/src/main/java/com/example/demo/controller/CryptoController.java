package com.example.demo.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/crypto")
public interface CryptoController {
    @GetMapping("/codes")
    @ApiOperation(value="Returns all crypto codes")
    ResponseEntity<Object> getCryptoCodes();

    @GetMapping("/prices")
    @ApiOperation(value="Returns all crypto prices")
    ResponseEntity<Object> getCryptoPrices();

    @GetMapping("/normalized")
    @ApiOperation(value="Returns  n a descending sorted list of all the cryptos,\n" +
            "comparing the normalized range")
    ResponseEntity<Object> getCryptoNormalized();

    @GetMapping("/normalized/{date}")
    @ApiOperation(value="Returns  n a descending sorted list of all the cryptos,\n" +
            "comparing the normalized range for the given date")
    ResponseEntity<Object> getCryptoNormalizedForDate(@ApiParam(name="date",example = "2022-02-02")
                                                      @RequestParam String date);

    @GetMapping("/stats")
    @ApiOperation(value="Returns  oldest/newest/min/max for each crypto ")
    ResponseEntity<Object> getCryptoStats();

    @GetMapping("/stats/{crypto_code}")
    @ApiOperation(value="Returns  oldest/newest/min/max for given crypto code ")
    @ApiResponses(value = {
            @ApiResponse(code=400, message = "Exception message when crypto code is not supported")
    })
    ResponseEntity<Object> getCryptoStatsForCode(@RequestParam String cryptoCode);
}
