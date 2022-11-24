package views.presenters;

import profile_customization_use_case.CustomizationFailed;
import profile_customization_use_case.CustomizationOutputBoundary;
import profile_customization_use_case.CustomizationResponse;

public class CustomizationPresenter implements CustomizationOutputBoundary {

    @Override
    public CustomizationResponse prepareSuccessView(CustomizationResponse response) {
        return response;
    }

    public CustomizationResponse prepareFailView(String error) {
        throw new CustomizationFailed(error);
    }
}
