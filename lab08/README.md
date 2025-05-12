`javac -h . Sorter.java   `

`g++ -c -std=c++11 -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin pl_edu_pwr_knowak_calculations_Sorter.cpp -o Sorter.o`

`g++ -dynamiclib -o libnative.dylib Sorter.o -lc`

`java -cp . -Djava.library.path=. Sorter.java`