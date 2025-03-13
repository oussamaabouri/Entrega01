package examen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Examen1 {
	public static Integer Factorial(Integer n) {
		Integer r = 1;
		for(int i=n; i>=1; i--) {
			r *= i;
		}
		return r;
	}
	
	public static List<String> lineasDeFichero(String file){
		List<String> lineas= null;
		try {
			lineas = Files.readAllLines(Paths.get(file));
		} catch(IOException e) {
			System.out.println(e);
		}		
		return lineas;
	}
	
	public static Integer productoImpares(Integer n) {
		if(n<=0) {
			throw new IllegalArgumentException("n debe ser un entero positivo");
		}
		Integer sol = 1;
		Integer count = 0;
		Integer numero = 1;
		while (count < n) {
			sol = sol * numero;
			numero = numero + 2;
			count ++;
		}
		return sol;
	}
	
	public static Double sumaGeometricaAlternada(Double a1, Double r, Integer k) {
		if (a1 <= 0) {
	        throw new IllegalArgumentException("a1 debe ser positivo");
	    }
		if (r <= 0) {
	        throw new IllegalArgumentException("r debe ser mayor que 0");
	    }
		if (k <= 0) {
	        throw new IllegalArgumentException("k debe ser mayor que 0");
	    }
		
		Double sol = a1;
		for(int i=2; i<=k; i++) {
			sol += Math.pow(-1, i) * a1 * Math.pow(r, i-1);
		}		
		return sol;			
	}
	
	
	public static Integer combinatorioSinMultiplosDeTres(Integer n, Integer k) {
		if (k > n) {
	        throw new IllegalArgumentException("n debe ser mayor o igual que k");
	    }
		if (n <= 0 || k <= 0) {
	        throw new IllegalArgumentException("n y k deben ser positivos");
	    }
		
		Integer numerador = Factorial(n);
		Integer denumenador = (Factorial(n-k)*Factorial(k));
		while (numerador%3 == 0) {
			numerador /= 3;
		}
		while (denumenador%3 == 0) {
			denumenador /= 3;
		}
			
		return numerador/denumenador;
			
	}
	
	public static List<String> filtrarLineasConsecutivas(String file, List<String> palabrasClave){
		
		List<String> lineas = lineasDeFichero(file);
		List<String> sol = new ArrayList<>();
		
		if (lineas == null) {
			return sol;
		}
		
		for (String linea : lineas) {
            String[] palabras = linea.split(" ");

            for (int i = 0; i < palabras.length - 1; i++) {
                if (palabrasClave.contains(palabras[i]) && palabrasClave.contains(palabras[i + 1])) {
                    sol.add(linea);
                    break;
                }
            }
        }
        return sol;
			
	}
	
	public static void main(String[] args) {
		System.out.println(productoImpares(3));
		System.out.println(sumaGeometricaAlternada(2., 3., 3));
		System.out.println(combinatorioSinMultiplosDeTres(4, 1));
		System.out.println(filtrarLineasConsecutivas("C:\\Users\\oussa\\git\\Entrega01\\resources\\archivo_palabras.txt", List.of("proyecto", "salud")));
		
		
		System.out.println(productoImpares(0));
		
		System.out.println(sumaGeometricaAlternada(-1., 3., 3));
		System.out.println(sumaGeometricaAlternada(3., -1., 3));
		System.out.println(sumaGeometricaAlternada(3., 3., -1));
		
		System.out.println(combinatorioSinMultiplosDeTres(1, 4));
		System.out.println(combinatorioSinMultiplosDeTres(0, 0));
		
		System.out.println(filtrarLineasConsecutivas("Noexiste", List.of("proyecto", "salud")));
	}

}
