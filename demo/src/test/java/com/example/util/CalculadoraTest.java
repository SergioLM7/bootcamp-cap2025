package com.example.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;

import com.example.interfaces.CalculadoraInt;
import com.example.ioc.Factura;
import com.example.utils.Smoke;

class CalculadoraTest {
	
	Calculadora calc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setup() throws Exception {
		calc = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("Método: suma")
	class Suma {
		
		@Nested
		@DisplayName("Casos válidos")
		class OK {
			@Test
			@DisplayName("Suma de dos números enteros")
			//@Tag("Smoke")
			//Anotación personalizada para evitar que cometamos el error de meter mayúscula o minúscula cuando no corresponde
			@Smoke
			void testSuma() {		
				var actual = calc.suma(2, 3);

				assertEquals(5, actual);
			}
			
			@Test
			@DisplayName("Suma de dos números reales")
			void testSumaDoublesResta() {		
				var actual = calc.suma(0.1, 0.2);

				assertEquals(0.3, actual);				
			}
			
			@Test
			@DisplayName("Suma de dos números reales: resta")
			void testSumaDoubles() {		
				var actual = calc.suma(1, -0.9);
				
				assertEquals(0.1, calc.suma(1, -0.9));
			}
			
			@DisplayName("Suma de dos números reales: PARAMETRIZADO")
			@ParameterizedTest(name = "{0} + {1} = {2}") 
			@CsvSource({ "1,2,3", "2,-1,1", "-1,2,1", "-2,-1,-3", "0,0,0"})
			void testSumaParametrizada(double operando1, double operando2, double expected) {		
				var actual = calc.suma(operando1, operando2);

				assertEquals(expected, actual);				
			}
			
		}
		
		
		@Nested
		@DisplayName("Casos inválidos")
		class KO {

			@Disabled
			@Test
			@DisplayName("Suma de dos números enteros grandes")
			void testSuma2() {		
				var actual = calc.suma(Integer.MAX_VALUE, 1);

				assertEquals(5.1, 1.0 /0);
				
			}
			
		}
		
	}
	
	
	@Nested
	@DisplayName("Método: divide")
	class Divide {
		
		@Nested
		@DisplayName("Casos válidos")
		class OK {
	
			@Test
			@DisplayName("División de dos números enteros")
			void testDivide() {		
				assertEquals(0.5, calc.divide(1.0 , 2));
			}
			
			@Test
			@DisplayName("División por cero")
			void testDivide2() {
				var ex = assertThrows(ArithmeticException.class, () -> calc.divide(1,0));
				assertEquals("/ by zero", ex.getMessage());
			}
			
			@Test
			@DisplayName("División por cero: try")
			void testDivide3() {
				try {
					calc.divide(1,0);
					fail("No se ha lanzado excepción");
				} catch (ArithmeticException e) {
					assertEquals("/ by zero", e.getMessage());
				}	
			}
			
			
		}
		
		
	}
	
	
	@Nested
	@DisplayName("Suplanta")
	class Suplantaciones {
		@Test
		void suplanta() {
			var calc = mock(Calculadora.class);
			when(calc.suma(anyInt(), anyInt())).thenReturn(3).thenReturn(5);

			var actual = calc.suma(2, 2);
			assertEquals(3, actual);
			assertEquals(5, calc.suma(2, 2));
			assertEquals(5, calc.suma(7,3));
		}
		@Test
		void suplanta2() {
			var calc = mock(Calculadora.class);
			when(calc.suma(anyInt(), anyInt())).thenReturn(4);
			var obj = new Factura(calc);
			var actual = obj.calcularTotal(2, 2);
			assertEquals(4, actual);
			verify(calc).suma(2, 2);
		}
		@Test
		void Integracion() {
			var obj = new Factura(new Calculadora());
			var actual = obj.calcularTotal(2, 2);
			assertEquals(4, actual);
		}
	}
	
	@Nested
	@DisplayName("Suplanta interfaz calculadora")
	class SuplantaInterfaz {
		
		@Test
		void testRestaWithValue() {
			var calc = mock(CalculadoraInt.class);
			when(calc.resta()).thenReturn(10);
			
			int resultado = calc.resta();

			assertEquals(10, resultado);
			
			verify(calc, times(1)).resta();
		}
		
		@Test
		void testMultiplicacionNoValue() {
			var calc = mock(CalculadoraInt.class);
			doNothing().when(calc).multiplicacion();
			
			calc.multiplicacion();
			
			verify(calc).multiplicacion();
		}
	}


} 