package conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusCodeCondition implements Condition{

    private final int StatusCode;

    @Override
    public void check(Response response) {
            response.then().assertThat().statusCode(StatusCode);
    }
}
