import org.junit.Test;

public class LoginUseCaseSyncTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    public static final String AUTH_TOKEN = "authToken";

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
 }