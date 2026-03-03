package com.example.shopvnregisterswing.ui;


import com.example.shopvnregisterswing.repo.AccountRepository;
import com.example.shopvnregisterswing.repo.ReferralRepository;
import com.example.shopvnregisterswing.security.PasswordHasher;
import com.example.shopvnregisterswing.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDate;

//@org.springframework.stereotype.Component
public class RegisterForm extends JFrame {

    private final AccountRepository accountRepo;
    private final ReferralRepository referralRepo;

    private JTextField txtFullName = new JTextField();
    private JTextField txtUsername = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtPhone = new JTextField();
    private JPasswordField txtPassword = new JPasswordField();
    private JPasswordField txtConfirm = new JPasswordField();
    private JTextField txtDob = new JTextField(); // dd/MM/yyyy
    private JRadioButton rMale = new JRadioButton("Nam");
    private JRadioButton rFemale = new JRadioButton("Nữ");
    private JRadioButton rNo = new JRadioButton("Không muốn tiết lộ");
    private JTextField txtReferral = new JTextField();
    private JCheckBox chkTerms = new JCheckBox("Tôi đồng ý Điều khoản (*)");
    private JButton btnTerms = new JButton("Xem Điều khoản");
    private JButton btnRegister = new JButton("Đăng ký");
    private JButton btnLogin = new JButton("Đăng nhập");

    private JLabel lblStatus = new JLabel(" ");

    public RegisterForm(
            AccountRepository accountRepo,
            ReferralRepository referralRepo,
            @Value("${app.ui.title}") String title
    ) {
        super(title);
        this.accountRepo = accountRepo;
        this.referralRepo = referralRepo;

        initUi();
        wireEvents();
        refreshRegisterButtonState();
    }

