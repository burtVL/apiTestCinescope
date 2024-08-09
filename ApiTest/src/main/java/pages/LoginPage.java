package pages;

import com.codeborne.selenide.Selenide;


public class LoginPage {
    public void openLoginPage() {
        Selenide.open("https://dev-cinescope.store/login");
    }

    public void setEmail(String email) {
        Selenide.$("#email").setValue(email);
    }

    public void setPassword(String password) {
        Selenide.$("#password").setValue(password);
    }

    public void clickLoginButton() {
        Selenide.$("[data-qa-id='login_submit_button']").click();
    }
}
