# lab04

## Polecenie
Napisz aplikację, która umożliwi zlecanie wykonywania zadań instancjom klas ładowanym własnym ładowaczem klas. Do realizacji tego ćwiczenia należy użyć Java Reflection API z jdk 17.

Tworzona aplikacja powinna udostępniać graficzny interfejs, na którym będzie można:
1. zdefiniować zadanie (zakładamy, że będzie można definiować "dowolne" zadania reprezentowane przez ciąg znaków),
2. załadować klasę wykonującą zadanie (zakładamy, że będzie można załadować więcej niż jedną taką klasę),
3. zlecić wykonanie wskazanego zadania wskazanej załadowanej klasie, monitorować przebieg wykonywania zadania, wyświetlić wynik zadania.
4. wyładować wybraną klasę z wcześniej załadowanych

Realizacja zadania powinna opierać się na wykorzystaniu API (klas i interfejsów) zdefiniowanych w archiwum Refleksja02.zip (http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/Refleksja02.zip - dostęp z sieci uczelnianej).

Należy dostarczyć przynajmniej 3 różne klasy implementujące interfejs Processor. Każda taka klasa po załadowaniu powinna oznajmić, poprzez wynik metody getInfo(), jakiego typu zadanie obsługuje.

Z uwagi na wymagania GUI wynik getInfo() nie powinien być zbyt długi (ma się zmieścić w przewidzianym polu). Musi jednak nieść jakąś informację o zadaniu i jego parametrach. Proponuję, by ten ciąg znaków zawierał jednowyrazową nazwę zadania, a po dwukropku szablon zapytania. Jest to tylko propozycja. Podczas realizacji zadania można zastosować inny sposób informowania użytkownika, na czym polega zadanie i jak je sparametryzować.

Przykładowe wyniki wywołania metody getInfo():
1. "sumowanie: #1 op #2" - definiując zadanie, użytkownik powinien podać ciąg znaków składający się z dwóch operandów oraz operatora, np.: "1 + 2". Oczekiwanym wynikiem będzie ciąg znaków: "3".
2. "uppercase: #1" - definiując zadanie, użytkownik powinien podać ciąg znaków do przetworzenia: "ala ma kota", oczekiwanym wynikiem będzie ciąg znaków: "ALA MA KOTA".
3. "pogoda: #1, #2" - definiując zadanie, użytkownik powinien podać ciąg znaków reprezentujący nazwę miasta i datę: "Wroclaw, 25.03.2025", a oczekiwanym wynikiem będzie ciąg znaków reprezentujący prognozowane warunki pogodowe: "12 stopni C, 1000 hPa".
4. "csvfilter: #1, #2" - definiując zadanie, użytkownik powinien podać ciąg znaków reprezentujący URL  przetwarzanego pliku csv oraz parametry filtra, np.: "file:\\C:\in.csv, 2:3:4. Oczekiwanym wynikiem będzie ciąg znaków reprezentujący URL przetworzonego pliku (z pozostawionymi kolumnami 2, 3 i 4): "file:\\C:\in.filtered.csv".

Każda z grup laboratoryjnych otrzymała wskazówki, na czym ma polegać przetwarzanie. Proszę nie implementować sposobu przetwarzania z przykładów 1 i 2 wymienionych powyżej.

Klasy ładowane powinny być skompilowane w innym projekcie niż sama użytkowa aplikacja (podczas budowania aplikacja nie powinna mieć dostępu do tych klas). Można założyć, że kod bajtowy tych klas będzie umieszczany w katalogu, do którego aplikacja będzie miała dostęp. Ścieżka do tego katalogu powinna być parametrem ustawianym w aplikacji w trakcie jej działania. Wartością domyślną dla ścieżki niech będzie katalog, w którym uruchomiono aplikację. Aplikacja powinna odczytać zawartość tego katalogu i załadować własnym ładowaczem odnalezione klasy. Zakładamy, że podczas działania aplikacji będzie można "dorzucić" nowe klasy do tego katalogu (należy więc pomyśleć o pewnego rodzaju "odświeżaniu").

Wybieranie klas (a tym samym algorytmów przetwarzania) powinno odbywać się poprzez listę wyświetlającą nazwy załadowanych klas. Nazwom tym niech towarzyszą opisy pozyskane metodą getInfo() z utworzonych instancji tych klas.
