'''
Created on 31 Oct 2024

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
        
        
        

class Lista_ordenada(Agregado_lineal[E], Generic[E, R]):
    def __init__(self, order:Callable[[E], R]) -> Lista_ordenada[E, R]:
        super().__init__()
        self.__order = order
    
    @staticmethod
    def of(order:Callable[[E], R]) -> Lista_ordenada[E, R]:
        return Lista_ordenada(order)
    
    def __index_order(self, e:E) -> int:
        if self.size == 0:
            return 0
        
        for i, elem in enumerate(self._elements):
            if self.__order(e) < self.__order(elem):
                return i
        
        return self.size
    
    def add(self, e:E) -> None:
        self._elements.insert(self.__index_order(e), e)
        

    def __str__(self) -> str:
        res: str = ', '.join(map(str, self.elements))
        return f'ListaOrdenada({res})'


class Lista_ordenada_sin_repeticion(Agregado_lineal[E], Generic[E, R]):
    def __init__(self, order:Callable[[E], R]) -> Lista_ordenada_sin_repeticion[E, R]:
        super().__init__()
        self.__order = order
    
    @staticmethod
    def of(order:Callable[[E], R]) -> Lista_ordenada_sin_repeticion[E, R]:
        return Lista_ordenada_sin_repeticion(order) 
    
    def __index_order(self, e:E) -> int:
        if self.size == 0:
            return 0
        
        for i, elem in enumerate(self._elements):
            if self.__order(e) < self.__order(elem):
                return i
        
        return self.size
    
    def add(self, e:E) -> None:
        if e not in self._elements:
            self._elements.insert(self.__index_order(e), e)
        

    def __str__(self) -> str:
        res: str = ', '.join(map(str, self.elements))
        return f'ListaOrdenadaSinRepeticion({res})'
    

class Cola(Agregado_lineal[E]):
    def __init__(self) -> Cola[E]:
        super().__init__()
    
    @staticmethod
    def of() -> Cola[E]:
        return Cola()
    
    def add(self, e: E) -> None:
        self._elements.append(e)
    
    def __str__(self) -> str:
        res: str = ', '.join(map(str, self.elements))
        return f'Cola({res})'
    


class Cola_de_prioridad(Generic[E, P]):
    def __init__(self) -> Cola_de_prioridad[tuple[E, P]]:
        self._elements = []
        self._priorities = []
        
    @property
    def size(self) -> int:
        return len(self._elements)    
    
    @property
    def is_empty(self) -> bool:
        return self.size == 0
    
    @property
    def elements(self) -> list[E]:
        return self._elements

    
    def __index_order(self, priority:P) -> int:
        if self.size == 0:
            return 0
        
        for i in range(self.size):
            if priority > self._priorities[i]:
                return i
            
        return self.size
    
    def add(self, e:E, priority:P) -> None:
        index = self.__index_order(priority)
        self._elements.insert(index, e)
        self._priorities.insert(index, priority)

        
    
    def add_all(self, ls:list[tuple[E, P]]) -> None:
        for e, p in ls:
            self.add(e, p)
    
    
    def remove(self) -> E:
        assert not self.is_empty, 'El agregado está vacío'
        r:E = self._elements[0]
        self._elements.pop(0)
        self._priorities.pop(0)
        return r
        
    def remove_all(self) -> list[E]:
        r:list[E] = self._elements.copy()
        while self.size > 0:
            self.remove()
        
        return r
        
    def decrease_priority(self, e:E, new_priority:P) -> None:
        if e in self._elements:
            index = self._elements.index(e)
            if new_priority < self._priorities[index]:
                self._elements.pop(index)
                self._priorities.pop(index)
                self.add(e, new_priority)
        
    
    def __str__(self):
        ls: list[tuple[E, P]] = [(self._elements[i], self._priorities[i]) for i in range(self.size)]
        res:str = ', '.join(map(str, ls))
        return f'ColaPrioridad[{res}]'
        

class Pila(Agregado_lineal[E]):
    def __init__(self) -> Pila[E]:
        super().__init__()
    
    @staticmethod
    def of() -> Pila[E]:
        return Pila()
    
    def add(self, e: E) -> None:
        self._elements.insert(0, e)
    
    def __str__(self) -> str:
        res: str = ', '.join(map(str, self.elements))
        return f'Pila({res})'
    



    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    