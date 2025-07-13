# lab12

## Polecenie
Celem laboratorium jest przetrenowanie możliwości dynamicznego rozszerzania funkcji programu Java przez ładowanie i wyładowywanie skryptów JavaScript (na podobieństwo ładowania klas własnym ładowaczem). Ponadto chodzi w nim o opanowanie technik przekazywania obiektów pomiędzy wirtualną maszyną Java a silnikiem JavaScript.

Na początek proszę przeczytać parę uwag:

Rozwój silnika JavaScript, odbywający się w ramach rozwoju JDK, został w pewnym momencie zatrzymany przez Oracle. Stwierdzono bowiem, że zadanie to realizowane jest w projekcie GraalVM, a szkoda poświęcać zasoby na powielanie prac. Efekt podjętej wtedy decyzji widać było już w JDK 11, gdzie odpowiedni moduł dostarczający rzeczony silnik otagowano:
>@Deprecated(since="11", forRemoval=true)
>
>Module jdk.scripting.nashorn
>
>W wersji JDK 17 tego modułu już nie ma. Przy okazji usunięto też niektóre pomocnicze narzędzia, jak uruchamiane z linii komend interpreter jjs. Co więcej, dla aplikacji JavaFX skryptowanie zostało domylnie wyłšczone. Aby je włšczyć, należy przekazać wirtualnej maszynie odpowiedniš opcję: -Djavafx.allowjs=true

Aby dało się "skryptować" w nowszych wersjach JDK, oprócz włączenia odpowiednich opcji, można:
1. umieścić we własnym projekcie zależność do rozwijanego niezależnie silnika nashorn (czyli do JDK dołożyć silnik nashorn)
2. umieścić we własnym projekcie zależność do rozwijanego niezależnie silnika graal.js (czyli do JDK dołożyć silnik graal.js - możliwe jest też przy okazji dołączenie kompilatora JIT z dystrybucji GraalVM)
3. stworzyć projekt wykorzystując GraalVM zamiast JDK

GraalVM Community Edition wersja 22.3.2 (obecnie najnowsza, patrz https://www.graalvm.org/downloads/) zbudowano na bazie OpenJDK 11.0.17 oraz OpenJDK 17.0.5.

Niezależna implementacja silnika nashorn - patrz https://github.com/openjdk/nashorn

Archiwum z czterema projektami: [ skryptowanie.zip ](http://tomasz.kubik.staff.iiar.pwr.wroc.pl/dydaktyka/Java/skryptowanie.zip)

Zadanie do wykonania polega na napisaniu programu w języku Java pozwalającego na wizualizację działania automatów komórkowych, przy czym logika działania tych automatów powinna być zaimplementowana za pomocą ładowanych dynamicznie skryptów JavaScript zapisanych w plikach o znanej lokalizacji. Nazwy plików ze skryptami powinny odpowiadać nazwom automatów - by było wiadomo, co robi ładowany skrypt. Załadowane skrypty powinno dać się wyładować.

Opis przykładowych automatów komórkowych opublikowano na wiki: https://pl.wikipedia.org/wiki/Automat_kom%C3%B3rkowy.

Materiały pomocnicze można znaleźć pod adresami:
- http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html
- https://www.n-k.de/riding-the-nashorn/

Proszę w gitowym repozytorium kodu w gałęzi sources umieścić wszystkie wykorzystywane artefakty (skrypty JavaScript oraz źródła kodu Java). Proszę też zamieścić instrukcję użycia programu wraz z udokumentowanym wynikiem jego działania (plik Readme.md z dołączonymi zrzutami z ekranu).
