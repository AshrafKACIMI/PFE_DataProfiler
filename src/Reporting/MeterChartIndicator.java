/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Reporting;

import java.awt.Color;
import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import net.sf.dynamicreports.report.builder.chart.MeterChartBuilder;

/**
 *
 * @author Ashraf
 */
public class MeterChartIndicator extends MeterChartBuilder{
    
    
    
    public MeterChartIndicator(int value, int min){
        cht.meterChart()
                .setValue(value)
                .setDataRangeHighExpression(100)
                .setTickInterval(10d)
                .setTickColor(Color.BLACK)
                .setNeedleColor(Color.BLACK)
                .setValueColor(Color.BLACK)
                .setMeterBackgroundColor(Color.LIGHT_GRAY)
                .intervals(
                        cht.meterInterval()
                                .setLabel("Good")
                                .setBackgroundColor(new Color(150, 255, 150))
                                .setDataRangeLowExpression(min)
                                .setDataRangeHighExpression(100),
                        cht.meterInterval()
                                .setLabel("Critical")
                                .setBackgroundColor(new Color(255, 150, 150))
                                .setDataRangeLowExpression(0)
                                .setDataRangeHighExpression(min)
                );
    }
    
}
