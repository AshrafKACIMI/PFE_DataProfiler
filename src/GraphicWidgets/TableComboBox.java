/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import com.jfoenix.controls.JFXComboBox;
import fxmltest.FXMLDocumentController;
import Entities.TableInfo;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;

/**
 *
 * @author Ashraf
 */
public class TableComboBox extends JFXComboBox<Label> {
    private ObservableList<String> data;
    
    public TableComboBox(ArrayList<TableInfo> tables, int side){
        super();
        setPrefWidth(300);

        //setItems(data);
        //data = FXCollections.observableArrayList();
        for (TableInfo table: tables)
            getItems().add(new Label(table.getName()));
        
        valueProperty().addListener(new ChangeListener<Label>() {

            @Override
            public void changed(ObservableValue<? extends Label> observable, Label oldValue, Label newValue) {
                FXMLDocumentController.getController().resetRefLabel();
                int selected = getSelectionModel().getSelectedIndex();
                if (side == 0)
                    FXMLDocumentController.getParentColumns().update(selected);
                else if (side == 1)
                    FXMLDocumentController.getChildColumns().update(selected);
            }
        });
    }
}
