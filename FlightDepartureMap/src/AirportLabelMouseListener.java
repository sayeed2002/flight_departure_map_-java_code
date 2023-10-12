import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class AirportLabelMouseListener extends MouseAdapter {
    private MAPDEPT gui;
    private int delayThreshold;
    

    public AirportLabelMouseListener() {
        this.gui = gui;
        this.delayThreshold = delayThreshold;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            JLabel label = (JLabel) e.getSource();
            String airportCode = label.getText();
            gui.updateTableForAirport(airportCode);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { 
            if (e.getSource() instanceof JLabel) {
                JLabel label = (JLabel) e.getSource();
                String airportCode = label.getText();
                gui.updateTableForDelayedFlights(airportCode, 30);
              

               
               
            }
        }
    }
}
