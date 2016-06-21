/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import Entities.ColumnInfo;
import Entities.TableInfo;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Ashraf
 */
public class ColumnsList extends JFXListView{
    
    public ColumnsList(TableInfo table){
        super();
        ObservableList<String> names = FXCollections.observableArrayList();
        for (ColumnInfo col: table.getColumns()){
            names.add(col.getName());
        }
        this.setItems(names);
        this.setWidth(100);
    }
    
}
