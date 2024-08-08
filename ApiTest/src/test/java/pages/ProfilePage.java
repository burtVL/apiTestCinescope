package pages;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ProfilePage {
    public void clickProfileButton() {
        $("[data-qa-id='profile_page_button']").shouldBe(visible).click();
    }

    public String getUserId() {
        return $(By.xpath("/html/body/div[2]/main/div[1]/div[2]/ul/li[1]")).getText();
    }
}
