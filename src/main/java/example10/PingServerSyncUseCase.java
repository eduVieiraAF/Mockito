package example10;

import example10.networking.PingServerHttpEndpointSync;

public class PingServerSyncUseCase {
    public enum UseCaseResult {
        FAILURE, SUCCESS
    }

    private final PingServerHttpEndpointSync mPingServerHttpEndpointSync;

    public PingServerSyncUseCase (PingServerHttpEndpointSync pingServerHttpEndpointSync) {
        mPingServerHttpEndpointSync = pingServerHttpEndpointSync;
    }

    public UseCaseResult pingServerSync() {
        PingServerHttpEndpointSync.EndpointResult result = mPingServerHttpEndpointSync.pingServerSynch();
        return switch (result) {
            case GENERAL_ERROR, NETWORK_ERROR -> UseCaseResult.FAILURE;
            case SUCCESS -> UseCaseResult.SUCCESS;
        };
    }
}
