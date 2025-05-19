package entrega6.preguntas;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.centro.*;

public class PreguntasCentro {
	Centro c = Centro.of();

	//a
	public Double PromedioEdadProfesoresFuncional(String dni) {
		Set<Grupo> matriculasAlumno = c.matriculas().todas().stream().filter(m -> m.dni().equals(dni)).map(m -> m.grupo()).collect(Collectors.toSet());
		Set<String> dniProfesoresAlumno = c.asignaciones().todas().stream().filter(a -> matriculasAlumno.contains(a.grupo())).map(a -> a.dni()).collect(Collectors.toSet());
		return c.profesores().todos().stream().filter(p -> dniProfesoresAlumno.contains(p.dni())).mapToDouble(p -> Period.between(p.fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears()).average().orElse(0);
	}
	
	public Double PromedioEdadProfesoresImperativo(String dni) {
		Set<Grupo> matriculasAlumno = new HashSet();
		for(Matricula matricula : c.matriculas().todas()) {
			if(matricula.dni().equals(dni)) {
				matriculasAlumno.add(matricula.grupo());
			}
		}
		Set<String> dniProfesoresAlumno = new HashSet();
		for(Asignacion asignacion : c.asignaciones().todas()) {
			if(matriculasAlumno.contains(asignacion.grupo())) {
				dniProfesoresAlumno.add(asignacion.dni());
			}
		}
		Double edades = 0.;
		
		for(Profesor profesor: c.profesores().todos()) {
			if(dniProfesoresAlumno.contains(profesor.dni())){
				edades += Period.between(profesor.fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears();
			}
		}
		return edades / dniProfesoresAlumno.size();
	}
	
	
	//b
	public Grupo grupoMayorDiversidadEdadFuncional() {
		Map<Grupo, List<Matricula>> grupoAmatriculas = c.matriculas().todas().stream().collect(Collectors.groupingBy(Matricula::grupo));
		return grupoAmatriculas.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> difEdadIFuncional(e.getValue()))).entrySet().stream()
				.max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue())).map(e -> e.getKey()).orElse(null);
		
		
		
	}
	public Double difEdadIFuncional(List<Matricula> ls) {
		return ls.stream().mapToDouble(m -> Period.between(c.alumnos().alumno(m.dni()).fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears()).max()
				.orElse(0) - ls.stream().mapToDouble(m -> Period.between(c.alumnos().alumno(m.dni()).fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears()).min().orElse(0);
	}
	
	
	public Grupo grupoMayorDiversidadEdadImperativo() {
		Grupo Key = null;
		Double Value = Double.MIN_VALUE;
		Map<Grupo, List<Matricula>> grupoAmatriculas = new HashMap<>();
		for(Matricula matricula: c.matriculas().todas()) {
			if(!grupoAmatriculas.keySet().contains(matricula.grupo())) {
				grupoAmatriculas.put(matricula.grupo(), new ArrayList<>());
			}
			
			grupoAmatriculas.get(matricula.grupo()).add(matricula);
		}
		Map<Grupo, Double> grupoAedad = new HashMap<>();
		for(Grupo g: grupoAmatriculas.keySet()) {
			grupoAedad.put(g, difEdadImperativo(grupoAmatriculas.get(g)));
		}
		
		for(Map.Entry<Grupo, Double> entry : grupoAedad.entrySet()) {
			if(entry.getValue() > Value) {
				Key = entry.getKey();
				Value = entry.getValue();
			}
		}
		return Key;
	}
	
	public Double difEdadImperativo(List<Matricula> ls) {
		Set<Double> edades = new HashSet();
		for(Matricula matricula:ls) {
			edades.add((double) Period.between(c.alumnos().alumno(matricula.dni()).fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears());
		}
		return Collections.max(edades) - Collections.min(edades);
	}
	
	
	//c
	public Alumno alumnoMasMatriculasFuncional() {
		return c.matriculas().todas().stream().collect(Collectors.groupingBy(Matricula::dni, Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(e -> c.alumnos().alumno(e.getKey())).orElse(null);
	}
	
	public Alumno alumnoMasMatriculasImperativo() {
		Map<String, Integer> dniMatricula = new HashMap();
		for(Matricula matricula:c.matriculas().todas()) {
			if(!dniMatricula.keySet().contains(matricula.dni())) {
				dniMatricula.put(matricula.dni(), 0);
			}
			
			dniMatricula.merge(matricula.dni(), 1, Integer::sum);
		}
		String key = null;
		Integer value = Integer.MIN_VALUE;
		for(Map.Entry<String, Integer> entry: dniMatricula.entrySet()) {
			if(entry.getValue()>value) {
				value = entry.getValue();
				key = entry.getKey();
			}
		}
		return c.alumnos().alumno(key);
	}
	
	//d
	public String rangosEdadPorAlumnoFuncional(String s) {
		List<String> rangos = new ArrayList<>(Arrays.asList(s.split(",")));
		return c.alumnos().todos().stream().map(a -> {
			Integer edad = Period.between(a.fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears();
			String nombre = a.nombreCompleto();
			
			String rango = rangos.stream().map(rangoStr -> rangoStr.split("-")).filter(extremos -> {
				
				Integer left = Integer.parseInt(extremos[0].strip());
				Integer right = Integer.parseInt(extremos[1].strip());
				
				return edad >= left && edad <= right;

			}).map(e -> e[0] + " - " + e[1]).findFirst().orElse(null);
			
			return nombre + " : " + rango;
		}).collect(Collectors.joining("\n"));
	}
	
	
	public String rangosEdadPorAlumnoImperativo(String s) {
		String[] rangos = s.split(",");
		List<String> results = new ArrayList<>();
		Map<Alumno, Integer> alumnoEdad = new HashMap<>();
		for(Alumno alumno:c.alumnos().todos()) {
			alumnoEdad.put(alumno, Period.between(alumno.fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears());
		}
		for(Map.Entry<Alumno, Integer> entry:alumnoEdad.entrySet()) {
			String rangoAlumno = null;
			Integer edad = entry.getValue();
			String nombre = entry.getKey().nombreCompleto();
			for(String rango:rangos) {
				String[] extremos = rango.split("-");
				Integer left = Integer.parseInt(extremos[0].strip());
				Integer right = Integer.parseInt(extremos[1].strip());
				
				if(edad >= left && edad<= right) {
					rangoAlumno = rango;
					results.add(nombre + " : " + rangoAlumno);
					break;
				}
			}
			if(rangoAlumno == null) {
				results.add(nombre + " : " + rangoAlumno);
			}else {
				rangoAlumno = null;
			}	
		}
		return String.join("\n", results);
	}
	

	//e
	
	public String nombreProfesorMasGruposFuncional(int min, int max) {
		if(max<min) {
			throw new IllegalArgumentException("El parametro min tiene que ser menor que max");
		}
		return c.asignaciones().todas().stream()
				.filter(a -> Period.between(c.profesores().profesor(a.dni()).fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears() >= min && 
						Period.between(c.profesores().profesor(a.dni()).fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears() <= max)
				.collect(Collectors.groupingBy(Asignacion::dni, Collectors.counting())).entrySet().stream()
				.collect(Collectors.toMap(e -> c.profesores().profesor(e.getKey()).nombreCompleto(), e -> e.getValue())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(e -> e.getKey()).orElse(null);
		
		
	}
	public String nombreProfesorMasGruposImperativo(int min, int max) {
		if(max<min) {
			throw new IllegalArgumentException("El parametro min tiene que ser menor que max");
		}
		Map<String, Integer> profesorNumGrupos = new HashMap<>();
		for(Asignacion asignacion: c.asignaciones().todas()) {
			Integer edad = Period.between(c.profesores().profesor(asignacion.dni()).fechaDeNacimiento().toLocalDate(), LocalDate.now()).getYears();
			if(edad <= max && edad >= min) {
				profesorNumGrupos.merge(c.profesores().profesor(asignacion.dni()).nombreCompleto(), 1, Integer::sum);
			}
		}
		String key = null;
		Integer value = Integer.MIN_VALUE;
		for(Map.Entry<String, Integer> entry:profesorNumGrupos.entrySet()) {
			if(entry.getValue()> value) {
				key = entry.getKey();
				value = entry.getValue();
			}
		}
		return key;

	}
		
	//f
	public List<String> nombresAlumnosMayorNotaFuncional(Integer n, Integer a){
		if(n == null || a == null || n<1 || n>10) {
			throw new IllegalArgumentException("Ambos n y a no deben ser nulos y n tiene que ser comprendido entre 1 y 10 ambos incluidos.");
		}
		return c.alumnos().todos().stream().sorted(Comparator.comparingDouble(Alumno::nota).reversed()).filter(al -> al.fechaDeNacimiento().getYear()>a).limit(n).map(al -> al.nombre()).toList();
	}
	
	public List<String> nombresAlumnosMayorNotaImperativo(Integer n, Integer a){
		if(n == null || a == null || n<1 || n>10) {
			throw new IllegalArgumentException("Ambos n y a no deben ser nulos y n tiene que ser comprendido entre 1 y 10 ambos incluidos.");
		}
		List<Alumno> alumnosDespuesAnio = new ArrayList<>();
		for(Alumno alumno:c.alumnos().todos()) {
			if(alumno.fechaDeNacimiento().getYear() > a) {
				alumnosDespuesAnio.add(alumno);
			}
		}
		alumnosDespuesAnio.sort(Comparator.comparingDouble(Alumno::nota).reversed());
		List<Alumno> alumnosMayorNota = alumnosDespuesAnio.subList(0, n);
		List<String> nombres = new ArrayList<>();
		for(Alumno alumno: alumnosMayorNota) {
			nombres.add(alumno.nombre());
		}
		return nombres;
	}
	

	public static void main(String[] args) {
		PreguntasCentro p = new PreguntasCentro();
		System.out.println(p.PromedioEdadProfesoresImperativo("54561899B"));
		System.out.println(p.PromedioEdadProfesoresFuncional("54561899B"));
		System.out.println(p.grupoMayorDiversidadEdadImperativo());
		System.out.println(p.grupoMayorDiversidadEdadFuncional());
		System.out.println(p.alumnoMasMatriculasFuncional());
		System.out.println(p.alumnoMasMatriculasImperativo());
		System.out.println(p.nombresAlumnosMayorNotaFuncional(5, 2001));
		System.out.println(p.nombresAlumnosMayorNotaImperativo(5, 2001));
		System.out.println(p.nombreProfesorMasGruposFuncional(21, 24));
		System.out.println(p.nombreProfesorMasGruposImperativo(21, 24));
		System.out.println(p.rangosEdadPorAlumnoImperativo("20 - 23, 24 - 26"));
		System.out.println(p.rangosEdadPorAlumnoFuncional("20 - 23, 24 - 26"));

		
	}

}
