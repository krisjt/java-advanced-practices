## Lab02

` -Xmx256m -Xms256m -XX:GCTimeRatio=4 -XX:+UseG1GC -XX:-ShrinkHeapInSteps `

- [ ] ` Xmx256m ` - maksymalny rozmiar sterty 256MB, JVM nie przydzieli więcej pamięci,
- [ ] ` Xms256m ` - minimalny (początkowy) rozmiar sterty na 256MB,
- [ ] ` XX:GCTimeRatio=4 ` - określa stosunek czasu przeznaczonego na pracę aplikacji do czasu poświęcanego na GC, 4 oznacza, że GC powinien zajmować maksymalnie 20% czasu wykonywania programu,
- [ ] ` XX:+UseG1GC ` - włącza Garbage First Garbage Collector (G1 GC),
- [ ] ` XX:-ShrinkHeapInSteps ` - wyłącza stopniowe zmniejszanie sterty.