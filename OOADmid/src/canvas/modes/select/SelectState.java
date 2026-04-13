package canvas.modes.select;

import java.awt.event.MouseEvent;

public interface SelectState {
    void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseReleased(MouseEvent e);
}