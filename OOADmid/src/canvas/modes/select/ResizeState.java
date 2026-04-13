package canvas.modes.select;

import canvas.controller.EditorController;
import canvas.core.Port;
import canvas.core.Shape;
import canvas.ui.CanvasPanel;
import canvas.modes.SelectMode;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class ResizeState implements SelectState {

    private SelectMode context;
    private CanvasPanel canvas;
    private EditorController controller;

    private Shape target;
    private Port port;
    private Point anchor;

    public ResizeState(SelectMode context, CanvasPanel canvas,
                       EditorController controller,
                       Shape target, Port port, Point startPoint) {
        this.context = context;
        this.canvas = canvas;
        this.controller = controller;
        this.target = target;
        this.port = port;
        this.anchor = computeAnchor(target, port);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point p = e.getPoint();

        Point adjusted = new Point(p);

        switch (port.getType()) {

        case TOP:
        case BOTTOM:
            adjusted.x = anchor.x;  // 固定 X
            break;

        case LEFT:
        case RIGHT:
            adjusted.y = anchor.y;  // 固定 Y
            break;

        default:
            break;
        }

        target.resize(port, adjusted, anchor);

        canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        context.setState(new IdleState(context, canvas, controller));
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    
    private Point computeAnchor(Shape s, Port port) {

        int left = s.x;
        int right = s.x + s.width;
        int top = s.y;
        int bottom = s.y + s.height;

        switch (port.getType()) {

            case TOP_LEFT: return new Point(right, bottom);
            case TOP_RIGHT: return new Point(left, bottom);
            case BOTTOM_LEFT: return new Point(right, top);
            case BOTTOM_RIGHT: return new Point(left, top);

            // 中間點 → 用整條邊的「對邊」
            case TOP: return new Point(left, bottom);
            case BOTTOM: return new Point(left, top);
            case LEFT: return new Point(right, top);
            case RIGHT: return new Point(left, top);
        }

        return null;
    }
}