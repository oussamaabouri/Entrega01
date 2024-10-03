
#Ejercicio1

def producto(n:int, k:int):
    n, k = max(n, k), min(n, k)
    s:int = 1
    for i in range(k+1):
        s*= n- i + 1
        
    return s

print(producto(4, 2))


#Ejercicio2
def secuencia(a1:int, r:int, k:int):
    ls: list[int] = [a1]
    for i in range(2, k+1):
        ls.append(a1*r**(i-1))
    s:int = 1
    for n in ls:
        s*=n
    
    return s

print(secuencia(3, 5, 2))


#Ejercicio3
def combinatorio(n:int, k:int):
    n, k = max(n, k), min(n, k)
    def factorial(t):
        r:int = 1
        for i in range(t, 1, -1):
            r*=i
        return r
    
    s:int = factorial(n)/(factorial(k)*factorial(n-k))
    
    return s

print(combinatorio(4, 2))


#Ejercicio4
def numeroS(n, k):
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
    
print(numeroS(4, 2))


#Ejercicio5
def Newton(a, e, f, df):
    pass








