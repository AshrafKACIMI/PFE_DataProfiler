/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import com.jfoenix.controls.JFXListView;
import fxmltest.FXMLDocumentController;
import fxmltest.computing.ProfilingScheduler;
import fxmltest.computing.ProfilingService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Ashraf
 */
public class ScheduleTasksDisplay extends StackPane{
    
    private ScheduleTasksListView listView;
    public ScheduleTasksDisplay(ProfilingScheduler scheduler){
        super();
        this.listView = new ScheduleTasksListView(scheduler);
        getChildren().add(listView);
        setId("jfx-dialog-layout");
        setMinSize(200, 400);
        setFocusTraversable(true);
        Button btn = new Button("Delete");
        getChildren().add(btn);
        btn.setFocusTraversable(false);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                removeSelectedTasks();
            }
        });
        this.setOnKeyPressed(new EventHandler<KeyEvent>(){
                
              @Override
              public void handle( final KeyEvent keyEvent ){
                  System.out.println("KEY PRESSED: " + keyEvent.getCode());
                  removeSelectedTasks();
              }
        });
        
    }
    
    public void removeSelectedTask(){
        this.listView.removeTask(this.listView.getSelecteIndex());
    }
    
    public void removeSelectedTasks(){
        final ObservableList<Integer> selectedIndexes = listView.getSelectionModel().getSelectedIndices();
                
                if ( selectedIndexes != null ){
                    System.out.println("DELETE KEY PRESSED");
                    //Delete or whatever you like:
                    for (int i = 0; i < selectedIndexes.size(); i++){
                        System.out.println("row to delete: " + i);
                        int index = selectedIndexes.get(i);
                        if (selectedIndexes.contains(i+1))
                            i--;
                        listView.removeTask(index);
                        
                    }
                }
    }    
            
    private class ScheduleTasksListView extends JFXListView<Label>{
        private int width;
        private int height;
        
    public ScheduleTasksListView(ProfilingScheduler scheduler){
        super();
        for (ProfilingService service: scheduler.getTasks()){
            Label label = new Label(service.toString());
            this.width = Integer.max((int) label.getWidth(), width);
            this.height += 20; 
            getItems().add(label);
            getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            this.setOnKeyTyped(new EventHandler<KeyEvent>(){
                
              @Override
              public void handle( final KeyEvent keyEvent )
              {
                  System.out.println(keyEvent.getCode());
                final ObservableList<Integer> selectedIndexes = getSelectionModel().getSelectedIndices();
                
                if ( selectedIndexes != null ){
                    if ( keyEvent.getCode()==( KeyCode.DELETE ) )
                    {
                        System.out.println("DELETE KEY PRESSED");
                      //Delete or whatever you like:
                        for (int i = 0; i < selectedIndexes.size(); i++){
                            System.out.println("row to delete: " + i);
                            int index = selectedIndexes.get(i);
                            removeTask(index);
                        }
                    }
                }
              }
            } );
            //this.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));

        }
        setMinSize(200, 400);
    }
    /**
         * @return the width
         */
        public int getWidthValue() {
            return width;
        }

        /**
         * @return the height
         */
        public int getHeightValue() {
            return height;
        }
        
        public void removeTask(int i){
            this.getItems().remove(i);
            FXMLDocumentController.getScheduler().removeTask(i);
        }
        
        public int getSelecteIndex(){
            return getSelectionModel().getSelectedIndex();
        }

        
    }
}
