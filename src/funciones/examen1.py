'''
Created on 24 Oct 2024

@author: oussa
'''
from collections import Counter
def fact(n:int) -> int:
    if n== 0:
        return 1
    temp:int = 1
    for i in range(2, n+1):
        temp*= i
    return temp

def combinatorio(n:int, k:int) -> int:
    return fact(n)/(fact(k)*fact(n-k))


def P2(n:int, k:int, i:int = 1) -> int:
    assert n>0 and k>0 and i>0 and i< k+1
    s:int = 1
    for j in range(i, k-1):
        s *= n-j+1
        
    return s


def C2(n:int, k:int) -> int:
    assert n>0 and k>0 and n>k
    return fact(n)//(fact(k+1)*fact(n-k-1))


def S2(n:int, k:int) -> float:
    assert n>0 and k>0 and n>=k
    t:float = fact(k)/(n*fact(k+2))
    s:float = 0
    for i in range(k+1):
        s += ((-1)**i) * combinatorio(k, i) * ((k-i)**(n+1))
    
    return t*s


def palabrasMasComunes(fichero:str, n:int = 5) -> list[tuple[str, int]]:
    assert n>1
    with open(fichero, 'r', encoding='utf-8') as f:
        palabras:list[str] = []
        for linea in f:
            for p in linea.split(' '):
                palabras.append(p.strip().lower())
        
        freq:list[tuple[str, int]] = list(Counter(palabras).most_common(n))
        return freq
        
        
def testP2():
    try:
        P2(-5, 3)
    except AssertionError:
        print("n debe ser positivo")
    
    try:
        P2(5, -3)
    except AssertionError:
        print("k debe ser positivo")
 
    try:
        P2(5, 3, 0)
    except AssertionError:
        print("i debe ser mayor que 0")   
    try:
        P2(5, 3, 4)
    except AssertionError:
        print("i debe ser menor que k+1")
    
    #Caso válido:
    print(P2(5, 3))

def testC2():
    try:
        C2(5, 5)
    except AssertionError:
        print("n debe ser mayor que k")
    
    try:
        C2(-5, 3)
    except AssertionError:
        print("n debe ser positivo")
    
    try:
        C2(5, -3)
    except AssertionError:
        print("k debe ser positivo")
    
    #Caso válido:
    print(C2(4, 1))
def testS2():
    try:
        S2(-5, 3)
    except AssertionError:
        print("n debe ser positivo")
    
    try:
        S2(5, -3)
    except AssertionError:
        print("k debe ser positivo")
    
    try:
        S2(3, 5)
    except AssertionError:
        print("n debe ser mayor o igual 1 k")
        
    #Caso válido:
    print(S2(4, 2))
    
def testpalabrasMasComunes():
    try:
        palabrasMasComunes('archivo_inexistente.txt')
    except FileNotFoundError:
        print("debes introducir un archivo que existe")
    
    try:
        palabrasMasComunes(r'C:\Users\oussa\git\Entrega01\resources\archivo_palabras.txt', 1)
    except AssertionError:
        print("n debe ser mayor que 1")     
    
    #Caso válido:
    print(palabrasMasComunes(r'C:\Users\oussa\git\Entrega01\resources\archivo_palabras.txt', 3))
     
if __name__ == '__main__':
    testP2()
    testC2()
    testS2()
    testpalabrasMasComunes()

    
    