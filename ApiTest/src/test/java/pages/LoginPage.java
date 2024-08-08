package pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {
    public void openLoginPage() {
        open("https://dev-cinescope.store/login");
    }

    public void setEmail(String email) {
        $("#email").setValue(email);
    }

    public void setPassword(String password) {
        $("#password").setValue(password);
    }

    public void clickLoginButton() {
        $("[data-qa-id='login_submit_button']").click();
    }
}
