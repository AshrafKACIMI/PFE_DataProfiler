/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import DQRepository.IConnector;
import DQRepository.MetaDataConnector;
import Entities.ColumnInfo;
import Entities.ColumnProfilingStatsRow;
import Entities.TableInfo;
import Mail.EmailOptions;
import Mail.ReportingEMail;
import fxmltest.FXMLDocumentController;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import net.sf.dynamicreports.*;
import net.sf.jasperreports.*;

/**
 * @author Ashraf
 */
public class TableReport {

    private JasperReportBuilder report;

    public TableReport(TableInfo tab, boolean toPdf) {
        JasperReportBuilder report = build(tab, toPdf);
    }

    
    public TableReport(TableInfo tab, boolean toPdf, boolean schedule) {
        JasperReportBuilder report = build(tab, toPdf, true);
    }

    public TableReport(String dbName, String tableName, boolean toPdf) {
        JasperReportBuilder report = build(dbName, tableName, toPdf);
    }
    
    

//    private JasperReportBuilder build(TableInfo tab) {
//        String saveFolder = EmailOptions.getFileDirectory();
//        Date currentDate = Calendar.getInstance().getTime();
//        String fileTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
//                .format(currentDate);
//        String titleTimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//                .format(currentDate);
//        String fileName = tab.getName() + " " 
//                + fileTimeStamp + ".pdf";
//        String saveTo = saveFolder + fileName;
//        
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(saveTo);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        StyleBuilder boldStyle = stl.style().bold();
//        StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
//        StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
//                .setBorder(stl.pen1Point())
//                .setBackgroundColor(Color.LIGHT_GRAY);
//        StyleBuilder titleStyle = stl.style(boldCenteredStyle)
//                .setVerticalAlignment(VerticalAlignment.MIDDLE)
//                .setFontSize(15);
//       //"column", "nbNull", "nbNotNull", "nbRows", "min", "max"
//        TextColumnBuilder<String> itemColumn = col.column("Column", "column", type.stringType()).setStyle(boldStyle);
//        TextColumnBuilder<Integer> nbNullColumn = col.column("Nb Null", "nbNull", type.integerType());
//        TextColumnBuilder<Integer> nbDistinctColumn = col.column("Nb Distinct", "nbDistinct", type.integerType());
//        TextColumnBuilder<Integer> nbLinesColumn = col.column("Nb rows", "nbRows", type.integerType());
//        TextColumnBuilder<String> minColumn = col.column("Min", "min", type.stringType());
//        minColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
//        TextColumnBuilder<String> maxColumn = col.column("Max", "max", type.stringType());
//        maxColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
//
//        
//        MeterChartBuilder cht1 = MeterChartIndicatorBuilder.meterChart(tab.getOverallCompleteness(), 30, "Completeness");
//        MeterChartBuilder cht3 = MeterChartIndicatorBuilder.meterChart(tab.getOverallUniqueness(), 30, "Uniqueness");
//        
////        
//        try {
//            report = 
//                report()//create new report design
//                    .setColumnTitleStyle(columnTitleStyle)
//                    .setSubtotalStyle(boldStyle)
//                    .highlightDetailEvenRows()
////                    .detailRowHighlighters(
////                            condition1)
//                    .columns(//add columns
//                            itemColumn, nbNullColumn, nbDistinctColumn, nbLinesColumn, minColumn, maxColumn
//                            //, problematicColumn
//                    )
//                    //.groupBy(itemGroup)
////                    .subtotalsAtSummary(
////                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
////                    .subtotalsAtFirstGroupFooter(
////                            sbt.sum(nbDistinctColumn), sbt.sum(nbLinesColumn))
//   
//                    .summary(cmp.horizontalList(cht1, cht3))
//                    
//                    .title(//shows report title
//                            cmp.horizontalList()
//                                    .add(
//                                            cmp.image(getClass().getResource("bbi.png")).setFixedDimension(160, 120),
//                                            cmp.text("BBI Profiling results" + titleTimeStamp).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.JUSTIFIED),
//                                            cmp.text(tab.getName()).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
//                                    .newRow()
//                                    .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
//                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
//                    //.setDataSource(createDataSource(TablesFactory.getTables().get(0)))
//                    .setDataSource(createDataSource(tab))
//                    .toPdf(fos);
//        } catch (DRException ex) {
//            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            fos.close();
//        } catch (IOException ex) {
//            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            
//        
////        if (EmailOptions.isOn()){
////            Platform.runLater(() -> {
////                Mail.ReportingEMail mail = new ReportingEMail(fileName);
////                mail.send();
////                String args[] = new String[3];
////                args[0] = fileName;
////                MailTest.main(args);
////            });
////        }
//            return getReport();
////            
//    }
//
//    private JRDataSource createDataSource(TableInfo tab) {
//        //DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
//        /*
//        class TableInfo {
//            private String schema;
//            private String name;
//            private ArrayList<ColumnInfo> columns;
//        String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max
//        }
//        */
//        
//        DRDataSource dataSource = new DRDataSource("column", "nbNull", "nbRows", "nbDistinct",  "min", "max");
//        //String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max
//        for (ColumnInfo ci: tab.getColumns()){
//            ColumnProfilingStatsRow stats = ci.getStats();
//            dataSource.add(
//                    stats.getColumnName(),
//                    stats.getNbNull(),
//                    stats.getNbLines(),
//                    stats.getNbDistinct(),
//                    stats.getMin(),
//                    stats.getMax()
//                    );            
//        }
//        
//        System.out.println(dataSource);
//        return dataSource;
//    }
    private JasperReportBuilder build(String dbName, String tableName, boolean toPdf) {
        String saveFolder = EmailOptions.getFileDirectory();
                //System.getProperty("user.home") + "\\Profiling Results\\";
        Date currentDate = Calendar.getInstance().getTime();
        String fileTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(currentDate);
        String titleTimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(currentDate);
        
        String fileName = tableName + " "
                + fileTimeStamp + ".pdf";
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
        TextColumnBuilder<String> itemColumn = col.column("Column", "column", type.stringType()).setStyle(boldStyle).setWidth(40);
        TextColumnBuilder<Integer> nbNullColumn = col.column("Nb Null", "nbNull", type.integerType()).setWidth(12);
        TextColumnBuilder<Integer> nbDistinctColumn = col.column("Nb Distinct", "nbDistinct", type.integerType()).setWidth(12);
        TextColumnBuilder<Integer> nbLinesColumn = col.column("Nb rows", "nbRows", type.integerType()).setWidth(12);
        TextColumnBuilder<String> minColumn = col.column("Min", "min", type.stringType()).setWidth(12);
        minColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<String> maxColumn = col.column("Max", "max", type.stringType()).setWidth(12);
        maxColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        MeterChartBuilder cht1 = MeterChartIndicatorBuilder.meterChart(FXMLDocumentController.getTableByName(tableName).getOverallCompleteness(), 30, "Completeness");
        MeterChartBuilder cht3 = MeterChartIndicatorBuilder.meterChart(FXMLDocumentController.getTableByName(tableName).getOverallUniqueness(), 30, "Uniqueness");

        //        
        try {
            report
                    = report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .highlightDetailEvenRows()
                    //                    .detailRowHighlighters(
                    //                            condition1)
                    .columns(//add columns
                            itemColumn, nbNullColumn, nbDistinctColumn, nbLinesColumn, minColumn, maxColumn
                    //, problematicColumn
                    )
                    //.groupBy(itemGroup)
                    //                    .subtotalsAtSummary(
                    //                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
                    //                    .subtotalsAtFirstGroupFooter(
                    //                            sbt.sum(nbDistinctColumn), sbt.sum(nbLinesColumn))

                    .summary(cmp.horizontalList(cht1, cht3))
                    .title(//shows report title
                            cmp.horizontalList()
                            .add(
                                    cmp.image(getClass().getResource("bbi.png")).setFixedDimension(160, 120),
                                    cmp.text("BBI Profiling results" + titleTimeStamp).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.JUSTIFIED),
                                    cmp.text(tableName).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                            .newRow()
                            .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                    //.setDataSource(createDataSource(TablesFactory.getTables().get(0)))
                    .setDataSource(createDataSource(dbName, tableName))
                    .toPdf(fos);
        } catch (DRException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }

//        if (EmailOptions.isOn()) {
//            Platform.runLater(() -> {
//                Mail.ReportingEMail mail = new ReportingEMail(fileName);
//                mail.send();
////                    String args[] = new String[3];
////                    args[0] = fileName;
////                    MailTest.main(args);
//            });
//        }
        return getReport();
        //            
    }

    private JasperReportBuilder build(TableInfo table, boolean toPdf) {
        String saveFolder = //EmailOptions.getFileDirectory();
                System.getProperty("user.home") + "\\Profiling Results\\";
        Date currentDate = Calendar.getInstance().getTime();
        String fileTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(currentDate);
        String titleTimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(currentDate);
        String fileName = table.getName() + " "
                + fileTimeStamp + ".pdf";
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
        TextColumnBuilder<String> itemColumn = col.column("Column", "column", type.stringType()).setStyle(boldStyle).setWidth(40);
        TextColumnBuilder<Integer> nbNullColumn = col.column("Nb Null", "nbNull", type.integerType()).setWidth(12);
        TextColumnBuilder<Integer> nbDistinctColumn = col.column("Nb Distinct", "nbDistinct", type.integerType()).setWidth(12);
        TextColumnBuilder<Integer> nbLinesColumn = col.column("Nb rows", "nbRows", type.integerType()).setWidth(12);
        TextColumnBuilder<String> minColumn = col.column("Min", "min", type.stringType()).setWidth(12);
        minColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<String> maxColumn = col.column("Max", "max", type.stringType()).setWidth(12);
        maxColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);

        MeterChartBuilder cht1 = MeterChartIndicatorBuilder.meterChart(table.getOverallCompleteness(), 30, "Completeness");
        MeterChartBuilder cht3 = MeterChartIndicatorBuilder.meterChart(table.getOverallUniqueness(), 30, "Uniqueness");

        report
                = report()//create new report design
                .setColumnTitleStyle(columnTitleStyle)
                .setSubtotalStyle(boldStyle)
                .highlightDetailEvenRows()
                //                    .detailRowHighlighters(
                //                            condition1)
                .columns(//add columns
                        itemColumn, nbNullColumn, nbDistinctColumn, nbLinesColumn, minColumn, maxColumn
                //, problematicColumn
                )
                //.groupBy(itemGroup)
                //                    .subtotalsAtSummary(
                //                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
                //                    .subtotalsAtFirstGroupFooter(
                //                            sbt.sum(nbDistinctColumn), sbt.sum(nbLinesColumn))

                .summary(cmp.horizontalList(cht1, cht3))
                .title(//shows report title
                        cmp.horizontalList()
                        .add(
                                cmp.image(getClass().getResource("bbi.png")).setFixedDimension(160, 120),
                                cmp.text("BBI Profiling results" + titleTimeStamp).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.JUSTIFIED),
                                cmp.text(table.getName()).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                        .newRow()
                        .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                //.setDataSource(createDataSource(TablesFactory.getTables().get(0)))
                .setDataSource(createDataSource(table.getSchema(), table.getName()));

        if (toPdf) {
            try {
                getReport().toPdf(fos);
                String attached = EmailOptions.getFileDirectory() + fileName;
                if (EmailOptions.isOn()){
                	Platform.runLater(() -> {

	                    ReportingEMail mail = new ReportingEMail(attached, fileName);
	                    mail.send();
                	});
                }
            } catch (DRException ex) {
                Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                return getReport();


    //        if (EmailOptions.isOn()){
        //            Platform.runLater(() -> {
        //                Mail.ReportingEMail mail = new ReportingEMail(fileName);
        //                mail.send();
        //                String args[] = new String[3];
        //                args[0] = fileName;
        //                MailTest.main(args);
        //            });
        //        }
        //            
    }
    
    private JasperReportBuilder build(TableInfo table, boolean toPdf, boolean schedule) {
        String saveFolder = //EmailOptions.getFileDirectory();
                System.getProperty("user.home") + "\\Profiling Results\\";
        Date currentDate = Calendar.getInstance().getTime();
        String fileTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(currentDate);
        String titleTimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .format(currentDate);
        String fileName = table.getName() + " "
                + fileTimeStamp + ".pdf";
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
        TextColumnBuilder<String> itemColumn = col.column("Column", "column", type.stringType()).setStyle(boldStyle).setWidth(40);
        TextColumnBuilder<Integer> nbNullColumn = col.column("Nb Null", "nbNull", type.integerType()).setWidth(12);
        TextColumnBuilder<Integer> nbDistinctColumn = col.column("Nb Distinct", "nbDistinct", type.integerType()).setWidth(12);
        TextColumnBuilder<Integer> nbLinesColumn = col.column("Nb rows", "nbRows", type.integerType()).setWidth(12);
        TextColumnBuilder<String> minColumn = col.column("Min", "min", type.stringType()).setWidth(12);
        minColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);
        TextColumnBuilder<String> maxColumn = col.column("Max", "max", type.stringType()).setWidth(12);
        maxColumn.setHorizontalTextAlignment(HorizontalTextAlignment.RIGHT);


        report
                = report()//create new report design
                .setColumnTitleStyle(columnTitleStyle)
                .setSubtotalStyle(boldStyle)
                .highlightDetailEvenRows()
                //                    .detailRowHighlighters(
                //                            condition1)
                .columns(//add columns
                        itemColumn, nbNullColumn, nbDistinctColumn, nbLinesColumn, minColumn, maxColumn
                //, problematicColumn
                )
                //.groupBy(itemGroup)
                //                    .subtotalsAtSummary(
                //                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
                //                    .subtotalsAtFirstGroupFooter(
                //                            sbt.sum(nbDistinctColumn), sbt.sum(nbLinesColumn))

                .title(//shows report title
                        cmp.horizontalList()
                        .add(
                                cmp.image(getClass().getResource("bbi.png")).setFixedDimension(160, 120),
                                cmp.text("BBI Profiling results" + titleTimeStamp).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.JUSTIFIED),
                                cmp.text(table.getName()).setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.RIGHT))
                        .newRow()
                        .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen2Point())).setFixedHeight(10)))
                .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer
                //.setDataSource(createDataSource(TablesFactory.getTables().get(0)))
                .setDataSource(createDataSource(table.getSchema(), table.getName()));

        if (toPdf) {
            try {
                getReport().toPdf(fos);
                String attached = EmailOptions.getFileDirectory() + fileName;
                if (EmailOptions.isOn()){
                	Platform.runLater(() -> {

	                    ReportingEMail mail = new ReportingEMail(attached, fileName);
	                    mail.send();
                	});
                }
            } catch (DRException ex) {
                Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                return getReport();


    //        if (EmailOptions.isOn()){
        //            Platform.runLater(() -> {
        //                Mail.ReportingEMail mail = new ReportingEMail(fileName);
        //                mail.send();
        //                String args[] = new String[3];
        //                args[0] = fileName;
        //                MailTest.main(args);
        //            });
        //        }
        //            
    }

    private JRDataSource createDataSource(String dbName, String tableName) {

        DRDataSource dataSource = new DRDataSource("column", "nbNull", "nbRows", "nbDistinct", "min", "max");
        Connection con = null;
        PreparedStatement stmt = MetaDataConnector.lastResultsQuery(dbName, tableName);
        try {
            con = stmt.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs;
        try {
            rs = stmt.executeQuery();
            while (rs.next()) {
                dataSource.add(
                        rs.getString("column_name"),
                        rs.getInt("nbnull"),
                        rs.getInt("count"),
                        rs.getInt("nb_distinct"),
                        rs.getString("min"),
                        rs.getString("max")
                );
                System.out.println(rs.getString("column_name") + " "
                        + rs.getInt("nbnull") + " "
                        + rs.getInt("count") + " "
                        + rs.getInt("nb_distinct") + " "
                        + rs.getString("min") + " "
                        + rs.getString("max"));
            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
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

    public static JasperConcatenatedReportBuilder getConcatenatedReports(ArrayList<JasperReportBuilder> reports) {
        JasperConcatenatedReportBuilder running = concatenatedReport();
        //casting ArrayList to array as parameter
        //running.concatenate(reports.toArray(new JasperReportBuilder[reports.size()]));
        for (JasperReportBuilder report : reports) {
            running.concatenate(report);
            System.out.println("Report : " + report);
        }
        return running;
    }
//    
//    public static void saveConcatenatedReports(ArrayList<JasperReportBuilder> reports){
//        try {
//            JasperConcatenatedReportBuilder report = getConcatenatedReports(reports);
//            System.out.println("REPORT : " + report.toString());
//            FileOutputStream fos = null;
//            String saveTo = EmailOptions.getFileDirectory() + "testConcat.pdf";
//            try {
//                fos = new FileOutputStream(saveTo);
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            report.toPdf(fos);
//        } catch (DRException ex) {
//            Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
