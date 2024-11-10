'''
Created on 31 Oct 2024

@author: oussa
'''
from tipos import *

def test_lista_ordenada():
    lista = Lista_ordenada(lambda x: x)
    lista.add(3)
    lista.add(1)
    lista.add(2)
    assert lista.elements == [1, 2, 3], "Error: Los elementos deberían estar ordenados como [1, 2, 3]"
    assert str(lista) == "ListaOrdenada(1, 2, 3)", "Error: La representación de la lista es incorrecta"
    print('La classe lista_ordenada funciona perfectamente')

def test_lista_ordenada_sin_repeticion():
    lista = Lista_ordenada_sin_repeticion(lambda x: x)
    lista.add(3)
    lista.add(1)
    lista.add(2)
    lista.add(1)
    assert lista.elements == [1, 2, 3], "Error: Los elementos deberían ser [1, 2, 3] sin repeticiones"
    assert str(lista) == "ListaOrdenadaSinRepeticion(1, 2, 3)", "Error: La representación de la lista es incorrecta"
    print('La classe lista_ordenada_sin_repeticion funciona perfectamente')

def test_cola():
    cola = Cola()
    cola.add(1)
    cola.add(2)
    cola.add(3)
    assert cola.remove() == 1, "Error: El primer elemento debería ser 1"
    assert cola.remove() == 2, "Error: El segundo elemento debería ser 2"
    assert cola.remove() == 3, "Error: El tercer elemento debería ser 3"
    assert cola.is_empty, "Error: La cola debería estar vacía"
    cola.add_all([1, 2, 3])
    assert str(cola) == "Cola(1, 2, 3)", "Error: La representación de la cola es incorrecta"
    print('La classe cola funciona perfectamente')

def test_cola_de_prioridad():
    cola = Cola_de_prioridad()
    cola.add('a', 1)
    cola.add('b', 3)
    cola.add('c', 2)
    assert cola.remove() == 'b', "Error: El elemento con mayor prioridad debería ser 'b'"
    assert cola.remove() == 'c', "Error: El siguiente elemento debería ser 'c'"
    assert cola.remove() == 'a', "Error: El último elemento debería ser 'a'"
    cola.add('a', 3)
    cola.add('b', 2)
    cola.decrease_priority('a', 1)
    assert cola.remove() == 'b', "Error: Después de disminuir la prioridad, 'b' debería ser el primero"
    assert cola.remove() == 'a', "Error: El último elemento debería ser 'a'"
    cola.add_all([('a', 1), ('b', 3), ('c', 2)])
    cola.decrease_priority('c', 4)
    assert str(cola) == "ColaPrioridad[('b', 3), ('c', 2), ('a', 1)]", "Error: La representación de la cola de prioridad es incorrecta"
    print('La classe cola_de_prioridad funciona perfectamente')
    
def test_pila():
    pila = Pila()
    pila.add(1)
    pila.add(2)
    pila.add(3)
    assert pila.remove() == 3, "Error: El último elemento añadido debería ser 3"
    assert pila.remove() == 2, "Error: El siguiente elemento debería ser 2"
    assert pila.remove() == 1, "Error: El primer elemento añadido debería ser 1"
    assert pila.is_empty, "Error: La pila debería estar vacía"
    pila.add_all([1, 2, 3])
    assert str(pila) == "Pila(3, 2, 1)", "Error: La representación de la pila es incorrecta"
    print('La classe cola funciona perfectamente')

if __name__ == '__main__':
    test_lista_ordenada()
    test_lista_ordenada_sin_repeticion()
    test_cola()
    test_cola_de_prioridad()
    test_pila()
    


