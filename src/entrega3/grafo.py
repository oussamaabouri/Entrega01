'''
Created on 21 nov 2024

@author: damat
'''
from __future__ import annotations

from typing import TypeVar, Generic, Dict, Set, Optional, Callable
import matplotlib.pyplot as plt
import networkx as nx


# Definición de tipos genéricos
V = TypeVar('V')  # Tipo para vértices
E = TypeVar('E')  # Tipo para aristas

class Grafo(Generic[V, E]):
    """
    Representaciónde un grafo utilizando un diccionario de adyacencia.
    """
    def __init__(self, es_dirigido: bool = True):
        self.es_dirigido: bool = es_dirigido
        self.adyacencias: Dict[V, Dict[V, E]] = {}  # Diccionario de adyacencia
    
    @staticmethod
    def of(es_dirigido: bool = True) -> Grafo[V, E]:
        """
        Método de factoría para crear un nuevo grafo.
        
        :param es_dirigido: Indica si el grafo es dirigido (True) o no dirigido (False).
        :return: Nuevo grafo.
        """
        return Grafo(es_dirigido)
    
    def add_vertex(self, vertice: V) -> None:
        """
        Añade un vértice al grafo si no existe.
        
        :param vertice: Vértice a añadir.
        """
        if vertice not in self.adyacencias.keys():
            self.adyacencias[vertice] = {}


    def add_edge(self, origen: V, destino: V, arista: E) -> None:
        """
        Añade una arista al grafo entre dos vértices.
        Si el grafo es no dirigido, añade la arista en ambos sentidos.
        
        :param origen: Vértice de origen.
        :param destino: Vértice de destino.
        :param arista: Arista a añadir.
        """
        
        if origen in self.adyacencias.keys() and destino in self.adyacencias.keys() and origen != destino and destino not in self.adyacencias[origen].keys():
            self.adyacencias[origen][destino] = arista
            if not self.es_dirigido:
                self.adyacencias[destino][origen] = arista
            

    def successors(self, vertice: V) -> Set[V]:
        """
        Devuelve los sucesores de un vértice.
        
        :param vertice: Vértice del que se buscan los sucesores.
        :return: Conjunto de sucesores.
        """
        return set(self.adyacencias[vertice].keys())
        


    def predecessors(self, vertice: V) -> Set[V]:
        """
        Devuelve los predecesores de un vértice.
        
        :param vertice: Vértice del que se buscan los predecesores.
        :return: Conjunto de predecesores.
        """
        s:set[V] = set()
        for k, vs in self.adyacencias.items():
            for v in vs.keys():
                if v == vertice:
                    s.add(k)
        
        return s

    def edge_weight(self, origen: V, destino: V) -> Optional[E]:
        """
        Devuelve el peso de la arista entre dos vértices.
        
        :param origen: Vértice de origen.
        :param destino: Vértice de destino.
        :return: Peso de la arista, o None si no existe.
        """
        return self.adyacencias[origen][destino]

    def vertices(self) -> Set[V]:
        """
        Devuelve el conjunto de vértices del grafo.
        
        :return: Conjunto de vértices.
        """
        return set(self.adyacencias.keys())
    
    def edge_exists(self, origen: V, destino: V) -> bool:
        """
        Verifica si existe una arista entre dos vértices.
        
        :param origen: Vértice de origen.
        :param destino: Vértice de destino.
        :return: True si existe la arista, False en caso contrario.
        """
        return destino in self.adyacencias[origen].keys()

    def subgraph(self, vertices: Set[V]) -> Grafo[V, E]:
        """
        Crea un subgraph basado en un conjunto de vértices.
        
        :param vertices: Conjunto de vértices del subgraph.
        :return: Nuevo grafo con los vértices y aristas correspondientes.
        """
        sub:Grafo = Grafo.of(self.es_dirigido)
        for origen in vertices:
            if origen in self.adyacencias.keys():
                sub.add_vertex(origen)
        
        for origen in sub.adyacencias.keys():
            destinos:dict[V, E] = self.adyacencias[origen]
            for destino, arista in destinos.items():
                sub.add_edge(origen, destino, arista)
                    
        return sub
                    
            

    def inverse_graph(self) -> Grafo[V, E]:
        """
        Devuelve el grafo inverso (solo válido para grafos dirigidos).
        
        :return: Grafo inverso.
        :raise ValueError: Si el grafo no es dirigido.
        """
        if self.es_dirigido:
            grafo_invertido: Grafo[V, E] = Grafo.of(es_dirigido=True)
            for v in self.vertices():
                grafo_invertido.add_vertex(v)
            for origen, destinos in self.adyacencias.items():
                for destino, arista in destinos.items():
                    grafo_invertido.add_edge(destino, origen, arista)
            
            return grafo_invertido
                
        else:
            raise ValueError('El grafo debe ser dirigido')

    def draw(self, titulo: str = "Grafo", 
            lambda_vertice: Callable[[V], str] = str, 
            lambda_arista: Callable[[E], str] = str) -> None:
        """
        Dibuja el grafo utilizando NetworkX y Matplotlib. las funciones lambda permiten personalizar la representación
        de los vértices y aristas.
        
        :param titulo: Título del gráfico
        :param lambda_vertice: Función lambda para representar los vértices
        :param lambda_arista: Función lambda para representar las aristas
        """
        # Crear un grafo de NetworkX
        G = nx.DiGraph() if self.es_dirigido else nx.Graph()
    
        # Añadir nodos y aristas
        for vertice in self.vertices():
            G.add_node(vertice, label=lambda_vertice(vertice))  # Usamos lambda_vertice para personalizar el nodo
        for origen in self.vertices():
            for destino, arista in self.adyacencias[origen].items():
                G.add_edge(origen, destino, label=lambda_arista(arista))  # Usamos lambda_arista para personalizar la arista
    
        # Dibujar el grafo
        pos = nx.spring_layout(G)  # Distribución de los nodos
        plt.figure(figsize=(8, 6))
        nx.draw(G, pos, with_labels=True, node_color="lightblue", font_weight="bold", node_size=500, 
                labels=nx.get_node_attributes(G, 'label'))  # Usamos las etiquetas personalizadas de los vértices
    
        # Dibujar las etiquetas de las aristas (con la representación personalizada)
        edge_labels = nx.get_edge_attributes(G, "label")
        nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels)
    
        plt.title(titulo)
        plt.show()

        
    def __str__(self) -> str:
        """
        Representación textual del grafo.
        
        Formato libre. Por ejemplo:
            vertice1 -> vertice2 (peso), vertice3 (peso)
            vertice2 -> vertice1 (peso)
            ...
        """
        lineas:str = []
        for origen, destino in self.adyacencias.items():
            destinos_str = ', '.join([f'{vertice} ({peso})' for vertice, peso in destino.items()])
            lineas.append(f'{origen} -> {destinos_str}')
        
        return '\n'.join(lineas)
        

if __name__ == '__main__':
    # Crear un grafo dirigido
    grafo = Grafo.of(es_dirigido=True)
    grafo.add_vertex("A")
    grafo.add_vertex("B")
    grafo.add_vertex("C")
    grafo.add_vertex("D")
    grafo.add_edge("B", "A", 5)
    grafo.add_edge("C", "A", 3)
    grafo.add_edge("C", "B", 4)
    grafo.add_edge("B", "D", 6)
    print(grafo)
    print('---------------')
    print(grafo.inverse_graph())
    grafo.draw(titulo="Mi Grafo Dirigido")
    grafo.inverse_graph().draw(titulo="Inverso del Grafo Dirigido")
 
    
    # Dibujar el grafo

