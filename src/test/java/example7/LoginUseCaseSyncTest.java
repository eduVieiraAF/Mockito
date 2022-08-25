package example7;

import example7.authtoken.AuthTokenCache;
import example7.eventbus.EventBusPoster;
import example7.eventbus.LoggedInEvent;
import example7.networking.LoginHttpEndpointSync;
import example7.networking.NetworkErrorExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginUseCaseSyncTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    public static final String AUTH_TOKEN = "authToken";

    @Mock
    LoginHttpEndpointSync mLoginHttpEndpointSyncMock;
    @Mock
    AuthTokenCache mAuthTokenCacheMock;
    @Mock
    EventBusPoster mEventBusPosterMock;
    LoginUseCaseSync SUT;

    @Before
    public void setup() throws Exception {

        SUT = new LoginUseCaseSync(
                mLoginHttpEndpointSyncMock,
                mAuthTokenCacheMock,
                mEventBusPosterMock
        );
        success();
    }

    @Test
    public void loginSync_success_usernameAndPasswordPassedToEndpoint() throws Exception {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USERNAME, PASSWORD);
        verify(mLoginHttpEndpointSyncMock, times(1)).loginSync(ac.capture(), ac.capture());
        List<String> captures = ac.getAllValues();
        assertEquals(captures.get(0), USERNAME);
        assertEquals(captures.get(1), PASSWORD);
    }

    @Test
    public void loginSync_success_authTokenCached() {
        ArgumentCaptor<String> ac = ArgumentCaptor.forClass(String.class);
        SUT.loginSync(USERNAME, AUTH_TOKEN);
        verify(mAuthTokenCacheMock).cacheAuthToken(ac.capture());
        assertEquals(ac.getValue(), AUTH_TOKEN);

    }

    @Test
    public void loginSync_generalError_authTokenNotCached() throws Exception {
        generalError();
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoInteractions(mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_authError_authTokenNotCached() throws Exception {
        authError();
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_serverError_authTokenNotCached() throws Exception {
        serverError();
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mAuthTokenCacheMock);
    }

    @Test
    public void loginSync_success_loggedInEventPosted() {
        ArgumentCaptor<Object> ac = ArgumentCaptor.forClass(Object.class);
        SUT.loginSync(USERNAME, PASSWORD);
        verify(mEventBusPosterMock).postEvent(ac.capture());
        //noinspection deprecation
        assertThat(ac.getValue(), is(instanceOf(LoggedInEvent.class)));
    }

    @Test
    public void loginSync_generalError_noInteractionWithEventBusPoster() throws Exception {
        generalError();
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void loginSync_authError_noInteractionWithEventBusPoster() throws Exception {
        authError();
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void loginSync_serverError_noInteractionWithEventBusPoster() throws Exception {
        serverError();
        SUT.loginSync(USERNAME, PASSWORD);
        verifyNoMoreInteractions(mEventBusPosterMock);
    }

    @Test
    public void loginSync_success_returnsSuccess() {
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertEquals(result, LoginUseCaseSync.UseCaseResult.SUCCESS);
    }

    @Test
    public void loginSync_serverError_returnsFailure() throws Exception {
        serverError();
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertEquals(result, LoginUseCaseSync.UseCaseResult.FAILURE);
    }

    @Test
    public void loginSync_authError_returnsFailure() throws Exception {
        authError();
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertEquals(result, LoginUseCaseSync.UseCaseResult.FAILURE);
    }

    @Test
    public void loginSync_GeneralError_returnsFailure() throws Exception {
        generalError();
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertEquals(result, LoginUseCaseSync.UseCaseResult.FAILURE);
    }

    @Test
    public void loginSync_networkError_returnsNetworkError() throws Exception {
        networkError();
        LoginUseCaseSync.UseCaseResult result = SUT.loginSync(USERNAME, PASSWORD);
        assertEquals(result, LoginUseCaseSync.UseCaseResult.NETWORK_ERROR);
    }

    //* ---------------HELPER-METHODS--------------------------------------------------------------------------------------

    private void networkError() throws Exception {
        doThrow(new NetworkErrorExceptions())
                .when(mLoginHttpEndpointSyncMock).loginSync(any(String.class), any(String.class));
    }

    private void success() throws NetworkErrorExceptions {
        when(mLoginHttpEndpointSyncMock.loginSync(any(String.class), any(String.class)))
                .thenReturn(new LoginHttpEndpointSync.EndpointResult(
                        LoginHttpEndpointSync.EndpointResultStatus.SUCCESS, AUTH_TOKEN)
                );
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