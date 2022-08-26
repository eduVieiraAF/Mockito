package example10.networking;

public interface PingServerHttpEndpointSync {
    enum EndpointResult {
        SUCCESS, GENERAL_ERROR, NETWORK_ERROR
        /*
        in previous examples, exceptions were handled as to practice. However, add the network error flavour is a
        better fit for actual production coding
         */
    }

    EndpointResult pingServerSynch();
}
