/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import Mail.EmailOptions;
import Mail.ReportingEMail;
import fxmltest.MailTest;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.ColumnProfilingStatsRow;
import fxmltest.computing.TableInfo;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.chart.MeterChartBuilder;
import net.sf.dynamicreports.report.builder.chart.ThermometerChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
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
    private JasperReportBuilder report;

    public TableReport(TableInfo tab) {
        JasperReportBuilder report = build(tab);
    }

    private JasperReportBuilder build(TableInfo tab) {
        String saveFolder = EmailOptions.getFileDirectory();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());
        String fileName = tab.getName() + " " 
                + timeStamp + ".pdf";
        String saveTo = saveFolder + fileName;
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
        //TextColumnBuilder<Integer> problematicColumn = col.column("Problematic", "problem", type.integerType());
        
        
//        ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.greater(problematicColumn, 0))
//                                              .setBackgroundColor(new Color(210, 255, 210));
//            
        
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
                .setUnits("%")
                .setMeterBackgroundColor(Color.LIGHT_GRAY)
                .setTitle("Overall Completeness")

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
        
        MeterChartBuilder cht3 =  cht.meterChart()
                .setValue(tab.getOverallUniqueness())
                .setDataRangeHighExpression(100)
                .setTickInterval(10d)
                .setTickColor(Color.BLACK)
                .setNeedleColor(Color.BLACK)
                .setValueColor(Color.BLACK)
                .setUnits("%")
                .setTitle("Overall Uniqueness")
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
            report = report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .highlightDetailEvenRows()
//                    .detailRowHighlighters(
//                            condition1)
                    .columns(//add columns
                            itemColumn, nbNullColumn, nbNotNullColumn, nbLinesColumn, minColumn, maxColumn
                            //, problematicColumn
                    )
                    //.groupBy(itemGroup)
                    .subtotalsAtSummary(
                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
                    .subtotalsAtFirstGroupFooter(
                            sbt.sum(nbNotNullColumn), sbt.sum(nbLinesColumn))
                    .summary(cmp.horizontalList(cht1, cht3))
                    
                    .title(//shows report title
                            cmp.horizontalList()
                                    .add(
                                            cmp.image(getClass().getResource("bbi.png")).setFixedDimension(160, 120),
                                            cmp.text("BBI Profiling results" + timeStamp).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.JUSTIFIED),
                                            cmp.text(tab.getName()).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                                    .newRow()
                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                    //.setDataSource(createDataSource(TablesFactory.getTables().get(0)))
                    .setDataSource(createDataSource(tab))
                    .toPdf(fos);
        } catch (DRException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
            Platform.runLater(() -> {
                Mail.ReportingEMail mail = new ReportingEMail(fileName);
                mail.send();
                String args[] = new String[3];
                args[0] = fileName;
                MailTest.main(args);
            });
        
            return getReport();
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
                    stats.getViolNotNullFlag()
                    //, stats.getNbNull()/stats.getNbLines() * 100
                    );            
        }
        
        System.out.println(dataSource);
        return dataSource;
    }

    /**
     * @return the report
     */
    public JasperReportBuilder getReport() {
        return report;
    }

    
    public static JasperConcatenatedReportBuilder getConcatenatedReports(ArrayList<JasperReportBuilder> reports){
        JasperConcatenatedReportBuilder running = concatenatedReport();
        //casting ArrayList to array as parameter
        //running.concatenate(reports.toArray(new JasperReportBuilder[reports.size()]));
        for (JasperReportBuilder report: reports){
            running.concatenate(report);
            System.out.println("Report : " + report);
        }
        return running;
    }
    
    public static void saveConcatenatedReports(ArrayList<JasperReportBuilder> reports){
        try {
            JasperConcatenatedReportBuilder report = getConcatenatedReports(reports);
            System.out.println("REPORT : " + report.toString());
            FileOutputStream fos = null;
            String saveTo = EmailOptions.getFileDirectory() + "testConcat.pdf";
            try {
                fos = new FileOutputStream(saveTo);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
            }
            report.toPdf(fos);
        } catch (DRException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
