package com.example.ioc;

import com.example.util.Calculadora;

public class Factura {
	Calculadora calculadora;

	public Factura(Calculadora calculadora) {
		super();
		this.calculadora = calculadora;
	}
	
	public double calcularTotal(int cantidad, int precio) {
		return calculadora.suma(cantidad, precio);
	}
}