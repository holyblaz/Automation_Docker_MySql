package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @BeforeEach
    void login() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void ClearDB() {
        DataHelper.clearDataBase();
    }

    @Test
    void shouldLoginFirstRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoFirstUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCode());
        val dashBoardPage = new DashboardPage();
    }

    @Test
    void shouldLoginSecondRegisteredUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoSecondUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getVerificationCode());
        var dashBoardPage = new DashboardPage();
    }

    @Test
    void shouldNotAuthWithWrongLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWrongLogin();
        loginPage.validLogin(authInfo);
        loginPage.getNotification();
    }

    @Test
    void shouldNotAuthWithWrongPass() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoWrongPassword();
        loginPage.validLogin(authInfo);
        loginPage.getNotification();
    }

    @Test
    void shouldBlockUserAfterThirdWrongAuthCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfoFirstUser();
        loginPage.validLogin(authInfo);
        var verificationPage = new VerificationPage();
        verificationPage.validVerify(DataHelper.getWrongCode());
        login();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getWrongCode());
        login();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getWrongCode());
        login();
        loginPage.validLogin(authInfo);
        verificationPage.validVerify(DataHelper.getWrongCode());
        verificationPage.getBlockedVerifyNotification();
    }
}