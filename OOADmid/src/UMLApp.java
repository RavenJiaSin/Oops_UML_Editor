import javax.swing.SwingUtilities;
import canvas.ui.MainFrame;

public class UMLApp {
    public static void main(String[] args) {
        // 啟動 Event Dispatch Thread (EDT) 確保 Swing 線程安全
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}