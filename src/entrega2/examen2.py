'''
Created on 21 Nov 2024

@author: oussa
'''
from __future__ import annotations
from typing import TypeVar, Generic, Callable
from abc import ABC, abstractmethod

E = TypeVar('E')
R = TypeVar('R')
P = TypeVar('P')

class Agregado_lineal(ABC, Generic[E]):
    
    def __init__(self) -> Agregado_lineal:
        self._elements = []
    
    @property
    def size(self) -> int:
        return len(self._elements)
    
    @property
    def is_empty(self) -> bool:
        return self.size == 0
    
    @property
    def elements(self) -> list[E]:
        return self._elements
    
    @abstractmethod
    def add(self, e:E) -> None:
        pass
    
    
    def add_all(self, ls:list[E]) -> None:
        for e in ls:
            self.add(e)
    

   
    def remove(self) -> E:
        assert not self.is_empty, 'El agregado está vacío'
        r: E = self._elements.pop(0)
        return r
    
    def remove_all(self) -> list[E]:
        l: list[E] = self._elements.copy()
        while self.size > 0:
            self.remove()
            
        return l
    
    

    def contains(self, e:E) -> bool:
        return e in self.elements
    
    def find(self,func: Callable[[E], bool]) -> E | None:
        for e in self.elements:
            if func(e):
                return e
        return None
    
    def filter(self, func: Callable[[E], bool]) -> list[E]:
        ls:list[E] = []
        for e in self.elements:
            if func(e):
                ls.append(e)
        return ls


class ClaseConLimite(Agregado_lineal[E]):
    def __init__(self, capacidad:int) -> ClaseConLimite:
        super().__init__()
        self.capacidad = capacidad

        
    
    def add(self, e:E) -> None:
        if len(self.elements) < self.capacidad:
            self.elements.append(e)
        else:
            raise OverflowError('La cola está llena.')
        
    
    @staticmethod
    def of(capacidad) -> ClaseConLimite:
        return ClaseConLimite(capacidad)
    

    def is_full(self) -> bool:
        return len(self.elements) == self.capacidad
    

def pruebas_clase_con_limite():
    print("==== Iniciando pruebas para ClaseConLimite ====")

    # Caso 1: Crear una cola con límite y agregar elementos dentro de la capacidad
    try:
        c = ClaseConLimite.of(3)
        c.add(1)
        c.add(2)
        c.add(3)
        print("Caso 1: Añadir elementos dentro del límite - Pasó")
    except Exception as e:
        print(f"Caso 1: Falló - {e}")

    # Caso 2: Intentar agregar elementos cuando la capacidad está llena
    try:
        c.add(4)
        print("Caso 2: Falló - No lanzó excepción al superar la capacidad")
    except OverflowError:
        print("Caso 2: Lanzó OverflowError al intentar agregar más elementos - Pasó")
    except Exception as e:
        print(f"Caso 2: Falló - Lanzó excepción inesperada: {e}")

    # Caso 3: Verificar si está lleno
    if c.is_full():
        print("Caso 3: Verificar si está lleno - Pasó")
    else:
        print("Caso 3: Verificar si está lleno - Falló")

    # Caso 4: Filtrar elementos
    filtrados = c.filter(lambda x: x > 1)
    if filtrados == [2, 3]:
        print("Caso 4: Filtrar elementos mayores que 1 - Pasó")
    else:
        print(f"Caso 4: Filtrar elementos mayores que 1 - Falló, resultado: {filtrados}")

    # Caso 5: Eliminar elementos secuencialmente
    try:
        removidos = []
        while not c.is_empty:
            removidos.append(c.remove())
        if removidos == [1, 2, 3]:
            print("Caso 5: Eliminar elementos secuencialmente - Pasó")
        else:
            print(f"Caso 5: Eliminar elementos secuencialmente - Falló, resultado: {removidos}")
    except Exception as e:
        print(f"Caso 5: Falló - {e}")

    # Caso 6: Intentar eliminar de una cola vacía
    try:
        c.remove()
        print("Caso 6: Falló - No lanzó excepción al intentar eliminar de una cola vacía")
    except AssertionError:
        print("Caso 6: Lanzó AssertionError al intentar eliminar de una cola vacía - Pasó")
    except Exception as e:
        print(f"Caso 6: Falló - Lanzó excepción inesperada: {e}")

    # Caso 7: Probar el método contains
    c.add_all([5, 6, 7])
    if c.contains(6) and not c.contains(10):
        print("Caso 7: Probar el método contains - Pasó")
    else:
        print("Caso 7: Probar el método contains - Falló")

    # Caso 8: Probar el método find
    encontrado = c.find(lambda x: x == 6)
    if encontrado == 6:
        print("Caso 8: Probar el método find - Pasó")
    else:
        print(f"Caso 8: Probar el método find - Falló, resultado: {encontrado}")

    # Caso 9: Probar el método add_all dentro de la capacidad
    try:
        c = ClaseConLimite.of(5)
        c.add_all([1, 2, 3, 4, 5])
        if c.elements == [1, 2, 3, 4, 5]:
            print("Caso 9: Probar el método add_all dentro de la capacidad - Pasó")
        else:
            print(f"Caso 9: Falló, resultado: {c.elements}")
    except Exception as e:
        print(f"Caso 9: Falló - {e}")

    # Caso 10: Probar el método add_all fuera de la capacidad
    try:
        c.add_all([6, 7, 8])
        print("Caso 10: Falló - No lanzó excepción al exceder la capacidad con add_all")
    except OverflowError:
        print("Caso 10: Lanzó OverflowError al exceder la capacidad con add_all - Pasó")
    except Exception as e:
        print(f"Caso 10: Falló - Lanzó excepción inesperada: {e}")

    print("==== Fin de pruebas para ClaseConLimite ====")

pruebas_clase_con_limite()




