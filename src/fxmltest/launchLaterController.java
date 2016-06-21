/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import GraphicWidgets.RegexFieldValidator;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 *
 * @author Ashraf
 */
public class launchLaterController implements Initializable {

    @FXML
    private JFXDatePicker datePicker;
    
    @FXML
    private JFXTextField timeField;
    @FXML
    private DatePicker date;
    @FXML
    private DatePicker hour;
            
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        LocalDate date = datePicker.getValue();
        RegexFieldValidator validator = new RegexFieldValidator(RegexFieldValidator.TIME_PATTERN);
        validator.setMessage("Password Can't be empty");
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"1em",";","error"));
        timeField.getValidators().add(validator);
        timeField.focusedProperty().addListener((o,oldVal,newVal)->{
                if(!newVal) timeField.validate();
        });
        
        date.setValue(LocalDate.now());
        timeField.setText(new SimpleDateFormat("HH:mm")
                    .format(Calendar.getInstance().getTime()));        
        
        
//        datePicker.setDisable(true);
        
    }
    
    @FXML
    private void cancelOnAction(ActionEvent event) {
      FXMLDocumentController.closeLaterDialog();
    }
    
    @FXML
    private void scheduleOnAction(ActionEvent event) {
//        LocalDate date = datePicker.getValue();
//        LocalDate hour = hourPicker.getValue();

//        System.out.println(datePicker.getValue());
//        System.out.println(hourPicker.getValue());
//        
        if (timeField.validate()) {

            LocalDate day = LocalDate.of(2016, Month.MAY, 17);
            LocalTime time = LocalTime.of(19, 06, 10);
            //LocalDateTime date = LocalDateTime.of(day, time);
            String timeStr = timeField.getText();
            String[] values = timeStr.split(":");

            int yy = date.getValue().getYear();
            Month mm = date.getValue().getMonth();
            int dd = date.getValue().getDayOfMonth();
            int hh = Integer.parseInt(values[0]);
            int mn = Integer.parseInt(values[1]);

            LocalDateTime exeDate = LocalDateTime.of(yy, mm, dd, hh, mn);
            System.out.println(exeDate);
            FXMLDocumentController.getScheduler().delay(exeDate);
            //FXMLDocumentController.closeLoginDialog();
        }
    }
    
//    public static void setLabel(String value){
//        taskLabel.setVisible(true);
//        taskLabel.setText(value);
//    }
    
    
    
}
