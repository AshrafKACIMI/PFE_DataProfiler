
package testhierarchie.Graphics;

import fxmltest.computing.BasicStatisticsProfiler;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.TableInfo;
import fxmltest.computing.TablesFactory;


/**
 *
 * @author Ashraf
 */
public class TableTreeView extends TreeView{
    private ArrayList<TableInfo> tables;

    public TableTreeView() {
        super();
        final TreeItem<String> treeRoot = new TreeItem<String>("Mes tables");
        
        this.tables = new TablesFactory().getTables();
        System.out.println(this.tables);
        
       
        for (TableInfo tab: this.tables){
            TreeItem<String> treeTab = new TreeItem<String>(tab.getName());
             ContextMenu rootContextMenu
                = ContextMenuBuilder.create()
                .items(
                    MenuItemBuilder.create()
                    .text("Menu Item")
                    .onAction(
                        new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle(ActionEvent arg0)
                            {
                                BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(tables.get(1));
                                System.out.println(profiler.profileTableQuery());
                            }
                        }
                    )
                    .build()
                )
                .build();
             this.setContextMenu(rootContextMenu);
            for (ColumnInfo col: tab.getColumns()){
                treeTab.getChildren().add(new TreeItem<String>(col.getName()+":"+col.getType()));
            }
            treeRoot.getChildren().add(treeTab);
            
        }
        
        this.setShowRoot(true);
        this.setRoot(treeRoot);
        
    }

     

    /**
     * @return the tables
     */
    public ArrayList<TableInfo> getTables() {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(ArrayList<TableInfo> tables) {
        this.tables = tables;
    }
    
    
    
}
