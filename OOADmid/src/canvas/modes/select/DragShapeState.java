package canvas.modes.select;

import canvas.controller.EditorController;
import canvas.core.Shape;
import canvas.ui.CanvasPanel;
import canvas.modes.SelectMode;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class DragShapeState implements SelectState {
    private SelectMode context;
    private CanvasPanel canvas;
    private Shape target;
    private Point lastPoint;
    private EditorController controller;
    public DragShapeState(SelectMode context, CanvasPanel canvas, EditorController controller, Shape target, Point start) {
        this.context = context;
        this.canvas = canvas;
        this.controller = controller;
        this.target = target;
        this.lastPoint = start;
    }

    

	@Override
    public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();
        int dx = p.x - lastPoint.x;
        int dy = p.y - lastPoint.y;

        target.move(dx, dy);
        lastPoint = p;

        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        context.setState(new IdleState(context, canvas, controller));
    }

    public void mousePressed(MouseEvent e) {}
}