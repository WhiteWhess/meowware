package wtf.whess.meowware.client.api.utilities.combat;

import lombok.Data;
import wtf.whess.meowware.client.api.utilities.Utility;

public final class PositionUtil extends Utility {
    @Data
    public static class Position {
        private final double x, y, z;
    }
}
