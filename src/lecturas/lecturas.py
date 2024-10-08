from collections import Counter
import csv
from pickle import NONE
#Ejercicio6
def count(fichero:str, sep:str, cad:str) -> int:
    with open(fichero, 'r', encoding='utf-8') as f:
        palabras:list[str] = []
        for linea in f:
            ls:list[str] = linea.split(sep)
            for palabra in ls:
                palabras.append(palabra.strip().lower())
                
        freq:dict[str, int] = Counter(palabras)
        
        return freq.get(cad, 0)
        
        

#Ejercicio7
def obtenerlineas(fichero:str, cad:str) -> list[str]:
    with open(fichero, 'r', encoding='utf-8') as f:
        lineas:list[str] = []
        for linea in f:
            if cad in linea.lower():
                lineas.append(linea.strip())
                
        
        return lineas
    
#Ejercicio8
def palabras(fichero:str) -> list[str]:
    with open(fichero, 'r', encoding='utf-8') as f:
        palabras:list[str] = []
        for linea in f:
            ls:list[str] = linea.split(' ')
            for palabra in ls:
                palabras.append(palabra.strip().lower())
                
        return list(set(palabras))
    

#Ejercicio9
def longitud_promedio_lineas(file_path: str) -> float:
    with open(file_path, 'r', encoding='utf-8') as f:
        ls = [linea for linea in csv.reader(f, delimiter=',')]
        total = 0
        n = 0
        if len(ls) == 0:
            return None
        
        for linea in ls:
            longitud = 0
            count = 0
            for palabra in linea:
                longitud += len(palabra)
                count += 1
            total += longitud/count
            n += 1
        
        return round(total/n, 1)
        


        
        
                
        
                
        