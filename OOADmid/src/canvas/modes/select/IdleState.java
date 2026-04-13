package canvas.modes.select;

import canvas.controller.EditorController;
import canvas.core.Port;
import canvas.core.Shape;
import canvas.ui.CanvasPanel;
import canvas.modes.SelectMode;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class IdleState implements SelectState {
    private SelectMode context;
    private CanvasPanel canvas;
    private EditorController controller;

    public IdleState(SelectMode context, CanvasPanel canvas, EditorController controller) {
        this.context = context;
        this.canvas = canvas;
        this.controller = controller;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Port port = controller.getPortAt(p);

        if (port != null) {
        	Shape s = port.getParent();   
            context.setState(new ResizeState(context, canvas, controller, s, port, p));
        }else {
            Shape s = controller.getShapeAt(p);

            if (s != null) {
                controller.unselectAll();
                s.setSelected(true);
                controller.moveShapeToFront(s);

                context.setState(new DragShapeState(context, canvas, controller, s, p));
            } else {
                controller.unselectAll();
                context.setState(new SelectionBoxState(context, canvas, controller, p));
            }
        }

        canvas.repaint();
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}