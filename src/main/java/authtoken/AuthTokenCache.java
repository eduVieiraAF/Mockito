package authtoken;

public interface AuthTokenCache {
    void cacheAuthToken(String authToken);

    String getAuthToken();
}
