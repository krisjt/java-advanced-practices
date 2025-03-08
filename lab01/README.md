# Polecenie
Napisz aplikację, która pozwoli na archiwizowanie wskazanych plików/katalogów. Aplikacja ta powinna równiez umożliwiać generowanie funkcji skrótu MD5 dla stworzonych archiwów oraz weryfikowanie, czy dana funkcja skrótu zgadza siê z funkcją skrótu wyliczoną dla danego archiwum. 

Używając jlink należy przygotować minimalne środowisko uruchomieniowe.

Aplikację powinno dać się uruchomić z linii komend, korzystając tylko z wygenerowanego środowiska uruchomieniowego.

## Przygotowanie środowiska uruchomieniowego

### Kompilacja kodu z wykorzystaniem java compiler
`javac -p module/bin -d outdir/ gui/src/main/java/**/*.java `

- `-p` określa ścieżkę modułów wymaganych do kompilacji, każda z nich rozdzielona jest przez ":"
- `-d` destination określa katalog docelowy
- `gui/src/main/java/**/*.java` określa ścieżki do źródeł

> Do określenia zależności aplikacji warto wykorzystać komendę `jdeps`. 

### Tworzenie JRE (Java Runtime Environment)

`jlink --module-path lab01/outdir:/lab01/modul2/bin --add-modules library,com.example.gui --output outJRE --launcher start=com.example.gui/com.example.gui.HomePage`

- `--module-path` określa lokalizację modułów
- `--add-modules` określa, które moduły aplikacji zostaną uwzględnione w JRE
- `--output` określa gdzie zapisane ma zostać JRE
- `--launcher` tworzy skrypt startowy, nadaje nazwę i określa moduł, który ma zostać uruchomiony





