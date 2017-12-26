/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.view;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.converter.DateTimeStringConverter;
import mg.MainApp;
import mg.model.Setup;
import mg.utils.DataFile;


/**
 * FXML Controller class
 *
 * @author xx
 */
public class GraphController {
    
   
    @FXML
    private LineChart<Long, Double> lineChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis xAxis;
    
    
    
    //xAxix.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
    private Object stage;
    
    private MainApp mainApp;
    private Setup setup;
    
    // storage to keep grapgh information before show on the screen
    //List<LineChart.Series<Long, Double>> fragments = new ArrayList<>(); 
    
    DataFile graphData = new DataFile();
   
    // max/min X and Y are calculated during loading data in to series on the line chart.
    long   maxX=0, minX=0;
    double maxY=0, minY=0;
    boolean minmaxSet=false;
    
    //xAxis.setLowerBound(fromString("2015.09.01 00:00"));
    
    /**
     * Scroll graph to the left
     * 
     */
    @FXML
    private void handleScrollLeft() {
               
    }  
    /**
     * Scroll graph to the Right
     * 
     */
    @FXML
    private void handleScrollRight() {
               
    }  
       
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        graphData.setMainApp(mainApp);
    }
    
    /**
     * Initializes the controller class.
     * @param setups
     */
    public void displayGraphs(){
        List<Setup> setups = mainApp.getSetupData();
        lineChart.getData().clear(); // remove all lines from graph chart
        System.out.println("GraphController displayGraph started.");

        for (Setup stp : setups){
            //s.getGraphDataFile();
            //stp.getGraphColor();
            //stp.getGraphChk();
            //stp.getGraphScale();
            //stp.getGraphShift();
            //ind=stp.getLegendNum();
            addGraphToChart(stp);
        }
        
        //System.out.println("Populating date" +" ind="+ ind);
        //populateData(ind,mainApp.seriesData);
        toggleLine();
        
    }
    
    public int addGraphToChart(Setup stp){
        int ind=stp.getLegendNum()-1;
        System.out.println("addGraphToChart ind="+ ind);
        if (!stp.getGraphDataFile().isEmpty()) {
                 //mainApp.seriesData.add(ind, new LineChart.Series<>());
                lineChart.getData().add(ind, new LineChart.Series<>());
                //mainApp.seriesData.get(ind).setName(stp.getGraphName());
                System.out.println("new series is created for ind="+ind);
                graphData.addReader(ind, stp.getGraphDataFile());
                System.out.println("Reader is created for file"+stp.getGraphDataFile());
                //addGraphData(mainApp.seriesData.get(ind), graphData.getGraphPart(ind),stp );
                addGraphData(lineChart.getData().get(ind), graphData.getGraphPart(ind),stp );
                System.out.println("data added to the graph. ind = " + ind);
                ind++;
            
            }
        return ind;
    }
    
    /*
    *  toggle visibility of lines on the chart
    */

    public void toggleLine() {
        System.out.println("Started toggleLine()");
        List<Setup> setups = mainApp.getSetupData();
        List<LineChart.Series<Long, Double>> series=lineChart.getData();
        
        
        for (Setup stp : setups){
            int ind = stp.getLegendNum()-1;
            if (stp.getGraphChk()== false) {   // Show line on if chk box is not marked 
                //System.out.println("To SHOW  ind="+ind);
                series.get(ind).getNode().setVisible(true); // Toggle visibility of line
                //Node lineSymbol = series.get(ind).getNode().lookup(".chart-line-symbol");
                //lineSymbol.setStyle("-fx-background-color: "+stp.getGraphColor()+", #000000; -fx-background-insets: 0, 2;\n" +
                //"    -fx-background-radius: 3px;\n" +
                //"    -fx-padding: 3px;");
                // my lines does'nt have series, and I don't need this part
                for (XYChart.Data<Long, Double> d : series.get(ind).getData()) {
                    if (d.getNode() != null) {
                        //System.out.println("stp.getGraphColor()="+stp.getGraphColor());
                        Node lineSymbol = d.getNode().lookup(".chart-line-symbol");
                        lineSymbol.setStyle("-fx-background-color: "+stp.getGraphColor()+", #000000;");
                        d.getNode().setVisible(true); // Toggle visibility of every node in the series
                    }
                }
                
            } else {        // Hide line on if chk box is  marked
                //System.out.println("To HIDE  ind="+ind);
                series.get(ind).getNode().setVisible(false); // Toggle visibility of line
                // my lines does'nt have series, and I don't need this part
                for (XYChart.Data<Long, Double> d : series.get(ind).getData()) {
                    if (d.getNode() != null) {
                        d.getNode().setVisible(false); // Toggle visibility of every node in the series
                    }
                }
            }
        }
    }
    
    
    private void addGraphData(LineChart.Series<Long, Double> series, List<String> bufData, Setup stp){
        System.out.println("GraphController addGraphData started");
        //mainApp.series.add(ind, new LineChart.Series<>());
        long x=0;
        double y=0;
        
        int numGridX = 50;
        int numGridY = 20;
        for (String s : bufData){
            String[] csv = s.split(",");
            //System.out.println("-->1 == SCV[0]="+ csv[0]+"SCV[1]="+ csv[1]+" x="+ x+" Back to date="+fromMStoDate(x));
            //x = fromStringToMs(csv[0]);
            x =  (long) (stp.getGraphXScale()*(stp.getGraphXShift()+fromStringToMs(csv[0])));
            y = stp.getGraphYScale()*(stp.getGraphYShift()+Double.parseDouble(csv[1]));
            //System.out.println("-->2 == SCV[0]="+ fromStringToMs(csv[0])+" x="+ x);
            
            // setup initial values for min and max
            if (!minmaxSet){
                maxX = minX = x;
                maxY = minY = y;
                minmaxSet = true;
            } else {   // looking for min and max values on the graph to set up boundaries
                if (x > maxX)  maxX=x; 
                else if (x < minX) minX=x;
                if (y > maxY)  maxY=y;
                else if (y < minY) minY=y;
                //System.out.println(" +++++ minX= " + minX +" maxX= " + maxX +" minY="+minY+" maxY="+maxY);
            }
                
            //series.getData().add(new XYChart.Data<>(csv[0], stp.getGraphScale()*(stp.getGraphShift()+Double.parseDouble(csv[1]))));
            series.getData().add(new XYChart.Data<>(x ,y ));
            
        }
        //System.out.println("addGraphData.New Data added to the chart ind="+stp.getLegendNum()+" minX="+minX+" maxX="+maxX);
        //System.out.println(bufData.get(0));
        //String[] minbound = bufData.get(0).split(",");
        //System.out.println("minbound= " + minbound[0]);
        //System.out.println("minX= " + minX +" maxX= " + maxX +" minY="+minY+" maxY="+maxY);
        xAxis.setLowerBound(minX);
        xAxis.setUpperBound(maxX);
        yAxis.setLowerBound(minY); 
        yAxis.setUpperBound(maxY);
        yAxis.accessibleHelpProperty(); 
        xAxis.setTickUnit((maxX-minX)/numGridX);
        yAxis.setTickUnit((maxY-minY)/numGridY);
        //myLowerBound = Double.parseDouble(minbound[0]);
        //graphData.setLowerX(minX);
        //graphData.setUpperX(maxX);
        //graphData.setLowerY(maxY);
        //graphData.setUpperY(maxY);
        //double minRange = fromStringToMs(bufData.get(0).split(",")[0]);
        //System.out.println("minRange= " + minRange);
        //System.out.println("Settings done");
        //xAxis.invalidateRange((List)xAxis.getTickMarks());
        //System.out.println("invalidateRange for xAxis");
        //yAxis.invalidateRange((List)yAxis.getTickMarks());
        //System.out.println("invalidateRange for yAxis");
        //lineChart.getData().addAll(series);
        System.out.println("GraphController addGraphData finished");
    }
 
   @FXML
    public void initialize() {
        // TODO
        System.out.println("GrafController Initialize started.");
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(false);
        //xAxis.setLowerBound(graphData.getLowerX());
        //xAxis.setUpperBound(graphData.getUpperX());
        //xAxis.setLowerBound(fromStringToMs(bufData.get(0).split(",")[0]));
        //xAxis.setUpperBound(fromStringToMs(bufData.get(0).split(",")[999]));
        //xAxis.setTickUnit(0.1);
        //System.out.println("GrafController " + xAxis.getl.getLabel());
        //System.out.println("GrafController " + fromMStoDate(Double.parseDouble(xAxis.getLabel())));

        /*xAxis.setTickLabelFormatter(
            //new MyDataFormatter("text") //fromMStoDate(Double.parseDouble(xAxis.getLabel())))
            new MyDataFormatter()
        );
        */
        /************************** More Working examples of setTickLabelFormatter ***************************************/
        /* from https://stackoverflow.com/questions/19399667/is-it-possible-to-change-the-values-of-a-numberaxis-in-javafx-2-0 */
        /* from https://stackoverflow.com/questions/19399667/is-it-possible-to-change-the-values-of-a-numberaxis-in-javafx-2-0 */
        /************************************************************************************************************/

        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) { 
            @Override public String toString(Number object) { return String.format("%1$tm.%1$te.%1$tY %1$TH:%1$TM",object.longValue());
            } 
        });
        
        /*****************************************************************/
        xAxis.setTickLabelRotation(-90); 
                 
    } 
    
    /*private Date fromString(String string) {
       //DateTimeStringConverter dt = new DateTimeStringConverter("yyyy.MM.dd HH:mm");
        //System.out.println("fromString "+ string + " toString "+dt.fromString(string).toString());
        //return dt.fromString(string);
        return String.format("%1$tm.%1$te.%1$tY %1$TH:%1$TM",string);

    }*/
    
     private long fromStringToMs(String string) {
        DateTimeStringConverter dt = new DateTimeStringConverter("yyyy.MM.dd HH:mm");
        //System.out.println("fromString "+ string + " toMS "+(double)dt.fromString(string).getTime());
        //System.out.format("fromString %s toMS %d%n", string,dt.fromString(string).getTime());
        return (long)dt.fromString(string).getTime();
    }
     
     private String fromMStoDate(long ms) {
         //Date date=new Date((long)ms);
         //SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
         //String strDate = sdf.format(date);
         //System.out.println("fromMS "+ ms + " toString "+strDate);
         //return strDate;
         return String.format("%1$tm.%1$te.%1$tY %1$TH:%1$TM",ms);
     }
     
     public List<LineChart.Series<Long, Double>> getLineChart(){
         return lineChart.getData();
     }
     
 
}
