package wtf.whess.meowware.client.api.utilities.client;

import wtf.whess.meowware.client.api.utilities.Utility;

import java.util.Random;

public final class NameUtil extends Utility {
    private static final char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String randomName() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= 8; ++i) {
            stringBuilder.append(alphabet[new Random().nextInt(alphabet.length)]);
        } return stringBuilder.toString();
    }

    public static String randomName(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= length; ++i) {
            stringBuilder.append(alphabet[new Random().nextInt(alphabet.length)]);
        } return stringBuilder.toString();
    }

    public static String randomName(String prefix, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= length; ++i) {
            stringBuilder.append(alphabet[new Random().nextInt(alphabet.length)]);
        } return prefix + stringBuilder;
    }
}
