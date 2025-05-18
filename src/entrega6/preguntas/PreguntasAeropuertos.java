package entrega6.preguntas;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.aeropuerto.*;

public class PreguntasAeropuertos {
	EspacioAereo e = EspacioAereo.of();
	public String ciudadAeropuertoMayorFacturacionFuncional(LocalDateTime a, LocalDateTime b) {
	    Map<String, Double> facturacionPorCiudad = e.ocupacionesVuelos().todas().stream()
	        .filter(ocp -> !ocp.fecha().isBefore(a) && !ocp.fecha().isAfter(b))
	        .collect(Collectors.groupingBy(ocp -> ocp.vuelo().ciudadOrigen(), Collectors.summingDouble(ocp -> ocp.vuelo().numPlazas() * ocp.vuelo().precio())));


	    return facturacionPorCiudad.entrySet().stream()
	        .max(Map.Entry.comparingByValue())
	        .map(Map.Entry::getKey)
	        .orElse(null);
	}
	
	public String ciudadAeropuertoMayorFacturacionImperativo(LocalDateTime a, LocalDateTime b) {
	    Map<String, Double> facturacionPorCiudad = new HashMap<>();
	    for(OcupacionVuelo ocp : e.ocupacionesVuelos().todas()) {
	    	if(!ocp.fecha().isBefore(a) && !ocp.fecha().isAfter(b)) {
	    		facturacionPorCiudad.merge(ocp.vuelo().ciudadOrigen(), ocp.vuelo().precio()*ocp.vuelo().numPlazas(), (ov, nv) -> (ov + nv));
	    	}
	    }
	    
	    String key = null;
	    Double value = Double.MIN_VALUE;
	    for(Map.Entry<String, Double> entry: facturacionPorCiudad.entrySet()) {
	    	if(entry.getValue()>value) {
	    		value = entry.getValue();
	    		key = entry.getKey();
	    	}
	    }
	    return key;	    
	}

	public static void main(String[] args) {
		PreguntasAeropuertos p = new PreguntasAeropuertos();
		System.out.println(p.ciudadAeropuertoMayorFacturacionFuncional(LocalDateTime.of(LocalDate.of(2020, 1, 2), LocalTime.of(12, 0)), LocalDateTime.of(LocalDate.of(2021, 1, 2), LocalTime.of(12, 0))));
		System.out.println(p.ciudadAeropuertoMayorFacturacionImperativo(LocalDateTime.of(LocalDate.of(2020, 1, 2), LocalTime.of(12, 0)), LocalDateTime.of(LocalDate.of(2021, 1, 2), LocalTime.of(12, 0))));

	}

}
