/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphicWidgets;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ProgressIndicator;

/**
 *
 * @author Ashraf
 */
public class ProgressIndicatorGraph extends ProgressIndicator{
    
    public ProgressIndicatorGraph(float progressValue){
        super();
        this.setProgress(progressValue);
        this.styleProperty().bind(Bindings.createStringBinding(
            () -> {
                final double percent = this.getProgress();
                if (percent < 0) return null; // progress bar went indeterminate
                final double m = (2d * percent);
                final int n = (int) m;
                final double f = m - n;
                final int t = (int) (255 * f);
                int r = 0, g = 0, b = 0;

                switch (n) {
                    case 0:
                        r = 255;
                        g = t;
                        b = 0;
                        break;

                    case 1:
                        r = 255 - t;
                        g = 255;
                        b = 0;
                        break;

                    case 2:
                        r = 0;
                        g = 255;
                        b = 0;
                        break;

                }

                final String style = String.format("-fx-progress-color: rgb(%d,%d,%d)", r, g, b);

                return style;

            },

            this.progressProperty()

        ));
    }
    
        public ProgressIndicatorGraph(float progressValue, int width, int height){
            this(progressValue);
            this.setPrefSize(width, height);
            this.setMinSize(width, height);
            this.setMaxSize(width, height);
        }

    
}
