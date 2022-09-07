package example11;

import example11.cart.CartItem;
import example11.networking.CartItemSchema;
import example11.networking.GetCartItemsHttpEndpoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FetchCartItemsUseCaseTest {
    private static final int PRICE = 5;
    private static final int LIMIT = 10;
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String ID = "id";

    @Mock
    GetCartItemsHttpEndpoint mGetCartItemsHttpEndpointMock;
    @Mock
    FetchCartItemsUseCase.Listener mListenerMock1;
    @Mock
    FetchCartItemsUseCase.Listener mListenerMock2;
    @Captor
    ArgumentCaptor<List<CartItem>> mAcListCartItem;

    FetchCartItemsUseCase SUT;

    @Before
    public void setup() throws Exception {
        SUT = new FetchCartItemsUseCase(mGetCartItemsHttpEndpointMock);
        success();
    }

    // * correct limit passed to endpoint
    @Test
    public void fetchCartItems_correctLimitPassedToEndpoint() throws Exception {
        ArgumentCaptor<Integer> acInt = ArgumentCaptor.forClass(Integer.class);

        SUT.fetchCartItemsAndNotify(LIMIT);

        verify(mGetCartItemsHttpEndpointMock).getCartItems(acInt.capture(), any(GetCartItemsHttpEndpoint.Callback.class));
        assertEquals(acInt, LIMIT);
    }

    // * success - all observers notified with correct data
    @Test
    public void fetchCartItems_success_observersNotifiedWithCorrectData() throws Exception {

        SUT.registerListener(mListenerMock1);
        SUT.registerListener(mListenerMock2);
        SUT.fetchCartItemsAndNotify(LIMIT);

        verify(mListenerMock1).onCartItemsFetched(mAcListCartItem.capture());
        verify(mListenerMock2).onCartItemsFetched(mAcListCartItem.capture());
        List<List<CartItem>> captures = mAcListCartItem.getAllValues();
        List<CartItem> capture1 = captures.get(0);
        List<CartItem> capture2 = captures.get(1);
        assertEquals(capture1, getCartItems());
        assertEquals(capture2, getCartItems());
    }


    // * success - unsubscribed observers not notified
    // * general error - observers notified of failure
    // * network error - observers notified of failure

    // *----------------------HELPER-METHODS----------------------------------------------------------------------------

    public void success() {
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            GetCartItemsHttpEndpoint.Callback callback = (GetCartItemsHttpEndpoint.Callback) args[1];
            callback.onGetCartItemsSucceeded(getItemSchemes());
            return null;
        }).when(mGetCartItemsHttpEndpointMock).getCartItems(anyInt(), any(GetCartItemsHttpEndpoint.Callback.class));
    }

    private List<CartItemSchema> getItemSchemes() {
        List<CartItemSchema> schemas = new ArrayList<>();
        schemas.add(new CartItemSchema(ID, TITLE, DESCRIPTION, PRICE));

        return schemas;
    }

    private List<CartItem> getCartItems() {
        List<CartItem> schemas = new ArrayList<>();
        schemas.add(new CartItem(ID, TITLE, DESCRIPTION, PRICE));

        return schemas;
    }
}