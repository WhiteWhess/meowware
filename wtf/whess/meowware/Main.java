package wtf.whess.meowware;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.Session;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Scanner;

public class Main
{
    public static void main(String[] p_main_0_) throws IOException {
        String hwid =   Hash.SHA512.hash(Hash.MD5.hash(
                        System.getProperty("os.name")           +
                        System.getProperty("os.version")        +
                        System.getProperty("os.arch")           +
                        System.getenv("PROCESSOR_ARCHITECTURE") +
                        System.getenv("PROCESSOR_ARCHITEW6432") +
                        System.getenv("PROCESSOR_IDENTIFIER")   +
                        System.getenv("NUMBER_OF_PROCESSORS")   +
                        Runtime.getRuntime().availableProcessors()
        ));
        boolean auth = false;
        Scanner scanner = new Scanner(new URL("https://pastebin.com/raw/fsFWQp8n").openStream());
        while (scanner.hasNext())
            if (scanner.nextLine().equals(hwid))
                auth = true;
        if (!auth) {
            System.out.println(hwid);
            JOptionPane.showMessageDialog(null, "Invalid HWID. Contact with administration\nYour HWID in console/logs", "MeowWare anti-clown prot", JOptionPane.ERROR_MESSAGE);
            return;
        } OptionParser optionparser = new OptionParser();
        optionparser.allowsUnrecognizedOptions();
        optionparser.accepts("demo");
        optionparser.accepts("fullscreen");
        optionparser.accepts("checkGlErrors");
        OptionSpec<File> optionspec2 = optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
        OptionSpec<File> optionspec3 = optionparser.accepts("assetsDir").withRequiredArg().ofType(File.class);
        OptionSpec<File> optionspec4 = optionparser.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
        OptionSpec<String> optionspec5 = optionparser.accepts("proxyHost").withRequiredArg();
        OptionSpec<Integer> optionspec6 = optionparser.accepts("proxyPort").withRequiredArg().defaultsTo("8080").ofType(Integer.class);
        OptionSpec<String> optionspec7 = optionparser.accepts("proxyUser").withRequiredArg();
        OptionSpec<String> optionspec8 = optionparser.accepts("proxyPass").withRequiredArg();
        OptionSpec<String> optionspec9 = optionparser.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L);
        OptionSpec<String> optionspec10 = optionparser.accepts("uuid").withRequiredArg();
        OptionSpec<String> optionspec11 = optionparser.accepts("accessToken").withRequiredArg().required();
        OptionSpec<String> optionspec12 = optionparser.accepts("version").withRequiredArg().required();
        OptionSpec<Integer> optionspec13 = optionparser.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854);
        OptionSpec<Integer> optionspec14 = optionparser.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480);
        OptionSpec<String> optionspec15 = optionparser.accepts("userProperties").withRequiredArg().defaultsTo("{}");
        OptionSpec<String> optionspec16 = optionparser.accepts("profileProperties").withRequiredArg().defaultsTo("{}");
        OptionSpec<String> optionspec17 = optionparser.accepts("assetIndex").withRequiredArg();
        OptionSpec<String> optionspec18 = optionparser.accepts("userType").withRequiredArg().defaultsTo("legacy");
        OptionSpec<String> optionspec19 = optionparser.accepts("versionType").withRequiredArg().defaultsTo("release");
        OptionSpec<String> optionspec20 = optionparser.nonOptions();
        OptionSet optionset = optionparser.parse(p_main_0_);
        List<String> list = optionset.valuesOf(optionspec20);

        if (!list.isEmpty())
        {
            System.out.println("Completely ignored arguments: " + list);
        }

        String s = optionset.valueOf(optionspec5);
        Proxy proxy = Proxy.NO_PROXY;

        if (s != null)
        {
            try
            {
                proxy = new Proxy(Type.SOCKS, new InetSocketAddress(s, optionset.valueOf(optionspec6)));
            }
            catch (Exception ignored)
            {
            }
        }

        final String s1 = optionset.valueOf(optionspec7);
        final String s2 = optionset.valueOf(optionspec8);

        if (!proxy.equals(Proxy.NO_PROXY) && isNullOrEmpty(s1) && isNullOrEmpty(s2))
        {
            Authenticator.setDefault(new Authenticator()
            {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(s1, s2.toCharArray());
                }
            });
        }

        int i = optionset.valueOf(optionspec13);
        int j = optionset.valueOf(optionspec14);
        boolean flag = optionset.has("fullscreen");
        boolean flag1 = optionset.has("checkGlErrors");
        boolean flag2 = optionset.has("demo");
        String s3 = optionset.valueOf(optionspec12);
        Gson gson = (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new Serializer()).create();
        PropertyMap propertymap = JsonUtils.gsonDeserialize(gson, optionset.valueOf(optionspec15), PropertyMap.class);
        PropertyMap propertymap1 = JsonUtils.gsonDeserialize(gson, optionset.valueOf(optionspec16), PropertyMap.class);
        String s4 = optionset.valueOf(optionspec19);
        File file1 = optionset.valueOf(optionspec2);
        File file2 = optionset.has(optionspec3) ? optionset.valueOf(optionspec3) : new File(file1, "assets/");
        File file3 = optionset.has(optionspec4) ? optionset.valueOf(optionspec4) : new File(file1, "resourcepacks/");
        String s5 = optionset.has(optionspec10) ? optionspec10.value(optionset) : optionspec9.value(optionset);
        String s6 = optionset.has(optionspec17) ? optionspec17.value(optionset) : null;
        Session session = new Session(optionspec9.value(optionset), s5, optionspec11.value(optionset), optionspec18.value(optionset));
        assert propertymap1 != null;
        assert propertymap != null;
        GameConfiguration gameconfiguration = new GameConfiguration(new GameConfiguration.UserInformation(session, propertymap, propertymap1, proxy), new GameConfiguration.DisplayInformation(i, j, flag, flag1), new GameConfiguration.FolderInformation(file1, file3, file2, s6), new GameConfiguration.GameInformation(flag2, s3, s4));
        Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread")
        {
            public void run()
            {
                Minecraft.stopIntegratedServer();
            }
        });
        Thread.currentThread().setName("Client thread");
        (new Minecraft(gameconfiguration)).run();
    }

    /**
     * Returns whether a string is either null or empty.
     */
    private static boolean isNullOrEmpty(String str)
    {
        return str != null && !str.isEmpty();
    }

    public static final class Hash {
        private Hash() { }
        public static final class MD5 {
            private MD5() { }
            private static final MessageDigest hasher;

            static {
                try {
                    hasher = MessageDigest.getInstance("MD5");
                } catch (Exception ignored) {
                    throw new RuntimeException();
                }
            }

            public static String hash(String str) {
                return new String(hash(str.getBytes(StandardCharsets.UTF_8)));
            }

            public static byte[] hash(byte[] bytes) {
                hasher.update(bytes);
                final StringBuilder hash = new StringBuilder();
                for (final byte b: hasher.digest()) {
                    hash.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
                } return hash.toString().getBytes(StandardCharsets.UTF_8);
            }
        }

        public static final class SHA512 {
            private SHA512() { }
            private static final MessageDigest hasher;
            static {
                try {
                    hasher = MessageDigest.getInstance("SHA-512");
                } catch (Exception ignored) {
                    throw new RuntimeException();
                }
            }

            public static String hash(String str) {
                return new String(hash(str.getBytes(StandardCharsets.UTF_8)));
            }

            public static byte[] hash(byte[] bytes) {
                hasher.update(bytes);
                final StringBuilder hash = new StringBuilder();
                for (final byte b: hasher.digest()) {
                    hash.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
                } return hash.toString().getBytes(StandardCharsets.UTF_8);
            }
        }
    }

}
