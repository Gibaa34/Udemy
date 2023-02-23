package stepDefenitions;

import io.cucumber.core.gherkin.Step;
import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {

        StepDefenition step = new StepDefenition();

        if(StepDefenition.place_Id==null) {
            step.add_place_payload_with("Plovdiv", "Italian", "Europe");
            step.user_calls_with_http_request("addPlaceAPI", "POST");
            step.verify_place_id_created_maps_to_using("Plovdiv", "getPlaceAPI");
        }
    }
}
