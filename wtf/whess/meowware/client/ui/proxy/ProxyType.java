package wtf.whess.meowware.client.ui.proxy;

public enum ProxyType {
    NONE("NONE", 0),
    SOCKS5("SOCKS5", 1),
    SOCKS4("SOCKS4", 2),
    HTTP("HTTP", 3);

    ProxyType(final String name, final int ordinal) {
    }
}