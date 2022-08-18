import authtoken.AuthTokenCache;
import eventbus.EventBusPoster;
import networking.LoginHttpEndpointSync;
import networking.NetworkErrorExceptions;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginUseCaseSyncTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    public static final String AUTH_TOKEN = "authToken";

    LoginHttpEndpointSync mLoginHttpEndpointSyncMock;
    AuthTokenCache mAuthTokenCacheMock;
    EventBusPoster mEventBusPosterMock;
    LoginUseCaseSync SUT;

    @Before
    public void setup() throws Exception {
        mLoginHttpEndpointSyncMock = mock(LoginHttpEndpointSync.class);
        mAuthTokenCacheMock = mock(AuthTokenCache.class);
        mEventBusPosterMock = mock(EventBusPoster.class);
        SUT = new LoginUseCaseSync(
                mLoginHttpEndpointSyncMock,
                mAuthTokenCacheMock,
                mEventBusPosterMock
        );

    }

    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() {

    }

    @Test
    public void loginSync_generalError_authTokenNotCached() {

    }

    @Test
    public void loginSync_authError_authTokenNotCached() {

    }

    @Test
    public void loginSync_serverError_authTokenNotCached() {

    }

    @Test
    public void loginSync_success_loggedInEventPosted() {

    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() {

    }

    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() {

    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() {

    }

    @Test
    public void loginSync_success_returnsSuccess() {

    }

    @Test
    public void loginSync_serverError_returnsFailure() {

    }

    @Test
    public void loginSync_authError_returnsFailure() {

    }

    @Test
    public void loginSync_GeneralError_returnsFailure() {

    }

    @Test
    public void loginSync_networkError_returnsNetworkError() {

    }

    private void networkError() throws Exception {
        doThrow(new NetworkErrorExceptions())
                .when(mLoginHttpEndpointSyncMock).loginSync(any(String.class), any(String.class));
    }

    private void success() throws NetworkErrorExceptions {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.SUCCESS,
                        AUTH_TOKEN
                ));
    }

    private void generalError() throws Exception {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.GENERAL_ERROR,
                        ""
                ));
    }

    private void authError() throws Exception {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.AUTH_ERROR,
                        ""
                ));
    }

    private void serverError() throws Exception {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.SERVER_ERROR,
                        ""
                ));
    }
}