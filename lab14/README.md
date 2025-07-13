# lab14

## Polecenie

Napisz program, który będzie symulował działanie systemu biletowego (kolejkowego) stosowanego w obsłudze klientów.

Systemy biletowe można spotkać w służbie zdrowia, administracji publicznej itp. W systemach tych klienci wybierają na panelu biletomatu kategorię sprawy, a następnie otrzymują bilet, który uprawnia ich do obsługi. Na bilecie wypisany jest identyfikator: symbol odpowiadający kategorii sprawy oraz kolejny numer. Obsługa klientów odbywa się na numerowanych stanowiskach. O tym, kto w danej chwili może być obsłużony oraz jaka jest lista oczekujących można przeczytać na tablicach informacyjnych (wyświetlane na nich są numery stanowisk oraz listy oczekujących - z podaniem identyfikatora biletu).

Program powinien wyświetlać interfejsy: biletomatów (gdzie można wybrać kategorię sprawy i pobrać bilet), stanowisk (gdzie wyświetla się identyfikator aktualnie obsługiwanego biletu i gdzie można zatwierdzić obsługę sprawy, by dało się przejść do następnej sprawy w kolejce) oraz tablic informacyjnych (na których widać listy kolejkowe).

Lista kategorii spraw ma być parametrem programu.

Kategorie spraw powinny mieć przypisane priorytety wpływające na kolejność obsługi (czyli wpływ na kolejność pobrania kolejnego biletu do obsługi na stanowisku mają wpływ numery biletów oraz priorytety kategorii spraw).

Kategorie spraw obsługiwanych na danym stanowisku powinny być konfigurowalne.

Zanurz w programie agenta obsługującego ziarenko, które pozwali na zmianę parametrów programu (listy kategorii spraw oraz priorytetów, listę kategorii przypisanych do obsługi na danym stanowisku). Ziarenko to powinno również generować notyfikacje przy każdej zmianie parametrów inicjowanej przez użytkownika aplikacji (nie powinno tych notyfikacji wysyłać, jeśli zmiana parametrów odbywać się będzie przez nie same).

Podsumowując, ziarenko powinno posiadać/obsługiwać:
- właściwość (pozwalającą na ustawianie/odczytywanie kategorii spraw),
- metodę zmieniającą priorytety spraw, metodę zmieniającą przypisanie kategorii obsługiwanych spraw na danym stanowisku,
- notyfikację.

Komunikacja z agentem odbywać się ma za pomocą aplikacji klienckiej (jconsole lub jmc).