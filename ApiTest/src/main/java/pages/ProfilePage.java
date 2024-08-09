package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;


public class ProfilePage {
    public void clickProfileButton() {
        Selenide.$("[data-qa-id='profile_page_button']").shouldBe(Condition.visible).click();
    }

    public String getUserId() {
        return Selenide.$(By.xpath("/html/body/div[2]/main/div[1]/div[2]/ul/li[1]")).getText();
    }
}
