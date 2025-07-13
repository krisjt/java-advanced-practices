# lab09

## Polecenie

Podczas laboratoriów należy przećwiczyć różne sposoby przetwarzania dokumentów XML. Aby zadanie nie było zbyt skomplikowane, a jednocześnie umożliwiało zapoznanie się z różnymi technologiami, zdefiniowano je w następujący sposób:

Napisz program pozwalający:
1. wczytać dane XML korzystając z JAXB i wyrenderować je na ekranie (tutaj należy zdefiniować klasę mapującą się do struktury dokumentu XML oraz skorzystać z serializatora/deserializatora).
2. wczytać dane XML korzystając z JAXP i wyrenderować je na ekranie (tutaj należy uruchomić parsery SaX i DOM).
3. przetworzyć dane z dokumentu XML za pomocą wybranego arkusza transformacji XSLT i wyświetlić wynik tego przetwarzania. Przetwarzanie może polegać na wygenerowaniu i wyrenderowaniu htmla powstałego na skutek zaaplikowania wybranego arkusza XSLT do dokumentu XML (arkusz może "wycinać" niektóre elementy, "wyliczać" jakieś wartości, stylować tekst itp.). Program powinien umożliwić wybór używanego arkusza spośród kilku arkuszy składowanych w zadanym miejscu. Chodzi tu, między innymi, o to, by dało się zmienić "działanie programu" już po jego kompilacji (poprzez podmianę/wskazanie używanego arkusza).

Dane XML do przetwarzania mają pochodzić z publicznie dostępnych repozytoriów (z licencjami pozwalającymi na ich użytkowanie przynajmniej do celów edukacyjnych). Na przykład mogą do być dane opublikowane pod linkami:
- https://bip.poznan.pl/api-xml/bip/dane-o-srodowisku-i-ochronie/A/,
- https://catalog.data.gov/dataset/popular-baby-names,
- https://www.floatrates.com/feeds.html,

Dane mogą dotyczyć np. wyników piłkarskich rozgrywek ligowych (często dane te udostępniane są na komercyjnych zasadach, ale istnieją też darmowe przykłady do pobrania, jak: http://xmldocs.sports.gracenote.com/XML-Downloads-and-Samples_1048597.html)

Istnieje coś takiego jak "xml feeds"(strumienie danych XML), w szczególności "atom feeds" - ale to jest temat do osobnego omówienia (patrz https://www.ibm.com/docs/en/cics-ts/5.5?topic=support-overview-atom-feeds).