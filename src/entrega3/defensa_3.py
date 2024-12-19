'''
Created on 19 Dec 2024

@author: oussa
'''

from __future__ import annotations
from dataclasses import dataclass
from grafo import Grafo
from recorridos import bfs


def lineas_de_fichero(file:str,encoding:str='utf-8') -> list[str]:
    with open(file,"r",encoding=encoding) as f:
        return  [linea.strip() for linea in f]

@dataclass(frozen=True)
class Gen:
    nombre: str
    tipo: str
    num_mutaciones: int
    loc_cromosoma: str


    @staticmethod
    def of(nombre:str, tipo:str, num_mutaciones:int, loc_cromosoma:str) -> Gen:
        assert num_mutaciones >= 0, 'El número de mutaciones debe ser mayor o igual que cero.'
        return Gen(nombre, tipo, num_mutaciones, loc_cromosoma)
    

    @staticmethod
    def parse(linea:str) -> Gen:
        campos:list[str] = linea.split(',')
        nombre:str = campos[0]
        tipo:str = campos[1]
        num_mutaciones:int = int(campos[2])
        loc_cromosoma:str = campos[3]
        
        return Gen.of(nombre, tipo, num_mutaciones, loc_cromosoma)
    

@dataclass(frozen=True)
class RelacionGenAGen:
    nombre_gen1: str
    nombre_gen2: str
    conexion: float
    
    @staticmethod
    def of(nombre_gen1:str, nombre_gen2:str, conexion:float) -> RelacionGenAGen:
        assert (conexion>=-1 and conexion <=1), 'La conexión debe ser un número real entre -1 y 1 ambos inclusive.'
        return RelacionGenAGen(nombre_gen1, nombre_gen2, conexion)
    

    @staticmethod
    def parse(linea:str) -> RelacionGenAGen:
        campos:list[str] = linea.split(',')
        nombre_gen1:str = campos[0]
        nombre_gen2:str = campos[1]
        conexion:float = float(campos[2])
        
        return RelacionGenAGen.of(nombre_gen1, nombre_gen2, conexion)
    
    
    @property
    def coexpresados(self) -> bool:
        return self.conexion > 0.75
    
    @property
    def antiexpresados(self) -> bool:
        return self.conexion < 0.75

class RedGenica(Grafo[Gen, RelacionGenAGen]):
    """
    Representa una red génica basada en Grafo
    """

    def __init__(self, es_dirigido: bool = False) -> None:
        super().__init__(es_dirigido)
        self.genes_por_nombre: dict[str, Gen] = {}

    @staticmethod
    def of(es_dirigido: bool = False) -> "RedGenica":
        """
        Método de factoría para crear una nueva Red Génica.

        :param es_dirigido: Indica si la red génica es dirigida (True) o no dirigida (False).
        :return: Nueva red génica.
        """
        return RedGenica(es_dirigido)

    @staticmethod
    def parse(f1: str, f2: str, es_dirigido: bool = False) -> "RedGenica":
        """
        Método de factoría para crear una Red Génica desde archivos de genes y relaciones.

        :param f1: Archivo de genes.
        :param f2: Archivo de relaciones entre genes.
        :param es_dirigido: Indica si la red génica es dirigida (True) o no dirigida (False).
        :return: Nueva red génica.
        """
        # Primero, crear la red génica que se va a devolver
        red_genica = RedGenica(es_dirigido)
        

        # Segundo, leer y agregar genes
        genes:list[Gen] = [Gen.parse(l) for l in lineas_de_fichero(f1)]
        red_genica.genes_por_nombre: dict[str, Gen] = {x.nombre:x for x in genes}
        for gen in genes:
            red_genica.add_vertex(gen)
        
  
        
        # Por último, leer y agregar relaciones entre genes
        relaciones: list[tuple(Gen, RelacionGenAGen)] = []
        for l in lineas_de_fichero(f2):
            campos:list[str] = l.split(',')
            gen1:Gen = red_genica.genes_por_nombre[campos[0]]
            gen2:Gen = red_genica.genes_por_nombre[campos[1]]
            relacion:RelacionGenAGen = RelacionGenAGen.parse(l)
            relaciones.append((gen1, gen2, relacion))
            
        for gen1, gen2, relacion in relaciones:
            red_genica.add_edge(gen1, gen2, relacion)
        
        
        
        return red_genica

    


if __name__ == '__main__':
    rrgg = RedGenica.parse(r'C:\Users\oussa\git\Entrega01\src\entrega3\genes.txt', r'C:\Users\oussa\git\Entrega01\src\entrega3\red_genes.txt', es_dirigido=False)
    print("El camino más corto desde KRAS hasta PIK3CA es:")
    camino = bfs(rrgg, rrgg.genes_por_nombre['KRAS'], rrgg.genes_por_nombre['PIK3CA'])
    print(camino)
    
    g_camino = rrgg.subgraph(camino)
    
    g_camino.draw("caminos", lambda_vertice=lambda v: f"{v.nombre}", lambda_arista=lambda e: e.conexion)
    
    
    
    
    
    
    
    
    