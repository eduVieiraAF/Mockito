package example10;

import example10.networking.PingServerHttpEndpointSync;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PingServerSyncUseCaseTest {

    @Mock
    PingServerHttpEndpointSync mPingServerHttpEndpointSyncMock;

    PingServerSyncUseCase SUT;

    @Before
    public void setup() {
        SUT = new PingServerSyncUseCase(mPingServerHttpEndpointSyncMock);
        success();
    }

    @Test
    public void pingServerSync_success_returnsSuccess() {
        PingServerSyncUseCase.UseCaseResult result = SUT.pingServerSync();
        assertEquals(result, PingServerSyncUseCase.UseCaseResult.SUCCESS);
    }

    @Test
    public void pingServerSync_generalError_returnsFailure() {
        generalError();
        PingServerSyncUseCase.UseCaseResult result = SUT.pingServerSync();
        assertEquals(result, PingServerSyncUseCase.UseCaseResult.FAILURE);
    }

    @Test
    public void pingServerSync_networkError_returnsFailure() {
        networkError();
        PingServerSyncUseCase.UseCaseResult result = SUT.pingServerSync();
        assertEquals(result, PingServerSyncUseCase.UseCaseResult.FAILURE);
    }

    public void success() {
        when(mPingServerHttpEndpointSyncMock.pingServerSynch())
                .thenReturn(PingServerHttpEndpointSync.EndpointResult.SUCCESS);
    }

    private void generalError() {
        when(mPingServerHttpEndpointSyncMock.pingServerSynch())
                .thenReturn(PingServerHttpEndpointSync.EndpointResult.GENERAL_ERROR);
    }

    private void networkError() {
        when(mPingServerHttpEndpointSyncMock.pingServerSynch())
                .thenReturn(PingServerHttpEndpointSync.EndpointResult.NETWORK_ERROR);
    }
}