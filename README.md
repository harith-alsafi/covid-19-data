# Table of content 

# Compiling 
* This was tested for ``java-15`` on linux 
```
chmod 755 build.sh
./build.sh
```
* Runing without a test file 
```
java --module-path javafx-sdk-15.0.1/lib/ --add-modules javafx.controls CovidChart
```
* Running with a test file 
java --module-path javafx-sdk-15.0.1/lib/ --add-modules javafx.controls CovidChart 2020-daily.csv

