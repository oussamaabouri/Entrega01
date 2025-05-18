package entrega6.preguntas;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.biblioteca.*;

public class PreguntasBiblioteca {

	public static Map.Entry<Libro, Long> masVecesPrestadoFuncional() {
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		Map<String, Long> isbnPrestamos = biblioteca.prestamos().todos().stream().collect(Collectors.groupingBy(Prestamo::isbn, Collectors.counting()));
		return isbnPrestamos.entrySet().stream().collect(Collectors.toMap(e -> biblioteca.libros().libro(e.getKey()), e -> e.getValue())).entrySet()
				.stream().max(Map.Entry.comparingByValue()).orElse(null);
	}
	
	public static Map.Entry<Libro, Integer> masVecesPrestadoImperativo() {
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		Map<String, Integer> isbnPrestamos = new HashMap<>();
		for(Prestamo prestamo: biblioteca.prestamos().todos()) {
			isbnPrestamos.merge(prestamo.isbn(), 1, (ov, nv) -> ov+nv);
		}
		String key = null;
		Integer value = Integer.MIN_VALUE;
		
		for(Map.Entry<String, Integer> entry:isbnPrestamos.entrySet()) {
			if(entry.getValue()> value) {
				value = entry.getValue();
				key = entry.getKey();
			}
		}
		return Map.entry(biblioteca.libros().libro(key), value);
		
	}
	
	public Map <String, Set<String>> librosPorAutorFuncional(Libros libros, List<String> nombres){
		Stream<Libro> streamLibros = libros.todos().stream();
	    if (nombres != null) {
	        streamLibros = streamLibros.filter(libro -> nombres.contains(libro.autor()));
	    }
	    return streamLibros.collect(Collectors.groupingBy(Libro::autor, Collectors.mapping(Libro::titulo, Collectors.toSet())));	
	}
	
	public Map <String, Set<String>> librosPorAutorImperativo(Libros libros, List<String> nombres){
		Map<String, Set<String>> resultado = new HashMap<>();

	    for (Libro libro : libros.todos()) {
	        String autor = libro.autor();

	        if (nombres != null && !nombres.contains(autor)) {
	            continue;
	        }

	        resultado.computeIfAbsent(autor, k -> new HashSet<>()).add(libro.titulo());
	    }

	    return resultado;	
	}
	
	public static void main(String[] args) {
		Biblioteca biblioteca = Biblioteca.of("Reina Mercedes", "41012", "lsi@us.es");
		PreguntasBiblioteca p = new PreguntasBiblioteca();
		System.out.println(p.masVecesPrestadoFuncional());
		System.out.println(p.masVecesPrestadoImperativo());
		System.out.println(p.librosPorAutorImperativo(biblioteca.libros(),null));
		System.out.println(p.librosPorAutorFuncional(biblioteca.libros(),null));
	
	}

}
