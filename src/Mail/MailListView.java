/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mail;

import com.jfoenix.controls.JFXListView;
import fxmltest.FXMLDocumentController;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;

/**
 *
 * @author Ashraf
 */
public class MailListView extends JFXListView<Label>{
    private ArrayList<String> mailList;
    
    public MailListView(ArrayList<String> mailList){
        super();
        setPrefSize(300, 250);
        setMinSize(300, 250);
        setMaxSize(300, 250);
        this.mailList = mailList;
        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for (String mail: mailList){
            Label mailLabel = new Label(mail);
            getItems().add(mailLabel);
        }
    }
    
    public void removeMail(int i){
            this.getItems().remove(i);
    }
    
    public void addMail(String mailAddress){
        this.mailList.add(mailAddress);
        this.getItems().add(new Label(mailAddress));
    }
        
    public int getSelectedIndex(){
        return getSelectionModel().getSelectedIndex();
    }
    
    public void removeSelectedMails(){
        final ObservableList<Integer> selectedIndexes = getSelectionModel().getSelectedIndices();
        
        if ( selectedIndexes != null ){
            System.out.println("DELETE KEY PRESSED");
            //Delete or whatever you like:
            for (int i = 0; i < selectedIndexes.size(); i++){
                System.out.println("row to delete: " + i);
                int index = selectedIndexes.get(i);
                if (selectedIndexes.contains(i+1))
                    i--;
                removeMail(index);
            }
        }
    }
    
    public void clear(){
        this.getItems().clear();
    }

    /**
     * @return the mailList
     */
    public ArrayList<String> getMailList() {
        return mailList;
    }
    
    
    
}
