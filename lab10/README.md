# lab10 

## Polecenie
Zaimplementuj aplikację pozwalającą na szyfrowanie/deszyfrowanie plików (taka aplikacja mogłaby, na przykład pełnić, rolę narzędzia służącego do szyfrowania/odszyfrowywania załączników do e-maili).

Na interfejsie graficznym aplikacji użytkownik powinien mieć możliwość wskazania plików wejściowych oraz wyjściowych, jak również algorytmu szyfrowania/deszyfrowania oraz wykorzystywanych kluczy: prywatnego (do szyfrowania) i publicznego (do deszyfrowania).

Cała logika związana z szyfrowaniem/deszyfrowaniem powinna być dostarczona w osobnej bibliotece, spakowanej do podpisanego cyfrowo i wyeksportowanego pliku jar (do przedyskutowania na początku zajęć jest, czy działać w niej ma menadżer bezpieczeństwa korzystający z dostarczonego pliku polityki).

Projekt opierać ma się na technologiach należących do Java Cryptography Architecture (JCA) i/lub Java Cryptography Extension (JCE). Proszę zwrócić uwagę na ograniczenia związane z rozmiarem szyfrowanych danych narzucane przez wybrane algorytmu (zależy nam, by zaszyfrować dało się pliki o dowolnym rozmiarze).

W trakcie realizacji laboratorium będzie trzeba skorzystać z repozytoriów kluczy i certyfikatów.  Ponadto proszę zapoznać się z zasadami korzystania z narzędzia jarsigner.

Proszę w gitowym repozytorium kodu w gałęzi sources/releases stworzyć osobne podkatalogi: na bibliotekę (biblioteka) oraz na aplikację (aplikacja).
