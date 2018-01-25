/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp;

/**
 *
 * @author us
 */
public class tmpCustomLegend {
    /* https://stackoverflow.com/questions/12197877/javafx-linechart-legend-style?rq=1
    I added a ListChangeListener to the legend's "getChildrenUnmodifiable()" ObservableList, 
    which in turn adds a ListChangeListener to each of the legend's children as they get added. 
    From within this listener, we can tell when new items are being added to the 
    legend (or removed). This allow us to then make the dynamic style changes.
    
    for (Node n : lineChart.getChildrenUnmodifiable())
        {
            if (n instanceof Legend)
            {
                final Legend legend = (Legend) n;

                // remove the legend
                legend.getChildrenUnmodifiable().addListener(new ListChangeListener<Object>()
                {
                    @Override
                    public void onChanged(Change<?> arg0)
                    {
                        for (Node node : legend.getChildrenUnmodifiable())
                        {
                            if (node instanceof Label)
                            {
                                final Label label = (Label) node;
                                label.getChildrenUnmodifiable().addListener(new ListChangeListener<Object>()
                                {
                                    @Override
                                    public void onChanged(Change<?> arg0)
                                    {
                                        //make style changes here
                                    }

                                });
                            }
                        }
                    }
                });
            }
        }
    */
    
    
    /*Trying to wrap text in css didn't work as label width cannot be controlled 
    there. So the following code can be used to wrap the text programmatically.

    for (Node node : pie.lookupAll(".chart-legend-item")) {
                    if (node instanceof Label) {
                        System.out.println("Label instance");
                        ((Label) node).setWrapText(true);
                        ((Label) node).setManaged(true);
                        ((Label) node).setPrefWidth(380);
                    }
    }
    */

    
    /*
    
    
    !!!!!!!!!!!!!!!!!!! Very nice article !!!!!!!!!!!!!!!!!
    
    https://stackoverflow.com/questions/20983131/remove-javafx-2-linechart-legend-items
    After several failed attempts at implementing various suggestions, I found 
    that the best way to allow a user to show/hide a data series in a JavaFx Chart 
    (or sub-classes thereof) is to extend the chart class you want to use and 
    override its updateLegend() method.

    It's quite simple actually. Here's an example using a basic HBox as the legend 
    containing check boxes as the legend items. In this example I have decided to 
    make my LineChart with fixed axes types (CategoryAxis and NumberAxis). You might 
    choose to leave your sub-class with generics for axes.

public class AtopLineChart<X, Y> extends LineChart<String, Number>
{

   /**
    * @param xAxis
    * @param yAxis
    * /
   public AtopLineChart(final CategoryAxis xAxis, final NumberAxis yAxis)
   {
      super(xAxis, yAxis);
   }

   /* (non-Javadoc)
    * @see javafx.scene.chart.LineChart#updateLegend()
    * /
   @Override
   protected void updateLegend()
   {
      final HBox legend = new HBox();
      legend.setVisible(true);
      for (final javafx.scene.chart.XYChart.Series<String, Number> series : getData())
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
    I'll leave it as an exercise for the reader to make the legend more readable, 
    for example, borders around each checkbox and binding the color of the series 
    to the something showing that color in the checkbox for the series.

    One other thing, you might want to check the getLegendSide() method to decide 
    which kind of layout container to use for the legend, i.e. HBox for TOP and 
    BOTTOM but VBOX for LEFT and RIGHT. Your choice.
    */
    
    
    
    /*
    
    !!!!!!!!!!!!!!!!!!  Very nice example !!!!!!!!!!!!!!!!!!!!!!
     Use Chart Legend to toggle show/hide Series 
https://stackoverflow.com/questions/44956955/javafx-use-chart-legend-to-toggle-show-hide-series-possible
    Here is how I solved this - I am not aware of any simpler built-in solution
    You need to run this code once on your chart (LineChart in this example, but 
    you can probably adapt it to any other chart). I find the Legend child, and 
    then iterate over all of its' items. I match the legend item to the correct 
    series based on the name - from my experience they always match, and I couldn't 
    find a better way to match them. Then it's just a matter of adding the correct 
    event handler to that specific legend item.

    LineChart<Number, Number> chart;

    for (Node n : chart.getChildrenUnmodifiable()) {
        if (n instanceof Legend) {
            Legend l = (Legend) n;
            for (Legend.LegendItem li : l.getItems()) {
                for (XYChart.Series<Number, Number> s : chart.getData()) {
                    if (s.getName().equals(li.getText())) {
                        li.getSymbol().setCursor(Cursor.HAND); // Hint user that legend symbol is clickable
                        li.getSymbol().setOnMouseClicked(me -> {
                            if (me.getButton() == MouseButton.PRIMARY) {
                                s.getNode().setVisible(!s.getNode().isVisible()); // Toggle visibility of line
                                for (XYChart.Data<Number, Number> d : s.getData()) {
                                    if (d.getNode() != null) {
                                        d.getNode().setVisible(s.getNode().isVisible()); // Toggle visibility of every node in the series
                                    }
                                }
                            }
                        });
                        break;
                    }
                }
            }
        }
    }
    
    */
    
    
    
    /*
    How to draw multiple axis on a chart using JAVAFX Charts
https://stackoverflow.com/questions/9667405/how-to-draw-multiple-axis-on-a-chart-using-javafx-charts/9694628#9694628
    
    */

}