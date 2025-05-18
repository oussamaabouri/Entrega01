package entrega6.preguntas;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import us.lsi.bancos.Banco;
import us.lsi.bancos.Personas;
import us.lsi.bancos.Prestamo;
import us.lsi.ejemplos_b1_tipos.Persona;

public class PreguntasBancos {

	public Map<String, Double> valorTotalPrestamosFuncional(int e, Double a, Double b, LocalDate f) {
	    if (e <= 18) {
	        throw new IllegalArgumentException("La edad debe ser mayor que 18");
	    }
	    if (a <= 0 || b <= 0) {
	        throw new IllegalArgumentException("Los valores a y b deben ser positivos");
	    }
	    if (a >= b) {
	        throw new IllegalArgumentException("El valor a debe ser menor que b");
	    }

	    Personas personas = Personas.of();

	    return this.prestamos().todos().stream()
	        .filter(p -> {
	            return personas.personaDni(p.dniCliente())
	                .map(persona -> persona.edad() < e)
	                .orElse(false);
	        })
	        .filter(p -> p.cantidad() >= a && p.cantidad() <= b)
	        .filter(p -> p.fechaComienzo().isAfter(f))
	        .collect(Collectors.groupingBy(
	            Prestamo::dniCliente,
	            Collectors.summingDouble(Prestamo::cantidad)
	        ));
	}
	public Map<String, Double> valorTotalPrestamosImperativo(int e, Double a, Double b, LocalDate f) {
	    if (e <= 18) {
	        throw new IllegalArgumentException("La edad debe ser mayor que 18");
	    }
	    if (a <= 0 || b <= 0) {
	        throw new IllegalArgumentException("Los valores a y b deben ser positivos");
	    }
	    if (a >= b) {
	        throw new IllegalArgumentException("El valor a debe ser menor que b");
	    }

	    Personas personas = Personas.of();
	    Map<String, Double> resultado = new HashMap<>();

	    for (Prestamo p : prestamos().todos()) {
	        Optional<Persona> personaOpt = personas.personaDni(p.dniCliente());

	        if (personaOpt.isPresent()) {
	            Persona persona = personaOpt.get();
	            int edadCliente = persona.edad();

	            if (edadCliente < e && 
	                p.cantidad() >= a && p.cantidad() <= b && 
	                p.fechaComienzo().isAfter(f)) {

	                String dni = p.dniCliente();
	                resultado.put(dni, resultado.getOrDefault(dni, 0.0) + p.cantidad());
	            }
	        }
	    }
	    return resultado;
	}
	
	
	public static void main(String[] args) {
		Banco banco = Banco.of();
		System.out.println(banco.valorTotalPrestamosFuncional(26, 10., 10000., LocalDate.of(2010, 5, 13)));
		System.out.println(banco.valorTotalPrestamosImperativo(26, 10., 10000., LocalDate.of(2010, 5, 13)));
	}

}
