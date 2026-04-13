package canvas.modes;

import java.awt.event.MouseEvent;

public interface Mode{
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseDragged(MouseEvent e);
	public void mouseClicked(MouseEvent e);
}


