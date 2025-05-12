# Analiza Wydajności Metod Sortowania

## Wstęp

Analizie poddano trzy metody `sort01`,`sort02` oraz `sort04`.
Celem było porównanie czasów wykonania metod oraz ocena wpływu JIT na ich działanie. 
Metoda, `sort03`, inicjuje interfejs graficzny oraz oczekuje wprowadzenia danych przez użytkownika, dlatego nie jest porównywana pod kątem wydajności sortowania.

## Opis Implementacji Metod

Wszystkie analizowane metody mają podobny schemat działania:

1.  Odbiór danych z Javy. 
2.  Konwersja do typu natywnego.
3.  Określenie porządku sortowania.
4.  Sortowanie.
5.  Konwersja z powrotem do typu Javy.
6.  Zwalnianie zasobów.
7.  Zwracanie wyniku.

**Różnice między metodami:**

* **`sort01`**: Otrzymuje tablicę do posortowania oraz obiekt określający porządek sortowania (`order`) jako argumenty. Zwraca posortowaną tablicę.
* **`sort02`**: Otrzymuje tablicę do posortowania jako argument. Porządek sortowania pobiera z pola `order` obiektu, na którym metoda jest wywoływana. Zwraca posortowaną tablicę.
* **`sort04`**: Nie przyjmuje argumentów. Pobiera tablicę wejściową z pola `a` oraz porządek sortowania z pola `order`. Po posortowaniu wynik zapisuje w polu `b`. Nie zwraca żadnych wartości.

## Wyniki Testów

Zmierzono średni czas wykonania dla każdej metody, zarówno dla sortowania **rosnącego** (`order = true`), 
jak i **malejącego** (`order = false`).

**Średnie wyniki (100 powtórzeń, 1 000 000 elementów):**

| Metoda | Porządek (`order`) | Średni Czas [ms] |
| :----- | :----------------- |:-----------------|
| sort01 | true               | 664,3084         |
| sort01 | false              | 664,9603         |
| sort02 | true               | 661,9049         |
| sort02 | false              | 662,0958         |
| sort04 | true               | 661,7249         |
| sort04 | false              | 663,6167         |

![sort01.png](..%2F..%2F..%2F..%2F..%2Fsort01.png)
*Wykres 1: Porównanie czasu działania metody sort01 dla porządków **rosnącego** (`order = true`) oraz  **malejącego** (`order = false`)*


![sort02.png](..%2F..%2F..%2F..%2F..%2Fsort02.png)
*Wykres 2: Porównanie czasu działania metody sort02 dla porządków **rosnącego** (`order = true`) oraz  **malejącego** (`order = false`)*


![sort04.png](..%2F..%2F..%2F..%2F..%2Fsort04.png)
*Wykres 3: Porównanie czasu działania metody sort04 dla porządków **rosnącego** (`order = true`) oraz  **malejącego** (`order = false`)*


## Analiza Wyników
Wyniki pokazują, że wszystkie trzy metody mają bardzo zbliżony średni czas wykonania. Różnice między nimi są niewielkie.

* Metody `sort02` i `sort04` wykazują nieznacznie lepszą wydajność (szczególnie w trybie rosnącym) w porównaniu do `sort01`. Może to być związane z przekazywaniem parametrów (w `sort01`) w porównaniu do odczytywania pól obiektu (w `sort02` i `sort04`).
* Kierunek sortowania ma minimalny wpływ na czas wykonania we wszystkich przypadkach.

## Wpływ Kompilatora JIT (Just-In-Time)

JIT (Just-In-Time) - JIT jest mechanizmem, który optymalizuje często wykonywany kod bajtowy, kompilując go do natywnego kodu maszynowego w trakcie działania programu.

1.  **Główna operacja sortowania (`qsort`) jest wykonywana w kodzie natywnym**, który nie podlega bezpośredniej optymalizacji przez JIT.
2.  **Kod Javy wywołujący metody natywne oraz operacje JNI** (np. `GetObjectArrayElement`, `CallDoubleMethod`, `NewObjectArray`) mogą być optymalizowane przez JIT.

Analizując wykresy (przedstawiające czasy wykonania dla kolejnych iteracji), nie widać wyraźnego trendu spadkowego czasu wykonania w początkowych iteracjach. Sugeruje to, że:
* Jeśli optymalizacja JIT kodu wywołującego miała miejsce, nastąpiła ona prawdopodobnie bardzo wcześnie lub jej wpływ na całkowity, zdominowany przez natywne sortowanie czas, jest niewielki.

Na podstawie przedstawionych wyników, nie ma dowodów na to, że działanie JIT miało wpływ na wydajność operacji. 

## Wnioski Końcowe

1.  Wszystkie trzy implementacje sortowania (`sort01`, `sort02`, `sort04`) wykorzystujące natywną funkcję `qsort` oraz wykazują bardzo podobną wydajność.
2.  Metody `sort02` i `sort04`, które pobierają dane z pól obiektu, okazały się minimalnie szybsze od metody `sort01`, która otrzymuje te dane jako argumenty.
3.  Nie zaobserwowano wyraźnego wpływu JIT na czasy wykonania metod.
