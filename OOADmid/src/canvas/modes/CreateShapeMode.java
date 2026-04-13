package canvas.modes;

import canvas.core.Shape;
import canvas.core.RectObject;
import canvas.controller.EditorController;
import canvas.core.OvalObject;
import canvas.ui.CanvasPanel;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class CreateShapeMode implements Mode {
    private String type;
    private CanvasPanel canvas;
    private EditorController controller;
    

    public CreateShapeMode(String type, CanvasPanel canvas, EditorController controller) {
        this.type = type;
        this.canvas = canvas;
        this.controller = controller;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // 座標轉換：從「按鈕」轉換到「畫布」
        Point p = e.getPoint();
        SwingUtilities.convertPointToScreen(p, e.getComponent()); // e.getComponent() 此時是按鈕
        SwingUtilities.convertPointFromScreen(p, canvas);

        // 檢查是否放開在畫布範圍內
        if (canvas.contains(p)) {
            // 根據 type 產生物件
            Shape newShape = null;
            if (type.equals("Rect")) {
                newShape = new RectObject(p.x, p.y, 0);
            } else if (type.equals("Oval")) {
                newShape = new OvalObject(p.x, p.y, 0);
            }

            if (newShape != null) {
            	controller.addShape(newShape);
            	canvas.repaint();
            }
        }     
    }

    // 如果想做預覽，可以在這裡實作轉換並呼叫 canvas 重繪
    @Override 
    public void mouseDragged(MouseEvent e) {
        // 這裡可以實作拖曳時的虛線預覽
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
}