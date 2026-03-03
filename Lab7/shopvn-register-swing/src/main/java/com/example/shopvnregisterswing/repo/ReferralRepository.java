package com.example.shopvnregisterswing.repo;


import com.example.shopvnregisterswing.db.DbConfig;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class ReferralRepository {
    private final DbConfig db;

    public ReferralRepository(DbConfig db) { this.db = db; }

    public boolean existsActive(String code) {
        String sql = "SELECT 1 FROM ReferralCodes WHERE Code = ? AND IsActive = 1";
        try (Connection c = db.openConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
