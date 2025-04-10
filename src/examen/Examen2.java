package examen;
import Entrega2.Clases.ListaOrdenada;
import Entrega2.Clases.Pila;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Entrega2.Clases.Cola;
import Entrega2.Clases.ColaPrioridad;

public class Examen2 {
	
	public static class Cliente{
		private String nombre;
		private Integer antiguedad;
		
		
		public Cliente(String nombre, Integer antiguedad) {
			this.nombre = nombre;
			this.antiguedad = antiguedad;
		}
		
		public static Cliente of(String nombre, Integer antiguedad) {
			return new Cliente(nombre, antiguedad);
		}
		
		public String getNombre() {
			return nombre;
		}
		public Integer getAntiguedad() {
			return antiguedad;
		}
		
		@Override
		public boolean equals(Object obj) {
		    if (this == obj) return true;
		    if (obj == null || getClass() != obj.getClass()) return false;

		    Cliente other = (Cliente) obj;
		    return this.nombre.equals(other.nombre);
		}
		
		@Override
		public String toString() {
			return String.format("Cliente[nombre=%s, antiguedad=%d]", nombre, antiguedad);
		}
	}
	
	public static class Compra{
		private Cliente cliente;
		private String descripcion;
		private Double importe;
		
		public Compra(Cliente cliente, String descripcion, Double importe) {
			this.cliente = cliente;
			this.descripcion = descripcion;
			this.importe = importe;
		}
		
		public static Compra of(Cliente cliente, String descripcion, Double importe) {
			return new Compra(cliente, descripcion, importe);
		}
		public Cliente getCliente() {
			return cliente;
		}
		
		public String getDescripcion() {
			return descripcion;
		}
		
		public Double getImporte() {
			return importe;
		}
		
		@Override
		public String toString() {
			return String.format("Compra [Nombre de cliente= %s, descripción= %s, importe= %.2f €].", cliente.getNombre(), descripcion, importe);
		}
		
	}
	
	public static class ClientesPorAntiguedad extends ListaOrdenada<Cliente> {
	    public ClientesPorAntiguedad() {
	        super((c1, c2) -> c2.getAntiguedad().compareTo(c1.getAntiguedad()));
	    }

	    public List<Cliente> topClientes(int n) {
	        List<Cliente> top = new ArrayList<>();
	        for (int i = 0; i < Math.min(n, size()); i++) {
	            top.add(elementos.get(i));
	        }
	        return top;
	    }
	}

	public static class HistorialCompras extends Pila<Compra> {
	    public double totalGastadoPor(Cliente cliente) {
	        return elementos.stream()
	            .filter(compra -> compra.getCliente().equals(cliente))
	            .mapToDouble(Compra::getImporte)
	            .sum();
	    }

	    public List<Compra> comprasMayoresA(double cantidad) {
	        return elementos.stream()
	            .filter(compra -> compra.getImporte() > cantidad)
	            .toList();
	    }
	}

	public static class ColaComprasPendientes extends Cola<Compra> {
	    public Compra buscarCompraPorDescripcion(String desc) {
	        for (Compra compra : elementos) {
	            if (compra.getDescripcion().contains(desc)) {
	                return compra;
	            }
	        }
	        return null;
	    }

	    public List<Compra> filtrarPorClienteYProducto(Cliente cliente, String producto) {
	        return elementos.stream()
	            .filter(compra -> compra.getCliente().equals(cliente) && compra.getDescripcion().contains(producto))
	            .toList();
	    }
	}
	
	public static class EstadisticasClientes {
	    public static Map<Cliente, List<Compra>> agruparComprasPorClienteFuncional(List<Compra> compras) {
	        return compras.stream()
	                .collect(Collectors.groupingBy(Compra::getCliente));
	    }

	    public static Map<Cliente, List<Compra>> agruparComprasPorClienteImperativa(List<Compra> compras) {
	        Map<Cliente, List<Compra>> mapa = new HashMap<>();
	        for (Compra compra : compras) {
	            Cliente cliente = compra.getCliente();
	            mapa.computeIfAbsent(cliente, k -> new ArrayList<>()).add(compra);
	        }
	        return mapa;
	    }
	}
	
	public static void main(String[] args) {
		Cliente ana = Cliente.of("Ana", 5);
		Cliente juan = Cliente.of("Juan", 2);
		Cliente luis = Cliente.of("Luis", 7);
		Compra c1 = Compra.of(ana, "Agenda personalizada", 25.5);
		Compra c2 = Compra.of(juan, "Camiseta estampada", 60.0);
		Compra c3 = Compra.of(ana, "Taza con foto", 15.0);
		Compra c4 = Compra.of(luis, "Poster gigante", 80.0);
		
		System.out.println("----- Test ClientesPorAntiguedad -----");
	    ClientesPorAntiguedad listaClientes = new ClientesPorAntiguedad();
	    listaClientes.addAll(List.of(ana, juan, luis));
	    System.out.println("Top 2 clientes por antigüedad: " + listaClientes.topClientes(2));

	    System.out.println("\n----- Test HistorialCompras -----");
	    HistorialCompras historial = new HistorialCompras();
	    historial.addAll(List.of(c1, c2, c3, c4));
	    System.out.println("Total gastado por Ana: " + historial.totalGastadoPor(ana));
	    System.out.println("Compras mayores a 50€: " + historial.comprasMayoresA(50));

	    System.out.println("\n----- Test ColaComprasPendientes -----");
	    ColaComprasPendientes pendientes = new ColaComprasPendientes();
	    pendientes.addAll(List.of(c1, c2, c3, c4));
	    System.out.println("Buscar compra por descripción 'foto': " + pendientes.buscarCompraPorDescripcion("foto"));
	    System.out.println("Filtrar por cliente Ana y producto 'Agenda': " + pendientes.filtrarPorClienteYProducto(ana, "Agenda"));

	    System.out.println("\n----- Test EstadisticasClientes -----");
	    List<Compra> todasLasCompras = List.of(c1, c2, c3, c4);
	    System.out.println("Agrupación funcional: " + EstadisticasClientes.agruparComprasPorClienteFuncional(todasLasCompras));
	    System.out.println("Agrupación imperativa: " + EstadisticasClientes.agruparComprasPorClienteImperativa(todasLasCompras));
	}

}
