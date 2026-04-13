package canvas.ui;

import canvas.controller.EditorController;
import canvas.core.LineObject;
import java.awt.Rectangle;

import canvas.modes.CreateLineMode;
import canvas.modes.Mode;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class CanvasPanel extends JPanel {
	private Mode currentMode;
	private Rectangle selectionRect;
	private EditorController controller;
	
    public CanvasPanel(EditorController controller) {
    	this.controller = controller;
        this.setBackground(Color.WHITE); // 設定畫布背景為白色
        
        MouseAdapter listener = new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		if (currentMode != null) currentMode.mousePressed(e);
        	}
        	@Override
            public void mouseReleased(MouseEvent e) {
                if (currentMode != null) currentMode.mouseReleased(e);
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentMode != null) currentMode.mouseDragged(e);
            }
        };
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }
    
    public void setMode(Mode mode) {
    	this.currentMode = mode;
    }
    
    
    
    
    
    public void setSelectionRect(Rectangle r) {
        this.selectionRect = r;
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 倒著畫：先畫 Index 最大的 (最底層)，最後畫 Index 0 (最上層)
        for (int i = controller.getShapes().size() - 1; i >= 0; i--) {
        	 controller.getShapes().get(i).draw(g);
        	 List<LineObject> lines = controller.getShapes().get(i).getLines();
        	 if (lines != null) {
        		 for (LineObject line : lines) {
        			 line.draw(g);
        		 }
        	 }
        }
        
        if (selectionRect != null) {
        	Graphics2D g2 = (Graphics2D) g;

            // 半透明填充
            g2.setColor(new Color(0, 0, 255, 50)); // RGBA
            g2.fill(selectionRect);

            // 邊框
            g2.setColor(Color.BLUE);
            g2.draw(selectionRect);
        }
        
        if (currentMode instanceof CreateLineMode) {
            ((CreateLineMode) currentMode).drawPreview(g);
        }
    }
}