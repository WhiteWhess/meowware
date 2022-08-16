package wtf.whess.meowware.client.api.utilities.client;

import lombok.Getter;
import lombok.Setter;
import wtf.whess.meowware.client.api.utilities.Utility;

public final class Spoofer extends Utility {
    @Getter @Setter
    private static String fakeIP = "";
    @Getter @Setter
    private static String fakeUUID = "";
}
