package com.example.ioc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@NoArgsConstructor @AllArgsConstructor
@Component
//le dice que vaya a buscar en el application.properties valores que empiecen con "rango"
@ConfigurationProperties("rango")
public class Rango {
	
	private int min;
	private int max;
	

}
