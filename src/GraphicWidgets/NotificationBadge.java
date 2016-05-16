/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXPopup;
import de.jensd.fx.fontawesome.Icon;
import fxmltest.FXMLDocumentController;
import fxmltest.DataProfilerPFE;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Ashraf
 */
public class NotificationBadge extends JFXBadge{
    public NotificationBadge(String iconName){
        super();
        Icon value = new Icon(iconName);
        value.setId("icon");
        setControl(value);
        setPosition(Pos.TOP_RIGHT);
        setText("0");
        setId("icons-badge");
        
        setPrefSize(50, 50);
        setOnMouseClicked((e) -> {
            StackPane root = (StackPane) DataProfilerPFE.getRoot();
            StackPane content = new ScheduleTasksDisplay(
                    FXMLDocumentController.getScheduler());
            JFXPopup popup = new JFXPopup(root, content);
            popup.setSource(this);
            popup.show(JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
        });
    }
}
