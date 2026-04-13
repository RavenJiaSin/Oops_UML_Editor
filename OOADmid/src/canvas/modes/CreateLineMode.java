package canvas.modes;

import canvas.controller.EditorController;
import canvas.core.*;
import canvas.ui.CanvasPanel;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

public class CreateLineMode implements Mode {
    private CanvasPanel canvas;
    private EditorController controller;
    private Port sourcePort = null;  // 改為紀錄 Port 實體
    private Port targetPort = null;
    private Port tempTargetPort = null;
    private Point mouseNow = null;
    private String lineType;
    private Shape sourceShape = null;
    private Shape targetShape = null;
    
    public CreateLineMode(CanvasPanel canvas, EditorController controller, String type) {
        this.canvas = canvas;
        this.controller = controller;
        this.lineType = type;
    }
    
    
    @Override
    public void mousePressed(MouseEvent e) {
        // 1. 先找到滑鼠點擊處的 Shape
        sourceShape = controller.getShapeAt(e.getPoint());
        
        // 2. 若點擊在 Shape 內，則紀錄最接近點擊位置的 Port
        if (sourceShape != null) {
        	sourcePort = sourceShape.getNearestPort(e.getPoint());
        	mouseNow = e.getPoint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // 若 sourcePort 存在，不斷更新滑鼠位置並重繪預覽線
        if (sourcePort != null) {
            mouseNow = e.getPoint();
            targetShape = controller.getShapeAt(e.getPoint());
        	if (targetShape != null && targetShape != sourcePort.getParent()) {
            	// 紀錄最近的 targetPort
                tempTargetPort = targetShape.getNearestPort(e.getPoint());
            }else {
            	tempTargetPort = null;
            }
            canvas.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	targetShape = controller.getShapeAt(e.getPoint());
    	if (sourcePort != null) {
	    	if (targetShape != null && targetShape != sourcePort.getParent()) {
	        	// 紀錄最近的 targetPort
	            targetPort = targetShape.getNearestPort(e.getPoint());
	        }
	        if (targetPort != null) {        
	            // 正式建立連線 (從 Port 到 Port)
	            LineObject newLine = createLine(sourcePort, targetPort);
	            sourceShape.addConnectedLine(newLine);
	            targetShape.addConnectedLine(newLine);
	        }
    	}
    	
        // 釋放資源與重繪
        sourcePort = null;
        targetPort =null;
        tempTargetPort =null;
        mouseNow = null;
        sourceShape = null;
        targetShape = null;
        canvas.repaint();
    }

    private LineObject createLine(Port from, Port to) {
        switch (lineType) {
            case "Generalization": return new GeneralizationLine(from, to);
            case "Composition":    return new CompositionLine(from, to);
            case "Association":     return new AssociationLine(from, to);
            default: 			   return new LineObject(from, to);
        }
    }

    public void drawPreview(Graphics g) {
        if (sourcePort != null && mouseNow != null) {
            // 預覽線從 sourcePort 的絕對位置拉到當前滑鼠位置
            Point pStart = sourcePort.getAbsoluteLocation();
            sourcePort.drawPort(g);
            if (tempTargetPort != null) { tempTargetPort.drawPort(g); }
            g.setColor(java.awt.Color.BLACK);
            g.drawLine(pStart.x, pStart.y, mouseNow.x, mouseNow.y);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
}