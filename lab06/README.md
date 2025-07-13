# lab06

## Polecenie 

Napisz aplikację służącą do zarządzania usługami oferowanymi klientom firmy dostarczającej telewizję internetową. Zadanie należy zrealizować wykorzystując relacyjną bazę danych. Zalecane jest użycie h2 (z zapisem do pliku) bądź sqlite (by podczas uruchamiania nie było zależności od zewnętrznej usługi).

Podczas realizacji zadania należy skorzystać z mapowania ORM (technologia JPA, do czego można użyć framework Hibernate) oraz zastosować wzorzec projektowy z użyciem serwisów i repozytoriów. Można w tym celu wykorzystać framework Spring Boot.

Zakładamy, że w bazie danych będą przechowywane następujące informacje (jest to model mocno uproszczony, można go nieco przeorganizować celem "ulepszenia"):
* Klient - imię, nazwisko, numer klienta
* Subkonta - dane uwierzytelnienia (login, hasło)
* Typ abonamentu - typ usługi z listy wyliczeniowej
* Należności - termin płatności, kwota do zapłaty
* Dokonane wpłaty - termin wpłaty, kwota wpłaty
* Cennik - typ usługi, cena

Jeden klient może wykupić wiele abonamentów, przy czym z każdym abonamentem może być związanych kilka subkont. Należności i wpłaty dotyczą danego abonamentu. Należności mają być naliczane w trybie miesięcznym. Cennik powinien obejmować wszystkie typy usług (z uwzględnieniem historii: usługi aktywne, usługi wygaszone).

Aby dało się przetestować działanie aplikacji należy zasymulować upływ czasu.

Program powinien:
- pozwalać na ręczne zarządzanie sprzedawanymi usługami (klientami i ich abonamentami) oraz cennikiem;
- automatycznie naliczać należności i wysyłać monity o kolejnych płatnościach (wystarczy, że będzie pisał do pliku z logami monitów);
- automatycznie eskalować monity w przypadku braku terminowej wpłaty (wystarczy, że będzie pisał do pliku z logami eskalowanych monitów, upływ czasu należy zasymulować);
- umożliwiać ręczne rejestrowanie wpłat oraz nanoszenie korekt;
- umożliwiać przeglądanie należności i wpłat.

Program może działać w trybie konsolowym, choć dużo lepiej wyglądałoby stworzenie interfejsu graficznego. Do logowania monitów proszę użyć jakąś standardową bibliotekę (np. log4j).
