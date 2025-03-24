package com.example.consumingwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.example.consumingwebservice.wsdl.GetCountryResponse;

@SpringBootApplication
public class LaboratorioSoapWebConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(LaboratorioSoapWebConsumerApplication.class, args);
  }

  @Bean
  CommandLineRunner lookup(CountryClient countryClient) {
    return args -> {
      String country = "Poland";

      if (args.length > 0) {
        country = args[0];
      }
      GetCountryResponse response = countryClient.getCountry(country);
      System.err.println(response.getCountry().getCurrency());
    };
  }

}