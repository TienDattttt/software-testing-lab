package com.example.shopvnregisterswing.ui;

import javax.swing.*;
import java.awt.*;

public class TermsDialog extends JDialog {

    public TermsDialog(JFrame owner) {
        super(owner, "Điều khoản sử dụng", true);
        setSize(520, 360);
        setLocationRelativeTo(owner);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setText("""
        ĐIỀU KHOẢN SỬ DỤNG (Demo)
        
        1) Bạn chịu trách nhiệm về thông tin cung cấp.
        2) Không sử dụng dịch vụ cho mục đích vi phạm pháp luật.
        3) ...
        """);

        JScrollPane sp = new JScrollPane(area);
        JButton ok = new JButton("Đóng");
        ok.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(ok);

        setLayout(new BorderLayout(10, 10));
        add(sp, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
