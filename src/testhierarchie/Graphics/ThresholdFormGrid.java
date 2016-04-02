/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import fxmltest.computing.ColumnInfo;
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
        content = new VBox(20);
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
        content.getChildren().add(new Label("Input min and max values"));
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
 
    
    
