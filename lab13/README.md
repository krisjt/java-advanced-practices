# Tworzenie i uruchamianie jara

## Tworzenie jara

W celu utworzenia jara wykorzystany został plugin maven shade. Umożliwia on tworzenie, tzw. FAT jarów, zawierających klasy wraz z ich zależnościami. 

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.6.0</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <createDependencyReducedPom>false</createDependencyReducedPom>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.example.test2.Sample1</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
```

Następnie pliki wykonywalny tworzony był komendą:
```shell
mvn clean compile package
```


## Włączanie jara
W celu uruchomienia programu konieczne było pobranie JavaFX SDK ze strony [ GluaonHQ ]("https://gluonhq.com/products/javafx/"). Następnie w trakcie uruchamiania jara podawana jest ścieżka do pobranego SDK.


```shell
java --module-path ~/javafx-sdk-17.0.15/lib --add-modules javafx.controls,javafx.fxml -Djavafx.allowjs=true -jar lab13-1.0-SNAPSHOT.jar
```