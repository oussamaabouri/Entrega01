package Entrega2;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Clases {
	
	public static abstract class AgregadoLineal<E> {
		
		protected List<E> elementos = new ArrayList<>();
		
	    public int size() {
	    	return elementos.size();
	    }
	   
	    public boolean isEmpty() {
	    	return elementos.isEmpty();
	    }
	    
	    public List<E> elements(){
	    	return new ArrayList<>(elementos);
	    }
	    
	    public abstract void add(E e);
	    
	    public void addAll(List<E> list) {
	    	for(E e:list) {
	    		add(e);
	    	}
	    }
	    
	    public E remove() {
	    	return elementos.isEmpty() ? null : elementos.remove(0);
	    }
	    
	    public List<E> removeAll(){
	    	List<E> removed = new ArrayList<>();
	        while(!elementos.isEmpty()) {
	        	removed.add(remove());
	        }
	        return removed;
	    }
	}
	
	public static class ListaOrdenada<E> extends AgregadoLineal<E> {
		private Comparator<E> comparator;

	    public ListaOrdenada(Comparator<E> comparator) {
	        this.comparator = comparator;
	    }

	    public static <E> ListaOrdenada<E> of(Comparator<E> comparator) {
	        return new ListaOrdenada<>(comparator);
	    }

	    private int indexOrder(E e) {
	        int i = 0;
	        while (i < elementos.size() && comparator.compare(elementos.get(i), e) < 0) {
	            i++;
	        }
	        return i;
	    }

	    @Override
	    public void add(E e) {
	        elementos.add(indexOrder(e), e);
	    }
	    
	    @Override
	    public String toString() {
	    	return String.format("ListaOrdenada(%s)", elementos.stream().map(i -> i.toString()).collect(joining(",")));
	    }
	}
	
	
	public static class ListaOrdenadaSinRepeticion<E> extends ListaOrdenada<E> {
		public ListaOrdenadaSinRepeticion(Comparator<E> comparator) {
	        super(comparator);
	    }

	    public static <E> ListaOrdenadaSinRepeticion<E> of(Comparator<E> comparator) {
	        return new ListaOrdenadaSinRepeticion<>(comparator);
	    }

	    @Override
	    public void add(E e) {
	        if (!elements().contains(e)) {
	            super.add(e);
	        }
	    }
	    @Override
	    public String toString() {
	    	return String.format("ListaOrdenadaSinRepeticion(%s)", elementos.stream().map(i -> i.toString()).collect(joining(",")));
	    }
	}
	
	public static class Cola<E> extends AgregadoLineal<E> {
		public static <E> Cola<E> of() {
	        return new Cola<>();
	    }

	    @Override
	    public void add(E e) {
	        elementos.add(e);
	    }
	    
	    @Override
	    public String toString() {
	    	return String.format("Cola(%s)", elementos.stream().map(i -> i.toString()).collect(joining(",")));
	    }
	}
	
	public static class ColaPrioridad<E, P extends Comparable<P>> extends Cola<PriorityElement<E, P>> {
		public static <E, P extends Comparable<P>> ColaPrioridad<E, P> ofPriority() {
	        return new ColaPrioridad<>();
	    }

	    @Override
	    public void add(PriorityElement<E, P> element) {
	        elementos.add(element);
	        elementos.sort(Comparator.comparing(PriorityElement::priority));
	    }

	    public void add(E value, P priority) {
	        add(new PriorityElement<>(value, priority));
	    }

	    public List<E> valuesAsList() {
	        List<E> values = new ArrayList<>();
	        for (PriorityElement<E, P> element : elementos) {
	            values.add(element.value());
	        }
	        return values;
	    }

	    public void decreasePriority(E value, P newPriority) {
	        elementos.removeIf(e -> e.value().equals(value));
	        add(value, newPriority);
	    }

	    public E removeValue() {
	        return elementos.isEmpty() ? null : elementos.remove(0).value();
	    }

	    public void addAllValues(List<E> values, P priority) {
	        for (E value : values) {
	            add(value, priority);
	        }
	    }
	    @Override
	    public String toString() {
	    	return String.format("ColaPrioridad(%s)", elementos.stream().map(e -> String.format("(%s,%s)", e.value.toString(), e.priority.toString() )).collect(joining(",")));
	    }
	}
	
	public static record PriorityElement<E, P extends Comparable<P>>(E value, P priority) {
	}
	
	
	public static class Pila<E> extends AgregadoLineal<E> {
		@Override
	    public void add(E e) {
	        elementos.add(0, e);
	    }

	    public E top() {
	        return elementos.isEmpty() ? null : elementos.get(0);
	    }
	    @Override
	    public String toString() {
	    	return String.format("Pila(%s)", elementos.stream().map(i -> i.toString()).collect(joining(",")));
	    }
	}
	

	
	public static void main(String[] args) {
		System.out.println("------------------Test ListaOrdenada---------------------------");
		ListaOrdenada<Integer> lo = ListaOrdenada.<Integer>of(Comparator.naturalOrder());  		
		lo.add(5);
		lo.add(2);
		lo.add(8);
		lo.add(1);
		lo.add(3);
		System.out.println(lo);
		System.out.println(lo.size());
		System.out.println(lo.remove());
		System.out.println(lo);
		lo.addAll(List.of(4, 6, 7));
		System.out.println(lo);
		System.out.println(lo.removeAll());
		System.out.println(lo.isEmpty());
		ListaOrdenada<String> los = ListaOrdenada.<String>of(Comparator.naturalOrder());  
		los.addAll(List.of("banana", "apple", "date", "cherry"));
		System.out.println(los);
		System.out.println();
		
		System.out.println("------------------Test ListaOrdenadaSinRepeticion--------------");
		ListaOrdenadaSinRepeticion<Integer> losr = ListaOrdenadaSinRepeticion.<Integer>of(Comparator.naturalOrder());  		
		losr.add(5);
		losr.add(2);
		losr.add(8);
		losr.add(1);
		losr.add(3);
		losr.add(5);
		losr.add(2);	
		System.out.println(losr);
		System.out.println(losr.size());
		System.out.println(losr.remove());
		System.out.println(losr);
		losr.addAll(List.of(4, 6, 7, 4));
		System.out.println(losr);
		System.out.println();
		
		System.out.println("------------------Test Cola (FIFO)---------------------------");
		Cola<String> cola = Cola.of();
		cola.add("primero");
		cola.add("segundo");
		cola.add("tercero");
		System.out.println(cola);
		System.out.println(cola.size());
		System.out.println(cola.remove());
		System.out.println(cola);
		System.out.println(cola.remove());
		System.out.println(cola);
		System.out.println(cola.remove());
		System.out.println(cola);
		System.out.println(cola.isEmpty());
		System.out.println(cola.remove()==null? "No se puede eliminar de un agregado vacío.": cola.remove());

		System.out.println("\n------------------Test Pila (LIFO)---------------------------");
		Pila<Double> pila = new Pila<>();
		pila.add(1.1);
		pila.add(2.2);
		pila.add(3.3);
		System.out.println(pila);
		System.out.println(pila.size());
		System.out.println(pila.top());
		System.out.println(pila.remove());
		System.out.println(pila);
		System.out.println(pila.remove());
		System.out.println(pila);
		System.out.println(pila.remove());
		System.out.println(pila);
		System.out.println(pila.isEmpty());
		System.out.println(pila.top()==null? "La pila está vacía.": pila.top());

		System.out.println("\n------------------Test ColaPrioridad---------------------------");
		ColaPrioridad<String, Integer> cp = ColaPrioridad.ofPriority();
		cp.add("Crítico", 1);
		cp.add("Normal", 3);
		cp.add("Urgente", 2);
		cp.add("Bajo", 4);
		System.out.println(cp.valuesAsList());
		System.out.println(cp);
		System.out.println(cp.size());
		cp.decreasePriority("Normal", 1);
		System.out.println(cp.valuesAsList());
		System.out.println(cp.removeValue());
		System.out.println(cp.valuesAsList());
		System.out.println(cp.removeValue());
		System.out.println(cp.valuesAsList());
		System.out.println(cp.removeValue());
		System.out.println(cp.valuesAsList());
		System.out.println(cp.removeValue());
		System.out.println(cp.valuesAsList());
		System.out.println(cp.isEmpty());
		System.out.println(cp.removeValue()==null? "No se puede eliminar de una cola vacía.": cp.removeValue());
		cp.addAllValues(List.of("Tarea A", "Tarea B", "Tarea C"), 2);
		System.out.println(cp);
		cp.add("Tarea Urgente", 1);
		System.out.println(cp);


	}

}
