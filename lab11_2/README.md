# Raport z Zajęć

## Struktura projektu
Moduł library został podzielony na foldery odpowiadające różnym wersjom JDK:
- `jdk9` - wersja dla Java 9
- `jdk11` - wersja dla Java 11
- `jdk17` - wersja dla Java 17
- `main` - główny kod źródłowy
- `test` - testy

## Różnice Wprowadzone Między Wersjami JDK
   
### **JDK 9**: Zmiana Algorytmu Szyfrowania

W JDK 9 brak wsparcia dla algorytmu ChaCha20 wymusił jego zastąpienie algorytmem AES w implementacji.

### Porównanie **JDK 11** i **JDK 17**

Analiza zmian w metodzie *listAllItems()* z klasy KeyStoreProvider:

W **JDK 11** metoda iteruje po aliasach, sprawdza typ wpisu (PrivateKeyEntry lub SecretKeyEntry) za pomocą instanceof i wyświetla podstawowe informacje o kluczu, bez szczegółów algorytmu.

Wersja **JDK 17** wykorzystuje Pattern Matching dla instanceof. Dodatkowo wyświetla konkretny algorytm każdego klucza i używa stałej SEPARATOR dla lepszej czytelności.

## Generowanie JAR i Zarządzanie Zależnościami
   Moduł library został zbudowany Mavenem, tworząc library-1.0-SNAPSHOT.jar. Ten JAR dodano jako zależność do modułu application poprzez pom.xml z scope system. Aby uniknąć błędu duplikacji klas (gdy application miało jednocześnie moduł library i jego JAR w classpath), moduł library został usunięty z Project Structure IDE. Dzięki temu application polegało wyłącznie na wygenerowanym JAR-ze, co rozwiązało problem.


## Tworzenie instalatora:
`jpackage \
--input /Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab11/application \
--name JavaTZ \   
--main-jar application.jar \
--main-class pl.edu.pwr.knowak.LoginWindow \
--type dmg
`
