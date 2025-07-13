# Lab02

## Polecenie

Napisz aplikację, która umożliwi przeglądanie danych pomiarowych przechowywanych na dysku w plikach csv.
Zakładamy, że pliki danych składowane są w folderach o nazwach reprezentujących daty kampanii pomiarowych, zaś nazwy samych plików odpowiadają identyfikatorowi stanowiska pomiarowego oraz godzinie rozpoczęcia pomiarów.
Zawartość plików powinna odpowiadać następującemu schematowi:



> **godzina pomiaru; ciśnienie [hPa];  temperatura [stopnie C]; wilgotność [%]**
>
> 10:00; 960,34; -1,2; 60;
> 
> 10:02; 960,34; -1,2; 60;
> 
> ....



Aplikację należy zaprojektować z wykorzystaniem słabych referencji (ang. weak references). Zakładamy, że podczas przeglądania folderów z plikami danych pomiarowych zawartość aktualnie wybranego pliku będzie ładowana do odpowiedniej struktury oraz zostanie przetworzona (celem wyliczenia odpowiednich wartości).
Aplikacja powinna wskazywać, czy zawartość pliku została załadowana ponownie, czy też została pobrana z pamięci.

Architektura aplikacji powinna umożliwiać dołączanie różnych algorytmów przetwarzania danych, jak również algorytmów renderujących fragment zawartości przeglądanego pliku (zakładamy, że aplikację będzie można rozbudować w celu przeglądania danych zapisanych w plikach o innym schemacie zawartości czy też innym formacie danych).

## Opcje wirtualnej maszyny

` -Xmx256m -Xms256m -XX:GCTimeRatio=4 -XX:+UseG1GC -XX:-ShrinkHeapInSteps `

- [ ] ` Xmx256m ` - maksymalny rozmiar sterty 256MB, JVM nie przydzieli więcej pamięci,
- [ ] ` Xms256m ` - minimalny (początkowy) rozmiar sterty na 256MB,
- [ ] ` XX:GCTimeRatio=4 ` - określa stosunek czasu przeznaczonego na pracę aplikacji do czasu poświęcanego na GC, 4 oznacza, że GC powinien zajmować maksymalnie 20% czasu wykonywania programu,
- [ ] ` XX:+UseG1GC ` - włącza Garbage First Garbage Collector (G1 GC),
- [ ] ` XX:-ShrinkHeapInSteps ` - wyłącza stopniowe zmniejszanie sterty.