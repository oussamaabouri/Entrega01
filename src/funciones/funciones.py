from typing import Callable
#Función1

def producto(n:int, k:int) -> int:
    n, k = max(n, k), min(n, k)
    s:int = 1
    for i in range(k+1):
        s*= n- i + 1
        
    return s



#Función2
def secuencia(a1:int, r:int, k:int) -> int:
    ls: list[int] = [a1]
    for i in range(2, k+1):
        ls.append(a1*r**(i-1))
    s:int = 1
    for n in ls:
        s*=n
    
    return s



#Función3
def combinatorio(n:int, k:int) -> int:
    n, k = max(n, k), min(n, k)
    def factorial(t):
        r:int = 1
        for i in range(t, 1, -1):
            r*=i
        return r
    
    s:int = factorial(n)/(factorial(k)*factorial(n-k))
    
    return s



#Función4
def numeroS(n, k) -> float:
    n, k = max(n, k), min(n, k)
    def factorial(t):
        r:int = 1
        for i in range(t, 1, -1):
            r*=i
        return r
    
    s:int = 1/factorial(k)
    t:int = 0
    for i in range(k):
        t += (-1)**i * combinatorio(k+1, i+1) * (k-i)**n
        
    return s*t
    


#Función5
def Newton(a:float, e:float, f:Callable[[float], float], df:Callable[[float], float]) -> float:
    x0:float = a
    funcion:float = float(f(x0))
    while abs(funcion) > e:
        x0 -= f(x0)/df(x0)
        funcion = float(f(x0))
    
    return x0




    
    