    private void initUi() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(720, 520);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setContentPane(root);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 6, 6, 6);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        int row = 0;
        row = addRow(form, g, row, "Họ và tên (*)", txtFullName);
        row = addRow(form, g, row, "Tên đăng nhập (*)", txtUsername);
        row = addRow(form, g, row, "Email (*)", txtEmail);
        row = addRow(form, g, row, "Số điện thoại (*)", txtPhone);
        row = addRow(form, g, row, "Mật khẩu (*)", txtPassword);
        row = addRow(form, g, row, "Xác nhận mật khẩu (*)", txtConfirm);
        row = addRow(form, g, row, "Ngày sinh (dd/mm/yyyy)", txtDob);

        // Gender row
        ButtonGroup bg = new ButtonGroup();
        bg.add(rMale); bg.add(rFemale); bg.add(rNo);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.add(rMale); genderPanel.add(rFemale); genderPanel.add(rNo);
        row = addRow(form, g, row, "Giới tính", genderPanel);

        row = addRow(form, g, row, "Mã giới thiệu", txtReferral);

        // Terms row with link button
        JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        termsPanel.add(chkTerms);
        termsPanel.add(btnTerms);
        row = addRow(form, g, row, " ", termsPanel);

        root.add(form, BorderLayout.CENTER);

        // Bottom area
        JPanel bottom = new JPanel(new BorderLayout(10, 10));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.add(btnLogin);
        actions.add(btnRegister);

        lblStatus.setForeground(new Color(30, 30, 30));
        bottom.add(lblStatus, BorderLayout.CENTER);
        bottom.add(actions, BorderLayout.EAST);

        root.add(bottom, BorderLayout.SOUTH);

        btnRegister.setEnabled(false);
    }

    private int addRow(JPanel panel, GridBagConstraints g, int row, String label, Component field) {
        g.gridy = row;

        g.gridx = 0;
        g.weightx = 0;
        panel.add(new JLabel(label), g);

        g.gridx = 1;
        g.weightx = 1;
        panel.add(field, g);

        return row + 1;
    }

    private void wireEvents() {
        DocumentListener dl = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { refreshRegisterButtonState(); }
            @Override public void removeUpdate(DocumentEvent e) { refreshRegisterButtonState(); }
            @Override public void changedUpdate(DocumentEvent e) { refreshRegisterButtonState(); }
        };

        txtFullName.getDocument().addDocumentListener(dl);
        txtUsername.getDocument().addDocumentListener(dl);
        txtEmail.getDocument().addDocumentListener(dl);
        txtPhone.getDocument().addDocumentListener(dl);
        txtPassword.getDocument().addDocumentListener(dl);
        txtConfirm.getDocument().addDocumentListener(dl);
        txtDob.getDocument().addDocumentListener(dl);
        txtReferral.getDocument().addDocumentListener(dl);

        chkTerms.addActionListener(e -> refreshRegisterButtonState());
        rMale.addActionListener(e -> refreshRegisterButtonState());
        rFemale.addActionListener(e -> refreshRegisterButtonState());
        rNo.addActionListener(e -> refreshRegisterButtonState());

        btnTerms.addActionListener(e -> new TermsDialog(this).setVisible(true));

        btnRegister.addActionListener(e -> onSubmit());

        btnLogin.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chuyển sang màn hình đăng nhập (demo).");
            // bạn có thể mở LoginForm tại đây
        });
    }

    private void refreshRegisterButtonState() {
        boolean requiredFilled =
                !txtFullName.getText().isBlank() &&
                        !txtUsername.getText().isBlank() &&
                        !txtEmail.getText().isBlank() &&
                        !txtPhone.getText().isBlank() &&
                        txtPassword.getPassword().length > 0 &&
                        txtConfirm.getPassword().length > 0 &&
                        chkTerms.isSelected();

        btnRegister.setEnabled(requiredFilled);
        lblStatus.setText(" ");
    }

    private void onSubmit() {
        try {
            // 1) client-side validate theo đặc tả
            String fullName = txtFullName.getText().trim();
            String username = txtUsername.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirm = new String(txtConfirm.getPassword());
            String referral = txtReferral.getText().trim();

            if (!ValidationUtil.validFullName(fullName)) fail("Họ và tên: 2–50 ký tự, chỉ chữ và khoảng trắng.");
            if (!ValidationUtil.validUsername(username)) fail("Tên đăng nhập: a-z, 0-9, _, bắt đầu bằng chữ, dài 5–20.");
            if (!ValidationUtil.validEmail(email)) fail("Email không đúng định dạng.");
            if (!ValidationUtil.validPhone(phone)) fail("SĐT: bắt đầu 0 và đủ 10 chữ số (VD: 0987654321).");
            if (!ValidationUtil.validPassword(password)) fail("Mật khẩu: 8–32, có hoa/thường/số/ký tự đặc biệt.");
            if (!password.equals(confirm)) fail("Xác nhận mật khẩu phải trùng khớp.");
            if (!chkTerms.isSelected()) fail("Bạn phải đồng ý Điều khoản.");

            LocalDate dob = null;
            try {
                dob = ValidationUtil.parseDobOrNull(txtDob.getText());
            } catch (Exception ex) {
                fail("Ngày sinh sai định dạng dd/mm/yyyy hoặc ngày không hợp lệ.");
            }
            if (!ValidationUtil.validAge16ToUnder100(dob, LocalDate.now())) {
                fail("Tuổi phải từ 16 đến dưới 100 tính đến ngày đăng ký.");
            }

            Integer gender = null;
            if (rMale.isSelected()) gender = 0;
            else if (rFemale.isSelected()) gender = 1;
            else if (rNo.isSelected()) gender = 2;

            if (!referral.isBlank()) {
                if (!ValidationUtil.validReferral(referral)) fail("Mã giới thiệu: đúng 8 ký tự chữ hoa và số.");
                if (!referralRepo.existsActive(referral)) fail("Mã giới thiệu không tồn tại hoặc không còn hiệu lực.");
            }

            // 2) server-side checks: unique username/email
            if (accountRepo.usernameExists(username)) fail("Tên đăng nhập đã tồn tại.");
            if (accountRepo.emailExists(email)) fail("Email đã được đăng ký.");

            // 3) hash + insert DB
            byte[] salt = PasswordHasher.newSalt();
            byte[] hash = PasswordHasher.hash(password.toCharArray(), salt);

            accountRepo.insertAccount(
                    fullName, username, email, phone,
                    hash, salt,
                    dob, gender,
                    referral.isBlank() ? null : referral,
                    true
            );

            JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
            clearForm();

        } catch (IllegalArgumentException ex) {
            lblStatus.setText(ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtFullName.setText("");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPassword.setText("");
        txtConfirm.setText("");
        txtDob.setText("");
        txtReferral.setText("");
        chkTerms.setSelected(false);
        rMale.setSelected(false);
        rFemale.setSelected(false);
        rNo.setSelected(false);
        refreshRegisterButtonState();
    }

    private void fail(String msg) {
        throw new IllegalArgumentException(msg);
    }
}
