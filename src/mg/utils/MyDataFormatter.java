/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.util.StringConverter;


 // -------------- INNER CLASSES ------------------------------------------------------------------------------------

    /**
     * Default number formatter for NumberAxis, this stays in sync with auto-ranging and formats values appropriately.
     * You can wrap this formatter to add prefixes or suffixes;
     * @since JavaFX 2.0
     */
    public  class MyDataFormatter extends StringConverter<Number> {
        private DecimalFormat formatter;
        private String prefix = null;
        private String suffix = null;

        /**
         * Construct a DefaultFormatter for the given NumberAxis
         *
         * @param axis The axis to format tick marks for
         */
        public MyDataFormatter() {
            formatter =  new DecimalFormat();
        /*    final ChangeListener<Object> axisListener = (observable, oldValue, newValue) -> {
                formatter = axis.isAutoRanging()? new DecimalFormat(axis.currentFormatterProperty.get()) : new DecimalFormat();
            };
            axis.currentFormatterProperty.addListener(axisListener);
            axis.autoRangingProperty().addListener(axisListener);
        */    
        }

        /**
         * Construct a DefaultFormatter for the given NumberAxis with a prefix and/or suffix.
         *
         * @param axis The axis to format tick marks for
         * @param prefix The prefix to append to the start of formatted number, can be null if not needed
         * @param suffix The suffix to append to the end of formatted number, can be null if not needed
         */
        public MyDataFormatter( String label) {
            //this(axis);
            this.prefix = label;
            
        }

        /**
        * Converts the object provided into its string form.
        * Format of the returned string is defined by this converter.
        * @param object
        * @return a string representation of the object passed in.
        * @see StringConverter#toString
        */
        @Override public String toString(Number object) {
            return toString(object, formatter);
        }

        private String toString(Number object, String numFormatter) {
            if (numFormatter == null || numFormatter.isEmpty()) {
                return toString(object, formatter);
            } else {
                return toString(object, new DecimalFormat(numFormatter));
            }
        }

        private String toString(Number object, DecimalFormat formatter) {
            /*if (prefix != null && suffix != null) {
                return prefix + formatter.format(object) + suffix;
            } else if (prefix != null) {
                return prefix + formatter.format(object);
            } else if (suffix != null) {
                return formatter.format(object) + suffix;
            } else {
                return formatter.format(object);
            }
            */
            Date date=new Date(object.longValue());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String strDate = sdf.format(date);
            //fromMStoDate(Double.parseDouble(object.getLabel()));
            return strDate;
        }

        /**
        * Converts the string provided into a Number defined by the this converter.
        * Format of the string and type of the resulting object is defined by this converter.
        * @param string
        * @return a Number representation of the string passed in.
        * @see StringConverter#toString
        */
        @Override public Number fromString(String string) {
            try {
                int prefixLength = (prefix == null)? 0: prefix.length();
                int suffixLength = (suffix == null)? 0: suffix.length();
                return formatter.parse(string.substring(prefixLength, string.length() - suffixLength));
            } catch (ParseException e) {
                return null;
            }
        }
    }
