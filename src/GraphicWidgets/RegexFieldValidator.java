/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import com.jfoenix.validation.RequiredFieldValidator;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Ashraf
 */
public class RegexFieldValidator extends RequiredFieldValidator{
    private String regex;
    
    public static final String IPADDRESS_PATTERN = 
		"(^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$)";
    
    public static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    
    public RegexFieldValidator(String regex){
        super();
        this.regex = regex;
    }
    
    @Override
	protected void eval() {
		if(srcControl.get() instanceof TextInputControl)
			evalTextInputField();
	}
	
    private void evalTextInputField(){
        TextInputControl textField = (TextInputControl) srcControl.get();
        
        if (regex.equals(IPADDRESS_PATTERN) && textField.getText().equals("localhost"))
            hasErrors.set(false);

        else if (textField.getText() == null || textField.getText().equals("")){
            hasErrors.set(true);
            setMessage("This field can't be empty");
        }
        else if (!textField.getText().matches(regex)){
            hasErrors.set(true);
            setMessage("Invalid value");
        }
        else{
            hasErrors.set(false);
        }
    }
    
}
