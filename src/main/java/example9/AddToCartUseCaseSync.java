package example9;

import example9.networking.AddToCartHttpEndpointSync;
import example9.networking.CartItemScheme;
import example9.networking.NetworkErrorException;

public class AddToCartUseCaseSync {
    private final AddToCartHttpEndpointSync mAddToCartHttpEndpointSync;

    public enum UseCaseResult {
        FAILURE, NETWORK_ERROR, SUCCESS

    }

    public AddToCartUseCaseSync(AddToCartHttpEndpointSync mAddToCartHttpEndpointSync) {

        this.mAddToCartHttpEndpointSync = mAddToCartHttpEndpointSync;
    }

    public UseCaseResult addToCartSync(String offerId, int amount) {
        AddToCartHttpEndpointSync.EndpointResult result;

        try {
            result = mAddToCartHttpEndpointSync.addToCartSync(new CartItemScheme(offerId, amount));
        } catch (NetworkErrorException e) {
            return UseCaseResult.NETWORK_ERROR;
        }

        switch (result) {
            case SUCCESS -> {
                return UseCaseResult.SUCCESS;
            }
            case AUTH_ERROR, GENERAL_ERROR -> {
                return UseCaseResult.FAILURE;
            }
            default -> throw new RuntimeException("INVALID STATUS → " + result);
        }
    }
}
