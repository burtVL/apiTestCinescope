package assertions;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.internal.shadowed.jackson.core.TreeNode;
import io.restassured.response.Response;
import conditions.Condition;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class AssertableResponse {
    private final Response response;

    @Step("api response should have {condition}")
    public AssertableResponse shouldHave(Condition condition){
            condition.check(response);
            return this;
    }
    public <T> T asPojo(Class<T> tClass){
        return response.as(tClass);
    }
    @Step("Extract value from response path: {path}")
    public <T> T extractPath(String path) {
        return response.path(path);
    }

    @Step("Extract id from response: {id}")
    public String extractId() {
        String id = extractPath("id");
        String prefixedId = "ID: " + id;
        // Логируем извлеченный id
        Allure.addAttachment("Extracted ID", prefixedId);
        return prefixedId;
    }


}
