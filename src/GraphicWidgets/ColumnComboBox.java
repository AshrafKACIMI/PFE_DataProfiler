/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import com.jfoenix.controls.JFXComboBox;
import fxmltest.FXMLDocumentController;
import Entities.ColumnInfo;
import Entities.TableInfo;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ashraf
 */
public class ColumnComboBox extends JFXComboBox<String>{
    private ObservableList<String> data;
    
    public ColumnComboBox(){
        super();
        setPrefWidth(300);
//            valueProperty().addListener(new ChangeListener<Label>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
//                FXMLDocumentController.getController().resetRefLabel();
//
//            }
//        });
        
    }
    
    public ColumnComboBox(TableInfo table){
        super();
        data = FXCollections.observableArrayList();
        
        for (ColumnInfo column: table.getColumns())
            data.add(column.getName());
    }
    
    
    public void update(int tableNumber){
        TableInfo table = FXMLDocumentController.getTables().get(tableNumber);
        getItems().clear();
        for (ColumnInfo column: table.getColumns())
            getItems().add(column.getName());
    }
    
    public String getSelectedColumn(){
        return getValue();
    }
    

    
    
    
}
