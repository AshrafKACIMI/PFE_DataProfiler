/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reporting;

import Mail.ReportingEMail;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.TableInfo;
import fxmltest.computing.TablesFactory;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.dynamicreports.examples.Templates;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.PercentageColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.ConditionalStyleBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Ashraf
 */
public class TableReport {
    private static int count = 1;

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
        TextColumnBuilder<String> maxColumn = col.column("Max", "max", type.stringType());
        TextColumnBuilder<Integer> problematicColumn = col.column("Problematic", "problematic", type.integerType());
        
        
        ConditionalStyleBuilder condition1 = stl.conditionalStyle(cnd.greater(problematicColumn, 0))
                                              .setBackgroundColor(new Color(210, 255, 210));
            
        
        TextColumnBuilder<Integer> rowNumberColumn = col.reportRowNumberColumn("No.")
                //sets the fixed width of a column, width = 2 * character width
                .setFixedColumns(2)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

                ColumnGroupBuilder itemGroup = grp.group(itemColumn);
        itemGroup.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(itemGroup));
        
        
        
        try {
            report()//create new report design
                    .setColumnTitleStyle(columnTitleStyle)
                    .setSubtotalStyle(boldStyle)
                    .highlightDetailEvenRows()
//                    .detailRowHighlighters(
//                    condition1)
                    .columns(//add columns
                            itemColumn, nbNullColumn, nbNotNullColumn, nbLinesColumn, minColumn, maxColumn, problematicColumn)
                    //.groupBy(itemGroup)
                    .subtotalsAtSummary(
                            sbt.sum(nbNullColumn), sbt.sum(nbLinesColumn))
                    .subtotalsAtFirstGroupFooter(
                            sbt.sum(nbNotNullColumn), sbt.sum(nbLinesColumn))

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
        
        DRDataSource dataSource = new DRDataSource("column", "nbNull", "nbNotNull", "nbRows", "min", "max", "problem");
        //String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max
        for (ColumnInfo ci: tab.getColumns()){
            dataSource.add(
                    ci.getStats().getColumnName(),
                    ci.getStats().getNbNull(),
                    ci.getStats().getNbDistinct(),
                    ci.getStats().getNbLines(),
                    ci.getStats().getMin(),
                    ci.getStats().getMax(),
                    ci.getStats().getViolNotNullFlag()
                    );            
        }
        
        System.out.println(dataSource);
        return dataSource;
    }

}
