package canvas.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Port {
    private Shape parent;
    private final int PORT_SIZE = 15;
    private int x, y;
    private PortType type;

    public enum PortType {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT
    }

    public Port(Shape parent, PortType type) {
        this.parent = parent;
        this.type = type;
        updateLocation();  // 初始化位置
    }

    // 每次畫圖或 shape resize/move 時呼叫
    public void updateLocation() {
        switch(type) {
            case TOP_LEFT:       x = parent.x;               y = parent.y; break;
            case TOP_RIGHT:      x = parent.x + parent.width; y = parent.y; break;
            case BOTTOM_LEFT:    x = parent.x;               y = parent.y + parent.height; break;
            case BOTTOM_RIGHT:   x = parent.x + parent.width; y = parent.y + parent.height; break;
            case TOP:            x = parent.x + parent.width/2; y = parent.y; break;
            case BOTTOM:         x = parent.x + parent.width/2; y = parent.y + parent.height; break;
            case LEFT:           x = parent.x;               y = parent.y + parent.height/2; break;
            case RIGHT:          x = parent.x + parent.width; y = parent.y + parent.height/2; break;
        }
    }

    public Point getAbsoluteLocation() {
        return new Point(x, y);
    }

    public void drawPort(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x - PORT_SIZE/2, y - PORT_SIZE/2, PORT_SIZE, PORT_SIZE);
    }

    public boolean isInside(Point p) {
        return new Rectangle(x - PORT_SIZE/2, y - PORT_SIZE/2, PORT_SIZE, PORT_SIZE).contains(p);
    }

    public PortType getType() { return type; }
    public Shape getParent() { return parent; }
}