/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import DQRepository.MetaDataConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.IConnector;
import fxmltest.computing.TableInfo;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ashraf
 */
public class ThresholdFormGrid extends StackPane {
    private JFXButton confirmButton;
    private JFXButton cancelButton;
    private VBox content;
    private GridPane test;
    private ArrayList<ColumnLine> columns;
    private TableInfo table;
    
    public ThresholdFormGrid(TableInfo table){
        super();
        this.table = table;
        columns = new ArrayList<ColumnLine>();
        int rowIndex = 0;
        test = new GridPane();
        ColumnConstraints labelCol = new ColumnConstraints(200);
        ColumnConstraints minCol = new ColumnConstraints(130);
        ColumnConstraints maxCol = new ColumnConstraints(130);
        ColumnConstraints distinctCol = new ColumnConstraints(100);
        ColumnConstraints notNullCol = new ColumnConstraints(100);
        
        test.getColumnConstraints().addAll(labelCol, minCol, maxCol, distinctCol, notNullCol);
        
        Label title = new Label("Specify Data Quality Rules");
        title.setId("thresholdtitle");
        GridPane header = new GridPane();
        header.setId("thresholdheader");
        header.getColumnConstraints().addAll(labelCol, new ColumnConstraints(260), distinctCol, notNullCol);
        Label columnName = new Label("Column Name");
        Label validity = new Label("Validity");
        Label uniqueness = new Label("Uniqueness");
        Label completeness = new Label("Completeness");
        
        header.add(columnName, 0, 0, 1, 1);
        header.add(validity, 1, 0, 2, 1);
        header.add(uniqueness, 2, 0, 1, 1);
        header.add(completeness, 3, 0, 1, 1);
        
        
        
        content = new VBox(20);
        content.getChildren().addAll(title, header);
        for (ColumnInfo column : table.getColumns()){
            ColumnLine columnLine = new ColumnLine(column); 
            columns.add(columnLine);
            test.addRow(
                    rowIndex,
                    new Label(column.getName() + " [" + column.getType() + "]"),
                    columnLine.getMin(), 
                    columnLine.getMax(),
                    columnLine.getDistinct(),
                    columnLine.getNotNull()
            );
            rowIndex++;
        }
        confirmButton = new JFXButton("Confirm");
        cancelButton = new JFXButton("Cancel");
        confirmButton.setId("jfx-dialog-button");
        cancelButton.setId("jfx-dialog-button");
        content.getChildren().add(test);
        content.getChildren().add(
                new HBox(100, confirmButton, cancelButton)
            );
        ScrollPane scrollPane = new ScrollPane(content);
        content.setId("jfx-dialog-layout");
        confirmButton.setOnAction((ActionEvent event) -> updateColumnsThresholds());
        this.getChildren().add(scrollPane);
    }
    
    
    private void updateColumnsThresholds(){
        for (ColumnLine column: columns){
            column.getColumnInfo().setMaxConstraint(column.getMaxValue());
            column.getColumnInfo().setMinConstraint(column.getMinValue());
            column.getColumnInfo().setIsUnique(column.getIsDistinct());
            column.getColumnInfo().setIsNotNull(column.getIsNotNull());
            column.getColumnInfo().updateStatsRules();
            
            int distinctInt = (column.getIsDistinct()? 1: 0 );
            int notNullInt = (column.getIsNotNull()? 1: 0 );
            String min = column.getMinValue();
            String max = column.getMaxValue();
            if (min == null)
                min = "";
            if (max == null)
                max = "";            
            
            //writing the rules in the MD DB
            MetaDataConnector.insertRule(table.getSchema(), table.getName(), 
                    column.getColumnInfo().getName(),
                    min, max, distinctInt, notNullInt);
        }
                
    }
    
    
    

    private class ColumnLine extends HBox{
        private JFXTextField min;
        private JFXTextField max;
        private JFXCheckBox distinct;
        private JFXCheckBox notNull;
        private ColumnInfo columnInfo;
        
        private ColumnLine(ColumnInfo column){
            super(20);
            this.columnInfo = column;
            min = new JFXTextField(column.getMinConstraint());
            min.setPromptText("min");
            
            max = new JFXTextField(column.getMaxConstraint());
            max.setPromptText("max");
            
            distinct = new JFXCheckBox("Distinct");
            if (column.isIsUnique()){
                distinct.setSelected(true);
            }
            
            notNull = new JFXCheckBox("Not null");
            if (column.isIsNotNull()){
                notNull.setSelected(true);
            }
            
            
            getChildren().add(new Label(column.getName() + " [" + column.getType() + "]"));
            getChildren().addAll(min, max);
            getChildren().addAll(distinct, notNull);
            
        }
        
        private String getMinValue(){
            return getMin().getText();
        }
        
        private String getMaxValue(){
            return getMax().getText();
        } 

        /**
         * @return the min
         */
        public JFXTextField getMin() {
            return min;
        }

        /**
         * @return the max
         */
        public JFXTextField getMax() {
            return max;
        }

        /**
         * @return the columnInfo
         */
        public ColumnInfo getColumnInfo() {
            return columnInfo;
        }

        /**
         * @param columnInfo the columnInfo to set
         */
        public void setColumnInfo(ColumnInfo columnInfo) {
            this.columnInfo = columnInfo;
        }
        
        public boolean getIsNotNull(){
            return getNotNull().isSelected();
        }
        
        public boolean getIsDistinct(){
            return getDistinct().isSelected();
        }

        /**
         * @return the distinct
         */
        public JFXCheckBox getDistinct() {
            return distinct;
        }

        /**
         * @return the notNull
         */
        public JFXCheckBox getNotNull() {
            return notNull;
        }
        
        
        
    }

}
 
    
    
