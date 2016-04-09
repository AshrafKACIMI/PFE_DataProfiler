/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import fxmltest.computing.ColumnInfo;
import fxmltest.computing.ColumnProfilingStatsRow;
import fxmltest.computing.TableInfo;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.chart.Charts.meterChart;
import net.sf.dynamicreports.report.builder.chart.MeterChartBuilder;
import net.sf.dynamicreports.report.builder.chart.ThermometerChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Ashraf
 */
public class TableReport {

    public TableReport(TableInfo tab) {
        build(tab);
    }

    private void build(TableInfo tab) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());
        String fileName = tab.getName() + " " 
                + timeStamp + ".pdf";
        String saveTo = System.getProperty("user.home")+"\\Profiling Results\\"+
                fileName;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveTo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        StyleBuilder boldStyle = stl.style().bold();
        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                .setBorder(stl.pen1Point())
                .setBackgroundColor(Color.LIGHT_GRAY);
        StyleBuilder titleStyle = stl.style(boldCenteredStyle)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(15);
       //"column", "nbNull", "nbNotNull", "nbRows", "min", "max"
        TextColumnBuilder<String> itemColumn = col.column("Column", "column", type.stringType()).setStyle(boldStyle);
        TextColumnBuilder<Integer> nbNullColumn = col.column("Nb Null", "nbNull", type.integerType());
        TextColumnBuilder<Integer> nbNotNullColumn = col.column("Nb not Null", "nbNotNull", type.integerType());
        TextColumnBuilder<Integer> nbLinesColumn = col.column("Nb rows", "nbRows", type.integerType());
        TextColumnBuilder<String> minColumn = col.column("Min", "min", type.stringType());
        minColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<String> maxColumn = col.column("Max", "max", type.stringType());
        maxColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<Integer> problematicColumn = col.column("Problematic", "problem", type.integerType());
        
        
        ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.greater(problematicColumn, 0))
                                              .setBackgroundColor(new Color(210, 255, 210));
            
        
        TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn("No.")
                //sets the fixed width of a column, width = 2 * character width
                .setFixedColumns(2)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

                ColumnGroupBuilder itemGroup = grp.group(itemColumn);
        itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));
        
        
        ThermometerChartBuilder completeness = cht.thermometerChart()
	         .setValue(tab.getOverallCompleteness());
        
        MeterChartIndicator cht2 = new MeterChartIndicator(tab.getOverallCompleteness(), 50);
        MeterChartBuilder cht1 =  cht.meterChart()
                .setValue(tab.getOverallCompleteness())
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
                                .setDataRangeLowExpression(30)
                                .setDataRangeHighExpression(100),
                        cht.meterInterval()
                                .setLabel("Critical")
                                .setBackgroundColor(new Color(255, 150, 150))
                                .setDataRangeLowExpression(0)
                                .setDataRangeHighExpression(30)
                );
        
        try {
            report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .highlightDetailEvenRows()
                    .detailRowHighlighters(
                    condition1)
                    .columns(//add columns
                            itemColumn, nbNullColumn, nbNotNullColumn, nbLinesColumn, minColumn, maxColumn, problematicColumn)
                    //.groupBy(itemGroup)
                    .subtotalsAtSummary(
                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
                    .subtotalsAtFirstGroupFooter(
                            sbt.sum(nbNotNullColumn), sbt.sum(nbLinesColumn))
                    .summary(cmp.horizontalList(completeness, cht1))

                    .title(//shows report title
                            cmp.horizontalList()
                                    .add(
                                            cmp.image(getClass().getResource("bbi.png")).setFixedDimension(160, 120),
                                            cmp.text("BBI Profiling results").setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.JUSTIFIED),
                                            cmp.text(tab.getName()).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                                    .newRow()
                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                    //.setDataSource(createDataSource(TablesFactory.getTables().get(0)))
                    .setDataSource(createDataSource(tab))
                    //.show();
                    .toPdf(fos);

            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (DRException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
            
//            Mail.ReportingEMail mail = new ReportingEMail(fileName);
//            mail.send();
        
//            final String fileLocation = "\\test1.pdf";
//            System.out.println(fileLocation);
//            ReportingEMail instance = new ReportingEMail(fileLocation);
//            instance.send();
        
//            
    }

    private JRDataSource createDataSource(TableInfo tab) {
        //DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
        /*
        class TableInfo {
            private String schema;
            private String name;
            private ArrayList<ColumnInfo> columns;
        String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max
        }
        */
        
        DRDataSource dataSource = new DRDataSource("column", "nbNull", "nbNotNull", "nbRows", "min", "max", "problem", "completeRation");
        //String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max
        for (ColumnInfo ci: tab.getColumns()){
            ColumnProfilingStatsRow stats = ci.getStats();
            dataSource.add(
                    stats.getColumnName(),
                    stats.getNbNull(),
                    stats.getNbDistinct(),
                    stats.getNbLines(),
                    stats.getMin(),
                    stats.getMax(),
                    stats.getViolNotNullFlag(),
                    stats.getNbNull()/stats.getNbLines() * 100
                    );            
        }
        
        System.out.println(dataSource);
        return dataSource;
    }

}
