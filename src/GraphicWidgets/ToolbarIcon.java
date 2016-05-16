/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXRippler;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;

/**
 *
 * @author Ashraf
 */
public class ToolbarIcon extends JFXRippler{
    
    public ToolbarIcon(AwesomeIcon icon){
        super();
        setControl(new Icon(icon, "30px", "-fx-text-fill: #FFFFF7;", "icon"));
        setStyle("-fx-mask-type: CIRCLE;");
        setPostion(JFXRippler.RipplerPos.BACK);
        setId("icons-rippler");        
    }
    
    public ToolbarIcon(JFXBadge badge){
        super();
        setControl(badge);
        setStyle("-fx-mask-type: CIRCLE;");
        setPostion(JFXRippler.RipplerPos.BACK);
        setId("icons-rippler");        
    }
}
