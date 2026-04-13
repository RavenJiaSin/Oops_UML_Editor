package canvas.core;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class GeneralizationLine extends LineObject {

    public GeneralizationLine(Port from, Port to) {
        super(from, to); // 直接使用 LineObject 的建構子
    }

    @Override
    protected void drawArrow(Graphics g, Point p1, Point p2) {
        // 1. 設定三角形的大小與角度
        int arrowSize = 12; // 三角形邊長
        double angle = Math.atan2(p2.y - p1.y, p2.x - p1.x); // 計算連線的絕對角度

        // 2. 計算三角形的另外兩個頂點 (利用極座標轉換)
        // 角度偏移 30 度 (PI / 6)
        int x2 = (int) (p2.x - arrowSize * Math.cos(angle - Math.PI / 6));
        int y2 = (int) (p2.y - arrowSize * Math.sin(angle - Math.PI / 6));
        
        int x3 = (int) (p2.x - arrowSize * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (p2.y - arrowSize * Math.sin(angle + Math.PI / 6));

        // 3. 建立多邊形物件
        Polygon triangle = new Polygon();
        triangle.addPoint(p2.x, p2.y); // 頂點 (接在 Port 上)
        triangle.addPoint(x2, y2);
        triangle.addPoint(x3, y3);

        // 4. 繪製空心三角形 (UML 規範：背景白，邊框黑)
        g.setColor(Color.WHITE);
        g.fillPolygon(triangle); // 填滿內部以覆蓋下方的線條
        
        g.setColor(Color.BLACK);
        g.drawPolygon(triangle); // 畫出黑色邊框
    }
}