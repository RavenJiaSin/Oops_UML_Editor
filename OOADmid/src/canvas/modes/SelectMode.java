package canvas.modes;

import canvas.ui.CanvasPanel;
import canvas.controller.EditorController;
import canvas.modes.select.*;

import java.awt.event.MouseEvent;

public class SelectMode implements Mode {
	private SelectState currentState;

    public SelectMode(CanvasPanel canvas, EditorController controller) {
        this.currentState = new IdleState(this, canvas, controller);
    }

    public void setState(SelectState state) {
        this.currentState = state;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentState.mousePressed(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentState.mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentState.mouseReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
}