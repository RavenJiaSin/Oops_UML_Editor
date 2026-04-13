package canvas.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

import canvas.core.Port.PortType;

public class OvalObject extends Shape {

    public OvalObject(int x, int y,  int depth) {
        this.x = x - minWidth/2;
        this.y = y - minHeight/2;
        this.width = minWidth;
        this.height = minHeight;
        this.labelName = ""; // 預設名稱
        createPorts();
    }

    @Override
    public void draw(Graphics g) {
        // 1. 畫出橢圓主體
    	g.setColor(bgColor);
    	g.fillOval(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);

        // 2. 畫出文字標籤 (置中計算)
        if (labelName != null) {
            int stringWidth = g.getFontMetrics().stringWidth(labelName);
            int stringHeight = g.getFontMetrics().getAscent();
            g.drawString(labelName, x + (width - stringWidth) / 2, y + (height + stringHeight) / 2);
        }

        // 3. 如果被選取，則畫出 4 個連接點 (Ports)
        if (isSelected) {
            drawPorts(g);
        }
    }

    @Override
    public boolean isInside(Point p) {
    	// 使用 Ellipse2D.Double 來建立幾何形狀，並呼叫 contains 判定點擊
        return new Ellipse2D.Double(x, y, width, height).contains(p);
    }

    @Override
	public void createPorts() {
        ports.clear();
        // 四個邊中點
        ports.add(new Port(this, PortType.TOP));    // 中上
        ports.add(new Port(this, PortType.BOTTOM));// 中下
        ports.add(new Port(this, PortType.LEFT));   // 左中
        ports.add(new Port(this, PortType.RIGHT));// 右中
    }
    
    

    
}