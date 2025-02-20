package Entrega1;

import java.util.function.Function;

public class Funciones {
	
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

	public static void main(String[] args) {
		System.out.println(Factorial(3));
		System.out.println(Producto(4, 2));
		System.out.println(Secuencia(3, 5, 2));
		System.out.println(Combinatorio(4, 2));
		System.out.println(S(4, 2));
		System.out.println(Newton(x -> 2*Math.pow(x, 2), x -> 4*x, 3., 0.001));

	}

}
