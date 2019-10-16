package it.teamgdm.sms.dibapp;

import com.github.mikephil.charting.formatter.ValueFormatter;

//FORMATTER (value on bars from float to integer)

public class BarChartIntergerFormatter extends ValueFormatter {

    @Override
    public String getFormattedValue(float value) {
        return "" + ((int) value);
    }

}
