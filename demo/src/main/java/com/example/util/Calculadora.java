package com.example.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	
	public double suma(double a, double b) {
		return roundIEEE754(a + b);
	}
	
	public int suma(int a, int b) {
		return a + b;
	}
	
	public double divide(double a, double b) {
		if(b == 0 ) {
			throw new ArithmeticException("/ by zero");
		}
		return roundIEEE754(a / b);
	}
	
	//Nombre del estándar de Java que provoca el fallo con operaciones como 0.1 + 0.3 o 1 - 0,9
	private double roundIEEE754(double o) {
		return BigDecimal.valueOf(o)
				.setScale(16, RoundingMode.HALF_UP)
				.doubleValue();
	}

}
