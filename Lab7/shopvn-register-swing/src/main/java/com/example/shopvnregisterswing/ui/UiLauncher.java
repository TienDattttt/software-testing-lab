package com.example.shopvnregisterswing.ui;


import com.example.shopvnregisterswing.repo.AccountRepository;
import com.example.shopvnregisterswing.repo.ReferralRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class UiLauncher implements ApplicationRunner {

    private final AccountRepository accountRepo;
    private final ReferralRepository referralRepo;

    @Value("${app.ui.title:ShopVN.vn - Đăng ký tài khoản}")
    private String title;

    public UiLauncher(AccountRepository accountRepo, ReferralRepository referralRepo) {
        this.accountRepo = accountRepo;
        this.referralRepo = referralRepo;
    }

    @Override
    public void run(ApplicationArguments args) {
        SwingUtilities.invokeLater(() -> {
            RegisterForm form = new RegisterForm(accountRepo, referralRepo, title);
            form.setVisible(true);
        });
    }
}