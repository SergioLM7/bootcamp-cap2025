package com.example.util;

public class Calculadora {
	
	public double suma(double a, double b) {
		return a + b;
	}
	
	public int suma(int a, int b) {
		return a + b;
	}
	
	public double divide(double a, double b) {
		if(b == 0 ) {
			throw new ArithmeticException("/ by zero");
		}
		return a / b;
	}

}
