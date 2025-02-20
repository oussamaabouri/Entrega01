package Entrega1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Lecturas {
	
	public static List<String> lineasDeFichero(String file){
		List<String> lineas= null;
		try {
			lineas = Files.readAllLines(Paths.get(file));
		} catch(IOException e) {
			System.out.println(e);
		}		
		return lineas;
	}
	
	
	public static Integer Contador(String file, String sep, String cad){
		Integer c = 0;
		List<String> lineas = lineasDeFichero(file);
		for(String linea:lineas) {
			for(String p: linea.split(sep)) {
				if(p.strip().toLowerCase().equals(cad)) {
					c++;
				}
			}
		}
		return c;
	}
	
	public static List<String> LineasQueContinenPalabra(String file, String cad){
		List<String> lineas = lineasDeFichero(file);
		List<String> result = new ArrayList<>();
		for(String linea: lineas) {
			if(linea.toLowerCase().contains(cad)) {
				result.add(linea);
			}
		}
		return result;
		
	}
	
	public static List<String> PalabrasUnicas(String file){
		HashSet<String> palabras = new HashSet<>();
		for(String linea: lineasDeFichero(file)) {
			for(String p:linea.split(" ")) {
				if(! p.isEmpty()) {
					palabras.add(p.toLowerCase());
				}
			}
		}
		return new ArrayList<>(palabras);
	}
	
	public static Double Longitud(String file, String sep) {
		List<String> lineas = lineasDeFichero(file);
		if(lineas.size() ==0) {
			return null;
		}
		Integer s = 0;
		for(String linea:lineas) {
			String [] palabras = linea.split(sep);
			s += palabras.length;
		}

		return (double)s/ (double)lineas.size();
		
	}

	public static void main(String[] args) {
		System.out.println(Contador("C:\\Users\\oussa\\git\\Entrega01\\resources\\lin_quijote.txt", " ", "quijote"));
		System.out.println(LineasQueContinenPalabra("C:\\Users\\oussa\\git\\Entrega01\\resources\\lin_quijote.txt", "quijote"));
		System.out.println(PalabrasUnicas("C:\\Users\\oussa\\git\\Entrega01\\resources\\archivo_palabras.txt"));
		System.out.println(Longitud("C:\\Users\\oussa\\git\\Entrega01\\resources\\palabras_random.csv", ","));
		System.out.println(Longitud("C:\\Users\\oussa\\git\\Entrega01\\resources\\vacio.csv", ","));

	}

}
