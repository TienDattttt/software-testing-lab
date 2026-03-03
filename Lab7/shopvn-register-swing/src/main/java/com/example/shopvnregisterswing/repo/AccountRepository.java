package com.example.shopvnregisterswing.repo;


import com.example.shopvnregisterswing.db.DbConfig;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;

@Repository
public class AccountRepository {
    private final DbConfig db;

    public AccountRepository(DbConfig db) { this.db = db; }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM Accounts WHERE Username = ?";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM Accounts WHERE Email = ?";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void insertAccount(
            String fullName,
            String username,
            String email,
            String phone,
            byte[] hash,
            byte[] salt,
            LocalDate dob,
            Integer gender,
            String referralCodeOrNull,
            boolean acceptedTerms
    ) {
        String sql = """
            INSERT INTO Accounts
            (FullName, Username, Email, Phone, PasswordHash, PasswordSalt, DateOfBirth, Gender, ReferralCode, AcceptedTerms)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, phone);

            ps.setBytes(5, hash);
            ps.setBytes(6, salt);

            if (dob == null) ps.setNull(7, Types.DATE);
            else ps.setDate(7, Date.valueOf(dob));

            if (gender == null) ps.setNull(8, Types.TINYINT);
            else ps.setInt(8, gender);

            if (referralCodeOrNull == null || referralCodeOrNull.isBlank()) {
                ps.setNull(9, Types.CHAR);
            } else {
                ps.setString(9, referralCodeOrNull);
            }

            ps.setBoolean(10, acceptedTerms);

            ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            // trường hợp race condition vẫn bị unique/fk chặn
            throw new RuntimeException("Vi phạm ràng buộc dữ liệu (username/email/referral).", ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}