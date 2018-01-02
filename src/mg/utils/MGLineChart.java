/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.utils;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 * https://stackoverflow.com/questions/20983131/remove-javafx-2-linechart-legend-items
    After several failed attempts at implementing various suggestions, I found 
    that the best way to allow a user to show/hide a data series in a JavaFx Chart 
    (or sub-classes thereof) is to extend the chart class you want to use and 
    override its updateLegend() method.

    It's quite simple actually. Here's an example using a basic HBox as the legend 
    containing check boxes as the legend items. In this example I have decided to 
    make my LineChart with fixed axes types (CategoryAxis and NumberAxis). You might 
    choose to leave your sub-class with generics for axes.
 * 
 * @author us
 */

    public class MGLineChart<X, Y> extends LineChart<X, Y>
{

   /**
     * Construct a new LineChart with the given axis and data.
     *
     * @param xAxis The x axis to use
     * @param yAxis The y axis to use
    */
   //public MGLineChart(final NumberAxis xAxis, final NumberAxis yAxis)@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis
    public MGLineChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis )
   {
      super(xAxis, yAxis);
   }

   
   /* (non-Javadoc)
    * @see javafx.scene.chart.LineChart#updateLegend()
    */
   @Override
   protected void updateLegend()
   {
      final VBox legend = new VBox();
      legend.setVisible(true);
      for (final Series<X, Y> series : getData())
      {
         final CheckBox cb = new CheckBox(series.getName());
         cb.setUserData(series);
         cb.setSelected(true);
         cb.addEventHandler(ActionEvent.ACTION, e ->
         {
            final CheckBox box = (CheckBox) e.getSource();
            @SuppressWarnings("unchecked")
            final Series<String, Number> s = (Series<String, Number>) box.getUserData();
            s.getNode().setVisible(box.isSelected());
         });
         legend.getChildren().add(cb);
      }
      setLegend(legend);
   }
}
    

