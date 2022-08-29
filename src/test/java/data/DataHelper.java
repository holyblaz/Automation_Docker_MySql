package data;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfoFirstUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfoSecondUser() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getAuthInfoWrongPassword() {
        return new AuthInfo("petya", "3432425");
    }

    public static AuthInfo getAuthInfoWrongLogin() {
        return new AuthInfo("vasyaa", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCode() {
        val verificationCodeSql = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        String verificationCode;
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            val code = runner.query(conn, verificationCodeSql, new ScalarHandler<>());
            verificationCode = (String) code;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new VerificationCode(verificationCode);
    }

    public static VerificationCode getWrongCode() {
        return new VerificationCode("999999");
    }

    public static void clearDataBase() {
        val deleteAuthCodes = "DELETE FROM auth_codes;";
        val deleteCardTransactions = "DELETE FROM card_transactions;";
        val deleteCards = "DELETE FROM cards;";
        val deleteUsers = "DELETE FROM users;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            runner.update(conn, deleteAuthCodes);
            runner.update(conn, deleteCardTransactions);
            runner.update(conn, deleteCards);
            runner.update(conn, deleteUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}