/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Ashraf
 */
public class TableTreeItem extends AbstractTreeItem{
    private String tableName;

    public TableTreeItem(String tableName) {
        super();
        this.tableName = tableName;
        this.setValue(tableName);
    }

    
    @Override
    public ContextMenu getMenu() {
        MenuItem profileColumn = new MenuItem("ProfileColumn");
        
        profileColumn.setOnAction(new EventHandler() {
            public void handle(Event t) {
            //    BoxTreeItem newBox = new BoxTreeItem("inbox");
          //      getChildren().add(newBox);
            }
        });
        return new ContextMenu(profileColumn);    
    }
    
    
    
}
