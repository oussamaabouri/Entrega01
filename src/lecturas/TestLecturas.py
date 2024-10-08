'''
Created on 8 Oct 2024

@author: oussa
'''

from lecturas import *

#Test Ejercicio1
print(count(r'C:\Users\oussa\git\Entrega01\resources\lin_quijote.txt', ' ', 'quijote'))

#Test Ejercicio2
print(obtenerlineas(r'C:\Users\oussa\git\Entrega01\resources\lin_quijote.txt', 'quijote'))

#Test Ejercicio3
print(palabras(r'C:\Users\oussa\git\Entrega01\resources\archivo_palabras.txt'))

#Test Ejercicio4
print(longitud_promedio_lineas(r'C:\Users\oussa\git\Entrega01\resources\palabras_random.csv'))
print(longitud_promedio_lineas(r'C:\Users\oussa\git\Entrega01\resources\vacio.csv'))