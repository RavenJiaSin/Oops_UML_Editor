package canvas.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


public abstract class Shape {
    public int x, y, width, height;
    public int minWidth = 100;
    public int minHeight = 60;
    protected String labelName = ""; // 用於自定義標籤 [cite: 130]
    protected Color bgColor = Color.WHITE;
    protected boolean isSelected = false;
    
    protected List<Port> ports = new ArrayList<>();
    protected List<LineObject> lines = new ArrayList<>();
    
    // 每個圖形都要實作自己的繪製方式
    public abstract void draw(Graphics g);
    // 判斷滑鼠是否點中此物件 [cite: 102]
    public abstract boolean isInside(Point p);
    public abstract void createPorts(); 
    public void updatePorts() {
        for (Port p : ports) {
            p.updateLocation();
        }
    }
    // 當被選取時，繪製出 4 或 8 個 ports [cite: 46, 62]
    public void drawPorts(Graphics g) {
        g.setColor(Color.BLACK);
        for (Port p : ports) {
        	// 繪製以該點為中心的填充小方塊
            p.drawPort(g);
        }
    }  
    public Port getNearestPort(Point p) {
        Port nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Port port : ports) {
            // 呼叫 Port 自己的方法來取得它在畫布上的絕對座標
            Point portLoc = port.getAbsoluteLocation();
            double dist = p.distance(portLoc);

            if (dist < minDistance) {
                minDistance = dist;
                nearest = port;
            }
        }
        return nearest;
    }   
       
    // Getter & Setter (處理移動與深度排序)
    public void setLocation(int x, int y) { this.x = x; this.y = y; }
    public Point getCenter() {
    	Point center = new Point(x + width/2, y - height/2);
    	return center;
    }
    public void move(int dx, int dy) {
        this.x += dx; // 在原本的 x 上加上位移
        this.y += dy;
        updatePorts();
    }
    public Port getPortAt(Point p) {
        for (Port port : ports) {
            if (port.isInside(p)) return port;
        }
        return null;
    }
    public void resize(Port port, Point p, Point anchor) {

        int newX = x;
        int newY = y;
        int newWidth = width;
        int newHeight = height;

        switch (port.getType()) {

            case TOP_LEFT:
            case TOP_RIGHT:
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
                // 四角：兩個維度都計算
                newX = Math.min(anchor.x, p.x);
                newY = Math.min(anchor.y, p.y);
                newWidth = Math.abs(anchor.x - p.x);
                newHeight = Math.abs(anchor.y - p.y);

                newWidth = Math.max(minWidth, newWidth);
                newHeight = Math.max(minHeight, newHeight);

                // 重新調整 x/y
                if (p.x < anchor.x) newX = anchor.x - newWidth;
                if (p.y < anchor.y) newY = anchor.y - newHeight;
                break;

            case TOP:
            case BOTTOM:
                // 垂直方向調整高度，寬度不變
                newHeight = Math.abs(anchor.y - p.y);
                newHeight = Math.max(minHeight, newHeight);

                if (p.y < anchor.y) newY = anchor.y - newHeight;
                else newY = anchor.y;
                break;

            case LEFT:
            case RIGHT:
                // 水平方向調整寬度，高度不變
                newWidth = Math.abs(anchor.x - p.x);
                newWidth = Math.max(minWidth, newWidth);

                if (p.x < anchor.x) newX = anchor.x - newWidth;
                else newX = anchor.x;
                break;
        }

        x = newX;
        y = newY;
        width = newWidth;
        height = newHeight;

        updatePorts();
    }
    
    public void addConnectedLine(LineObject line) {
        if (!lines.contains(line)) {
            lines.add(line);
        }
    }

    public void removeConnectedLine(LineObject line) {
        lines.remove(line);
    }
    
    
    
    
    
    public void setSelected(boolean b) { this.isSelected = b; }
    public boolean isSelected() { return this.isSelected; }
    public void setLabelName(String s) { this.labelName = s;}
    public String getLabelName() { return labelName;}
    public void setBgColor(Color color) { this.bgColor = color; }
    public Color getBgColor() { return bgColor; }
    public List<Port> getPorts() { return ports; }
    public List<LineObject> getLines() { return lines; }
	
}