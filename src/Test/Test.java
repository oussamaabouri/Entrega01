package Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class Test {
		
	public static List<String> lineasDeFichero(String file){
		List<String> lineas= null;
		try {
			lineas = Files.readAllLines(Paths.get(file));
		} catch(IOException e) {
			System.out.println(e);
		}		
		return lineas;
	}
	
	public static Integer Factorial(Integer n) {
		Integer r = 1;
		for(int i=n; i>=1; i--) {
			r *= i;
		}
		return r;
	}
	
	public static Integer Producto(Integer n, Integer k) {
		assert n>k : String.format("n debe ser mayor que k");
		Integer p = 1;
		for(int i=0; i<=k; i++) {
			p *= n-i+1;		
		}
		return p;	
	}
		
	public static Integer Secuencia(Integer a, Integer r, Integer k) {
		Integer result= 1;
		for (int i=1; i<=k; i++) {
			result *= a * (int)Math.pow(r, i-1);
		}
		
		return result;
	}
	
	public static Integer Combinatorio(Integer n, Integer k) {
		assert n>=k : String.format("n debe ser mayor o igual que k");
		return Factorial(n)/(Factorial(n-k)*Factorial(k));
	}
	
	public static Double S(Integer n, Integer k) {
		assert n>=k : String.format("n debe ser mayor o igual que k");
		Double s = 0.;
		for(int i=0; i<=k-1; i++) {
			s += Math.pow(-1, i) * Combinatorio(k+1, i+1) * Math.pow(k-i, n);
		}
		return (1./Factorial(k))*s;
	}
	
	public static Double Newton(Function<Double, Double> f, Function<Double, Double> df, Double a, Double e) {
		Double valor = a;
		while(! (Math.abs(f.apply(valor))<= e)) {
			valor -= f.apply(valor)/df.apply(valor);
		}		
		return valor;		
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
		HashSet palabras = new HashSet<>();
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
		System.out.println(Factorial(3));
		System.out.println(Producto(4, 2));
		System.out.println(Secuencia(3, 5, 2));
		System.out.println(Combinatorio(4, 2));
		System.out.println(S(4, 2));
		System.out.println(Newton(x -> 2*Math.pow(x, 2), x -> 4*x, 3., 0.001));
		System.out.println(Contador("C:\\Users\\oussa\\Java-workspace\\Test\\src\\Test\\lin_quijote.txt", " ", "quijote"));
		System.out.println(LineasQueContinenPalabra("C:\\Users\\oussa\\Java-workspace\\Test\\src\\Test\\lin_quijote.txt", "quijote"));
		System.out.println(PalabrasUnicas("C:\\Users\\oussa\\Java-workspace\\Test\\src\\Test\\archivo_palabras.txt"));
		System.out.println(Longitud("C:\\Users\\oussa\\Java-workspace\\Test\\src\\Test\\palabras_random.csv", ","));

	
	}
	
}