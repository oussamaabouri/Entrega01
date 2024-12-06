from __future__ import annotations
from dataclasses import dataclass
from typing import Dict
from datetime import date, datetime
from grafo import Grafo
from recorridos import bfs, dfs, reconstruir_camino
#from grafos.recorridos import * --> Adáptalo a tu proyecto
#from grafos.grafo import * --> Adáptalo a tu proyecto


def lineas_de_fichero(file:str,encoding:str='utf-8') -> list[str]:
    with open(file,"r",encoding=encoding) as f:
        return  [linea.strip() for linea in f]

@dataclass(frozen=True)
class Usuario:
    dni: str
    nombre: str
    apellidos: str
    fecha_nacimiento: date
    
    @staticmethod
    def of(dni: str, nombre: str, apellidos: str, fecha_nacimiento: date) -> Usuario:
        assert fecha_nacimiento < datetime.now().date(), 'La fecha de nacimiento debe ser anterior a la actual'
        assert len(dni) == 9, 'El dni debe contener 8 digitos y una letra'
        alfabeto: str = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ'
        digitos: str = '01234456789'
        for d in dni[:-1]:
            if d not in digitos:
                raise ValueError('El dni debe contener 8 digitos y una letra')
        if dni[-1] not in alfabeto:
            raise ValueError('El dni debe contener 8 digitos y una letra')
        
        return Usuario(dni, nombre, apellidos, fecha_nacimiento)
    
    @staticmethod
    def parse(linea:str) -> Usuario:
        campos:list[str] = linea.split(',')
        dni:str = campos[0]
        nombre:str = campos[1]
        apellidos:str = campos[2]
        fecha_nacimiento: date = datetime.strptime(campos[3], '%Y-%m-%d').date()
        return Usuario.of(dni, nombre, apellidos, fecha_nacimiento)
        
    
    def __str__(self) -> str:
        return f'{self.dni} - {self.nombre}'



@dataclass(frozen=True)
class Relacion:
    id: int
    interacciones: int
    dias_activa: int
    __n: int = 0 # Contador de relaciones. Servirá para asignar identificadores únicos a las relaciones.
    
    @staticmethod
    def of(interacciones: int, dias_activa: int) -> Relacion:
        Relacion.__n += 1
        return Relacion(Relacion.__n, interacciones, dias_activa)
    
    @staticmethod
    def parse(linea:str):
        campos:list[str] = linea.split(',')
        interacciones:int = int(campos[2])
        dias_activa:int = int(campos[3])
        return Relacion.of(interacciones, dias_activa)
        
        
    def __str__(self) -> str:
        return f'({self.id} - días activa: {self.dias_activa} - num interacciones {self.interacciones})'



class Red_social(Grafo[Usuario, Relacion]):
    """
    Representa una red social basada en el grafo genérico.
    """
    def __init__(self, es_dirigido: bool = False) -> None:
        super().__init__(es_dirigido)
        '''
        usuarios_dni: Diccionario que asocia un DNI de usuario con un objeto Usuario.
        Va a ser útil en la lectura del fichero de relaciones para poder acceder a los usuarios
        '''
        self.usuarios_dni: Dict[str, Usuario] = {}

    @staticmethod
    def of(es_dirigido: bool = False) -> Red_social:
        """
        Método de factoría para crear una nueva Red Social.
        
        :param es_dirigido: Indica si la red social es dirigida (True) o no dirigida (False).
        :return: Nueva red social.
        """
        return Red_social(es_dirigido)

    @staticmethod
    def parse(f1: str, f2: str, es_dirigido: bool = False) -> Red_social:
        """
        Método de factoría para crear una Red Social desde archivos de usuarios y relaciones.
        
        :param f1: Archivo de usuarios.
        :param f2: Archivo de relaciones.
        :param es_dirigido: Indica si la red social es dirigida (True) o no dirigida (False).
        :return: Nueva red social.
        """
        red:Red_social = Red_social.of(es_dirigido)
        usuarios: list[Usuario] = [Usuario.parse(l) for l in lineas_de_fichero(f1)]
        red.usuarios_dni = {x.dni:x for x in usuarios}
        relaciones: list[tuple(Usuario, Relacion)] = []
        for l in lineas_de_fichero(f2):
            campos:list[str] = l.split(',')
            usuario1:Usuario = red.usuarios_dni[campos[0]]
            usuario2:Usuario = red.usuarios_dni[campos[1]]
            relacion:Relacion = Relacion.parse(l)
            relaciones.append((usuario1, usuario2, relacion))
        

        for usuario in usuarios:
            red.add_vertex(usuario)
        
        for usuario1, usuario2, relacion in relaciones:
            red.add_edge(usuario1, usuario2, relacion)
        
        return red
            
        
        
        

    
if __name__ == '__main__':
    rrss = Red_social.parse(r'C:\Users\oussa\git\Entrega01\src\entrega3\usuarios.txt', r'C:\Users\oussa\git\Entrega01\src\entrega3\relaciones.txt', es_dirigido=False)
    print("El camino más corto desde 25143909I hasta 87345530M es:")
    camino = bfs(rrss, rrss.usuarios_dni['25143909I'], rrss.usuarios_dni['87345530M'])
    print(camino)
    
    g_camino = rrss.subgraph(camino)
    
    g_camino.draw("caminos", lambda_vertice=lambda v: f"{v.dni}", lambda_arista=lambda e: e.id)


        
