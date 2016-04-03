/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import com.jfoenix.controls.JFXListView;
import fxmltest.computing.ProfilingScheduler;
import fxmltest.computing.ProfilingService;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Ashraf
 */
public class ScheduleTasksDisplay extends StackPane{
    
    public ScheduleTasksDisplay(ProfilingScheduler scheduler){
        super();
        ScheduleTasksListView listView = new ScheduleTasksListView(scheduler);
        getChildren().add(listView);
        setId("jfx-dialog-layout");
        setMinSize(200, 400);
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
            getItems().add(new Label(service.toString()));
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

        
    }
}
