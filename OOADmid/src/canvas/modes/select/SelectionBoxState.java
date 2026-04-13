package canvas.modes.select;

import canvas.ui.CanvasPanel;
import canvas.controller.EditorController;
import canvas.modes.SelectMode;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectionBoxState implements SelectState {
    private SelectMode context;
    private CanvasPanel canvas;
    private Point start;
    private Point current;
    private EditorController controller;

    public SelectionBoxState(SelectMode context,
            CanvasPanel canvas,
            EditorController controller,
            Point start) {
        this.context = context;
        this.canvas = canvas;
        this.controller = controller;
        this.start = start;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        current = e.getPoint();

        int x = Math.min(start.x, current.x);
        int y = Math.min(start.y, current.y);
        int w = Math.abs(start.x - current.x);
        int h = Math.abs(start.y - current.y);

        canvas.setSelectionRect(new Rectangle(x, y, w, h));
        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (current != null) {
            int x = Math.min(start.x, current.x);
            int y = Math.min(start.y, current.y);
            int w = Math.abs(start.x - current.x);
            int h = Math.abs(start.y - current.y);

            Rectangle r = new Rectangle(x, y, w, h);
            controller.selectShapesInArea(r);
        }

        canvas.setSelectionRect(null);
        context.setState(new IdleState(context, canvas, controller));
        canvas.repaint();
    }

    public void mousePressed(MouseEvent e) {}
}