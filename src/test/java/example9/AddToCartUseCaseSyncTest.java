package example9;

import example9.networking.AddToCartHttpEndpointSync;
import example9.networking.CartItemScheme;
import example9.networking.NetworkErrorException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static example9.AddToCartUseCaseSync.UseCaseResult;
import static example9.networking.AddToCartHttpEndpointSync.EndpointResult;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartUseCaseSyncTest {
    public static final String OFFER_ID = "offerId";
    public static final int AMOUNT = 4;

    @Mock
    AddToCartHttpEndpointSync mAddToCartHttpEndpointSyncMock;

    AddToCartUseCaseSync SUT;

    @SuppressWarnings("RedundantThrows")
    @Before
    public void setup() throws Exception {
        SUT = new AddToCartUseCaseSync(mAddToCartHttpEndpointSyncMock);
        success();
    }

    @Test
    public void addToCartSync_correctParametersPassedToEndpoint() throws Exception {
        ArgumentCaptor<CartItemScheme> ac = ArgumentCaptor.forClass(CartItemScheme.class);

        SUT.addToCartSync(OFFER_ID, AMOUNT);

        verify(mAddToCartHttpEndpointSyncMock).addToCartSync(ac.capture());
        assertEquals(ac.getValue().getOfferId(), OFFER_ID);
        assertEquals(ac.getValue().getAmount(), AMOUNT);

    }

    @SuppressWarnings("RedundantThrows")
    @Test
    public void addToCartSync_success_returnsSuccess() throws Exception {
        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);

        assertEquals(result, UseCaseResult.SUCCESS);
    }

    @Test
    public void addToCartSync_authError_returnsFailure() throws Exception {
        authError();

        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);

        assertEquals(result, UseCaseResult.FAILURE);
    }

    @Test
    public void addToCartSync_generalError_returnsFailure() throws Exception {
        generalError();

        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);

        assertEquals(result, UseCaseResult.FAILURE);
    }

    @Test
    public void addToCartSync_netWorkException_returnsNetworkError() throws Exception {
        // Arrange
        networkError();
        // Act
        UseCaseResult result = SUT.addToCartSync(OFFER_ID, AMOUNT);
        //Assert
        assertEquals(result, UseCaseResult.NETWORK_ERROR);
    }


    //* helper methods
    private void authError() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(EndpointResult.AUTH_ERROR);
    }

    private void generalError() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(EndpointResult.GENERAL_ERROR);
    }


    private void networkError() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenThrow(new NetworkErrorException());
    }

    private void success() throws NetworkErrorException {
        when(mAddToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(EndpointResult.SUCCESS);
    }
}