# lab03

## Polecenie
Napisz aplikację, która pozwoli skonsumować dane bibliograficzne pozyskiwane z serwisu oferującego publiczne rest API. Niech pierwszym wyborem będzie api serwisu Wolne Lektury, udostępnione pod adresem https://wolnelektury.pl/api/ (nie potrzeba podawać żadnych parametrów uwierzytelnienia ani klucza api). Istnieją też inne serwisy oferujące dostęp do danych bibliograficznych. Przykłady można znaleźć na stronie: https://publicapis.dev/category/books.
Do realizacji zadania można więc wybrać inne api, ale poza https://openlibrary.org/developers/api(bo to api wykorzystano na laboratoriach w 2024 roku). W szczególności pouczające byłoby wykorzystanie api, które wymaga podania klucza. Jednak często uzyskanie klucza wiąże się z koniecznością rejestracji w danym serwisie z wyborem jakiegoś planu biznesowego (tj. za opłatą).

Ciekawą listę serwisów udostępniających różne API można znaleźć pod adresem:
https://rapidapi.com/collection/list-of-free-apis (wymagają klucza API), czy też https://mixedanalytics.com/blog/list-actually-free-open-no-auth-needed-apis/ (te klucza API nie wymagają).

Bazując na wskazanym api należy zbudować aplikację z graficznym interfejsem użytkownika, pozwalającą na przeprowadzanie testów dla bibliofilów, z pytaniami dotyczącymi książek (np. tytuły, tematyka, wydania, autorzy), autorów (np. dorobek wydawniczy, współautorstwo) itp. Renderowanie zapytań i odpowiedzi powinno być tak zaimplementowane, by dało się zmienić ustawienia językowe (lokalizacji) na podstawie tzw. bundle (definiowane w plikach i klasach - obie te opcje należy przetestować). Wspierane mają być języki: polski i angielski.

Proszę zapoznać się z api i zaproponować kilka schematów zapytań i pól odpowiedzi. Niech zapytania będą parametryzowane wartościami pochodzącymi z list wyboru wypełnionych treścią pozyskaną z serwisu, a odpowiedzi niech będą uzupełniane wolnym tekstem lub wartościami z list wyboru (jeśli "charakter" pytania jest, odpowiednio, otwarty lub zamknięty).
Odpowiedzi podawane przez użytkownika powinny być weryfikowane przez aplikację (aplikacja, po wysłaniu zapytania przez api powinna sprawdzić, czy wynik tego zapytania jest zgodny z odpowiedzią udzieloną przez użytkownika).

### Przykłady szablonów zapytania i odpowiedzi:

>Przykład 1:
> 
>Pole zapytania: "Kto jest autorem książki ... ?" (w miejsce kropek aplikacja powinna wstawić jakiś tytuł pobrany z serwisu)
> 
>Pole odpowiedzi: "..."  (miejsce na wpisanie autora).
>
>Pole weryfikacji (dla poprawnej odpowiedzi): "Tak, masz rację. Autorem książki .... jest ..." (to ma wypełnić sama aplikacja, przy czym można się zastanowić nad tym, jak ma przebiegać weryfikacja, gdy np. autorów jest więcej niż jeden).

>Przykład 2:
>
>Na przykład dla szablonu zapytania:
>
>Pole zapytania: "Ilu współautorów ma książka ... ?" (w miejsce kropek aplikacja powinna wstawić jakiś tytuł pobrany z serwisu)
>
>Pole odpowiedzi: "..."  (miejsce na wpisanie liczby).
>
>Pole weryfikacji (dla poprawnej odpowiedzi): "Tak, masz rację. Książka ... została napisana przez ... autorów" (to ma wypełnić sama aplikacja, przy czym trzeba obsłużyć odmianę przez liczby).

>Przykład 3:
>
>Pole zapytania: "Które z wymienionych tytułów: a) ...., b) ...., c) ....., d) ..... to książki dotyczące tematyki: ....?" (w miejsce kropek przy wyliczeniu aplikacja powinna wstawić losowe tytuły książek pobrane z serwisu zaś tematyka powinna być atrybutem pasującym przynajmniej do jednego tytułu - co można sprawdzić w serwisie).
>
> Pole odpowiedzi: "..."  (miejsce na wpisanie literki/literek odpowiadających wybranym tytułom).
>
> Pole weryfikacji (dla poprawnej odpowiedzi): "Tak, masz rację. Książka (bądź książki) ... dotyczy (dotyczą) tematyki ....." (to ma wypełnić sama aplikacja, przy czym trzeba obsłużyć odmianę przez liczby).

Proszę pamiętać o obsłudze przynajmniej dwóch języków na interfejsie. Do tego proszę zastosować wariantowe pobieranie tekstów z bundli. Do tego przyda się klasa ChoiceFormat.

Przypominam, że podczas realizacji zadania można wykorzystać inne api niż sugerowane, jeśli tylko pozwoli ono zrealizować przedstawioną wyżej koncepcję (parametryzowane szablony zapytań, do wypełnienia pola odpowiedzi, linijka weryfikacji z odmianą przez liczby/osoby - wszystko przynajmniej z obsługą dwóch języków: polski i angielski).

