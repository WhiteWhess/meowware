package wtf.whess.meowware.client.api.eventsystem;

/**
 * @author DarkMagician6
 *
 * @since 2014-07-07
 */
public final class Priority {
    public static final byte FIRST = 0;
    public static final byte SECOND = 1;
    public static final byte THIRD = 2;
    public static final byte FOURTH = 3;
    public static final byte FIFTH = 4;
    public static final byte[] VALUE_ARRAY;

    static {
        byte[] byArray = new byte[5];
        byArray[1] = 1;
        byArray[2] = 2;
        byArray[3] = 3;
        byArray[4] = 4;
        VALUE_ARRAY = byArray;
    }
}