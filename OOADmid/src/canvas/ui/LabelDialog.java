package canvas.ui;

import javax.swing.*;
import java.awt.*;

public class LabelDialog extends JDialog {
    private JTextField nameField;
    private Color selectedColor;
    private boolean confirmed = false;

    public LabelDialog(Frame parent, String currentName, Color currentColor) {
        super(parent, "Customize Label Style", true);
        this.selectedColor = currentColor;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Name Input
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        
        nameField = new JTextField(currentName, 15);
        gbc.gridx = 1;
        add(nameField, gbc);

        // 2. Color Selection
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Color:"), gbc);

        JButton colorBtn = new JButton();
        colorBtn.setBackground(currentColor);
        colorBtn.setPreferredSize(new Dimension(30, 20));
        colorBtn.addActionListener(e -> {
            Color c = JColorChooser.showDialog(this, "Select Background Color", selectedColor);
            if (c != null) {
                selectedColor = c;
                colorBtn.setBackground(c);
            }
        });
        gbc.gridx = 1;
        add(colorBtn, gbc);

        // 3. Buttons
        JPanel btnPanel = new JPanel();
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        
        okBtn.addActionListener(e -> { confirmed = true; dispose(); });
        cancelBtn.addActionListener(e -> { confirmed = false; dispose(); });

        btnPanel.add(cancelBtn);
        btnPanel.add(okBtn);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnPanel, gbc);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmed() { return confirmed; }
    public String getInputName() { return nameField.getText(); }
    public Color getSelectedColor() { return selectedColor; }
}