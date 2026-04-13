package canvas.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import canvas.core.Port.PortType;

public class RectObject extends Shape {
    
    public RectObject(int x, int y,  int depth) {
        this.x = x - minWidth/2;
        this.y = y - minHeight/2;
        this.width = minWidth;
        this.height = minHeight;
        this.labelName = ""; // 預設名稱
        createPorts();
    }

    @Override
    public void draw(Graphics g) {
        // 1. 畫出矩形主體
    	g.setColor(bgColor);
    	g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        // 2. 畫出文字標籤 (置中計算)
        if (labelName != null) {
            int stringWidth = g.getFontMetrics().stringWidth(labelName);
            int stringHeight = g.getFontMetrics().getAscent();
            g.drawString(labelName, x + (width - stringWidth) / 2, y + (height + stringHeight) / 2);
        }

        // 3. 如果被選取，則畫出 8 個連接點 (Ports)
        if (isSelected) {
            drawPorts(g);
        }
    }

    @Override
    public boolean isInside(Point p) {
        // 使用 Java 內建的 Rectangle 類別來判斷點擊最方便
        return new Rectangle(x, y, width, height).contains(p);
    }

    

    @Override
	public void createPorts() {
        ports.clear();
        // 這裡可以根據你的需求，輕鬆加入 8 個點
        // 四個角
        ports.add(new Port(this, PortType.TOP_LEFT));             // 左上
        ports.add(new Port(this, PortType.TOP_RIGHT));        // 右上
        ports.add(new Port(this, PortType.BOTTOM_LEFT));       // 左下
        ports.add(new Port(this, PortType.BOTTOM_RIGHT));   // 右下
        // 四個邊中點
        ports.add(new Port(this, PortType.TOP));    // 中上
        ports.add(new Port(this, PortType.BOTTOM));// 中下
        ports.add(new Port(this, PortType.LEFT));   // 左中
        ports.add(new Port(this, PortType.RIGHT));// 右中
    }
    
    
    
    
}