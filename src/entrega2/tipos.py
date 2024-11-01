'''
Created on 31 Oct 2024

@author: oussa
'''
from __future__ import annotations
from typing import TypeVar, Generic
from abc import ABC, abstractmethod

E = TypeVar('E')

class Agregado_lineal(ABC, Generic[E]):
    
    def __init__(self, elements:list[E]) -> Agregado_lineal:
        self._elements = elements
    
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










    