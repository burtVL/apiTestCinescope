package assertions;

import io.qameta.allure.Step;
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


}
