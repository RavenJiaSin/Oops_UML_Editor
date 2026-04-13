package canvas.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class LineObject {
    protected Port fromPort;
    protected Port toPort;

    public LineObject(Port from, Port to) {
        this.fromPort = from;
        this.toPort = to;
    }

    
    public void draw(Graphics g) {
        Point p1 = fromPort.getAbsoluteLocation();
        Point p2 = toPort.getAbsoluteLocation();

        g.setColor(Color.BLACK);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        
        // 子類別畫箭頭
        drawArrow(g, p1, p2);
    }

    protected void drawArrow(Graphics g, Point p1, Point p2) {
        // 預設是一條簡單的線，子類別再覆寫此方法畫箭頭
    }
}