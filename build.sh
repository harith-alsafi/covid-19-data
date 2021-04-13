#!/bin/bash
wget "https://gluonhq.com/download/javafx-15-0-1-sdk-linux/" -O test.zip
unzip test.zip
rm test.zip
javac CaseRecord.java CovidDataset.java
javac --module-path javafx-sdk-15.0.1/lib/ --add-modules javafx.controls,javafx.fxml CovidChart.java 
 
