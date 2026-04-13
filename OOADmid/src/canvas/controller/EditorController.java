package canvas.controller;

import canvas.core.CompositeObject;
import canvas.core.Port;
import canvas.core.Shape;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class EditorController {
    private List<Shape> shapes = new ArrayList<>();
    
    /**
     * 獲取由 controller 管理的所有 Shape 物件
     * @return shapes (List<Shape>)
     */
    public List<Shape> getShapes() {
        return shapes;
    }
    
    /** 增加一個 shape 物件到 controller 的 shapes (List<Shape>)
     * @param 欲加入的 Shape 物件
     */
    public void addShape(Shape s) {
        shapes.add(s);
    }

    /**
     * 獲得位於點 p 的最淺的 Shape 物件
     * @param p 座標點(Point)
     * @return 一個 Shape 物件 或 null
     */
    public Shape getShapeAt(Point p) {
        for (Shape s : shapes) {
            if (s.isInside(p)) return s;
        }
        return null;
    }
    
    /**
     * 將一個 Shape 物件移動到最淺 (shapes[0])
     * @param s 欲移動的 Shape 物件
     */
    public void moveShapeToFront(Shape s) {
        if (s != null && shapes.contains(s)) {
            shapes.remove(s);
            shapes.add(0, s);
        }
    }

    /**
     * 將 controller 中所有的 Shape 設定為未選中狀態
     */
    public void unselectAll() {
        for (Shape s : shapes) {
            s.setSelected(false);
        }
    }

    /**
     * 傳入一矩形，將位於該矩形範圍中的所有 Shape 物件設定為選中狀態
     * @param r 選取範圍 (Rectangle)
     */
    public void selectShapesInArea(Rectangle r) {
        boolean any = false;

        for (Shape s : shapes) {
            Rectangle sr = new Rectangle(s.x, s.y, s.width, s.height);
            if (r.contains(sr)) {
                s.setSelected(true);
                any = true;
            } else {
                s.setSelected(false);
            }
        }

        if (!any) unselectAll();
    }
    
    /**
     * 修改所有選中的 Shape 物件的標籤與背景顏色
     * @param label 標籤名稱 
     * @param color 背景顏色
     */
    public void updateSelectedStyle(String label, Color color) {
        for (Shape s : shapes) {
            if (s.isSelected()) {
                s.setLabelName(label);
                s.setBgColor(color);
            }
        }
    }
    
    /**
     * 獲取被選中的Shape物件中最淺的一個
     * @return
     */
    public Shape getFirstSelectedShape() {
        for (Shape s : shapes) {
            if (s.isSelected()) return s;
        }
        return null;
    }
    
    /**
     * 取得座標點 p 處的 Port 物件
     * @param p Point
     * @return Port 物件
     */
    public Port getPortAt(Point p) {
        for (Shape s : shapes) {
            if (s.isSelected()) {
                Port port = s.getPortAt(p);
                if (port != null) return port;
            }
        }
        return null;
    }
    
    /**
     * 將當前選擇的多個(>=2) Shape 物件群組成一個 CompositeObject
     */
    public void groupSelectedShapes() {
        List<Shape> selected = new ArrayList<>();

        for (Shape s : shapes) {
            if (s.isSelected()) {
                selected.add(s);
            }
        }

        // 條件：>=2
        if (selected.size() < 2) return;

        // 建立 composite
        CompositeObject group = new CompositeObject(selected);

        // 移除原本的
        shapes.removeAll(selected);

        // 加入 composite
        shapes.add(group);

        // 更新選取狀態
        unselectAll();
        group.setSelected(true);
    }
    
    /**
     * 將選取的 CompositeObject 解除群組
     */
    public void ungroupSelectedShapes() {
        Shape selected = getFirstSelectedShape();

        // 必須是 Composite
        if (!(selected instanceof CompositeObject)) return;

        CompositeObject group = (CompositeObject) selected;

        // 移除 group
        shapes.remove(group);

        // 加回 children
        for (Shape s : group.getChildren()) {
            shapes.add(s);
            s.setSelected(true); // 通常會選取回來
        }
    }
}