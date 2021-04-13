

// Implement your solution to the Advanced task here
// (Note: the class must be named CovidChart)

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CovidChart extends Application {
    private static String filename;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // data 
        CovidDataset dataset = new CovidDataset();
        // converting data into active cases
        dataset.readDailyCases(filename);
        dataset.writeActiveCases("activecases.csv");
        dataset.readDailyCases("activecases.csv");

        // window title 
        primaryStage.setTitle("COMP1721 Coursework-1");

        // x axis
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Day of the Year");
        // setting the domain
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(280);
        xAxis.setUpperBound(350);

        // y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Active Cases");
        // setting the range
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(850);

        // linechart
        LineChart lineChart = new LineChart(xAxis, yAxis);

        // chart title 
        lineChart.setTitle("Active Coronavirus Cases, University of Leeds");
        // removing the 'points'
        lineChart.setCreateSymbols(false);

        // creating series
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName(filename);

        // adding the data 
        for(int i = 0; i < dataset.size(); i++)
        { 
            dataSeries.getData().add(new XYChart.Data(dataset.getRecord(i).getDate().getDayOfYear(), dataset.getRecord(i).totalCases()));   
        }
        // adding the series to plot
        Scene scene = new Scene(lineChart, 400, 200);
        lineChart.getData().add(dataSeries);

        // adjusting the plot
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(700);

        primaryStage.show();
    }

    // main function
    public static void main(String[] args) {
        // checking args
        if (args.length == 0)
        {
            System.out.println("Usage: java CovidChart <filename>");
            System.exit(1);
        }
        // assigining file name
        filename = args[0];
        // starting plot
        Application.launch(args);
    }
}