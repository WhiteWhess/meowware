package wtf.whess.meowware.client.ui.proxy;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import wtf.whess.meowware.client.api.utilities.misc.MathUtil;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ProxyManager
{
    public static List<ProxyItem> proxyItemList = new ArrayList<>();

    public static void loadProxies(final String www) {
        try {
            final URL website = new URL(www);
            final URLConnection connection = website.openConnection();
            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.contains(":")) {
                    final String ip = inputLine.split(":")[0];
                    final int port = Integer.parseInt(inputLine.split(":")[1]);
                    ProxyManager.proxyItemList.add(new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(ip, port)));
                }
                else {
                    System.out.println(inputLine + " error");
                }
            }
        }
        catch (MalformedURLException e) {
            System.err.println("Page does not exist!");
        }
        catch (IOException e2) {
            System.err.println("No internet!");
        }
        System.out.println(ProxyManager.proxyItemList.size() + " Proxies loaded.");
    }

    public static void downloadFile() {
        try {
            final URL website = new URL("https://api.proxyscrape.com/?request=getproxies&proxytype=socks5&timeout=500000&country=all&anonymity=elite&ssl=yes");
            final URL website1 = new URL("https://api.proxyscrape.com/?request=getproxies&proxytype=socks5&timeout=500000&country=all&anonymity=elite&ssl=yes");
            final URL website2 = new URL("https://api.proxyscrape.com/?request=getproxies&proxytype=http&timeout=500000&country=all&anonymity=elite&ssl=yes");
            final ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            final ReadableByteChannel rbc1 = Channels.newChannel(website1.openStream());
            final ReadableByteChannel rbc2 = Channels.newChannel(website2.openStream());
            final FileOutputStream fos = new FileOutputStream("socks5.txt");
            final FileOutputStream fos1 = new FileOutputStream("socks4.txt");
            final FileOutputStream fos2 = new FileOutputStream("http.txt");
            fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            fos1.getChannel().transferFrom(rbc1, 0L, Long.MAX_VALUE);
            fos1.close();
            rbc1.close();
            fos2.getChannel().transferFrom(rbc2, 0L, Long.MAX_VALUE);
            fos2.close();
            rbc2.close();
        }
        catch (Exception ignored) {}
    }

    public static void loadProxiesFromFile() {
        new Thread(() -> {
            try {
                final File file = new File("/MeowWare/socks5.txt");
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String readString = "";
                while ((readString = bufferedReader.readLine()) != null) {
                    if (readString.contains(":")) {
                        ProxyManager.proxyItemList.add(new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(readString.split(":")[0], Integer.parseInt(readString.split(":")[1]))));
                    }
                }

                final File file1 = new File("/MeowWare/socks4.txt");
                final BufferedReader bufferedReader1 = new BufferedReader(new FileReader(file1));
                String readString1 = "";
                while ((readString1 = bufferedReader1.readLine()) != null) {
                    if (readString1.contains(":")) {
                        ProxyManager.proxyItemList.add(new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(readString.split(":")[0], Integer.parseInt(readString.split(":")[1]))));
                    }
                }

                final File file2 = new File("/MeowWare/http.txt");
                final BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file2));
                String readString2 = "";
                while ((readString2 = bufferedReader2.readLine()) != null) {
                    if (readString2.contains(":")) {
                        ProxyManager.proxyItemList.add(new ProxyItem(ProxyType.HTTP, new InetSocketAddress(readString.split(":")[0], Integer.parseInt(readString.split(":")[1]))));
                    }
                }
            }
            catch (Exception ignored) {}

            try {
                Document proxyList = Jsoup.connect("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks4").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                Document proxyList = Jsoup.connect("https://www.proxy-list.download/api/v1/get?type=socks4").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                Document proxyList = Jsoup.connect("https://openproxylist.xyz/socks4.txt").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                Document proxyList = Jsoup.connect("https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/socks4.txt").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                Document proxyList = Jsoup.connect("https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/socks5.txt").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                Document proxyList = Jsoup.connect("https://raw.githubusercontent.com/TheSpeedX/SOCKS-List/master/http.txt").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.HTTP, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                for (int k = 64; k < 1600; k += 64) {
                    Document proxyList3 = Jsoup.connect("https://hidemy.name/ru/proxy-list/?type=4&start=" + k + "#list").get();
                    for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                        try {
                            Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                            String host = elements.get(0).text();
                            int port = Integer.parseInt(elements.get(1).text());
                            proxyItemList.add(new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(host, port)));
                        } catch (Throwable ignored) {}
                    }
                }
            } catch (Throwable ignored) {}

            try {
                for (int k = 64; k < 1600; k += 64) {
                    Document proxyList3 = Jsoup.connect("https://hidemy.name/ru/proxy-list/?type=h&start=" + k + "#list").get();
                    for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                        try {
                            Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                            String host = elements.get(0).text();
                            int port = Integer.parseInt(elements.get(1).text());
                            proxyItemList.add(new ProxyItem(ProxyType.HTTP, new InetSocketAddress(host, port)));
                        } catch (Throwable ignored) {}
                    }
                }
            } catch (Throwable ignored) {}

            try {
                Document proxyList3 = Jsoup.connect("https://www.proxy-list.download/SOCKS5").get();
                for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                    try {
                        Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                        String host = elements.get(0).text();
                        int port = Integer.parseInt(elements.get(1).text());
                        proxyItemList.add(new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(host, port)));
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {}

            try {
                Document proxyList3 = Jsoup.connect("https://www.proxy-list.download/SOCKS4").get();
                for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                    try {
                        Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                        String host = elements.get(0).text();
                        int port = Integer.parseInt(elements.get(1).text());
                        proxyItemList.add(new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(host, port)));
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {}

            try {
                Document proxyList3 = Jsoup.connect("https://www.proxy-list.download/HTTP").get();
                for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                    try {
                        Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                        String host = elements.get(0).text();
                        int port = Integer.parseInt(elements.get(1).text());
                        proxyItemList.add(new ProxyItem(ProxyType.HTTP, new InetSocketAddress(host, port)));
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {}

            try {
                Document proxyList3 = Jsoup.connect("https://www.socks-proxy.net/").get();
                for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                    try {
                        Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                        String host = elements.get(0).text();
                        int port = Integer.parseInt(elements.get(1).text());
                        proxyItemList.add(new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(host, port)));
                    } catch (Throwable ignored) {}
                }
            } catch (Throwable ignored) {}

            try {
                for (int k = 64; k < 1600; k += 64) {
                    Document proxyList3 = Jsoup.connect("https://hidemy.name/ru/proxy-list/?type=5&start=" + k + "#list").get();
                    for (int i = 1; i < proxyList3.getElementsByTag("tr").size(); i++) {
                        try {
                            Elements elements = proxyList3.getElementsByTag("tr").get(i).getElementsByTag("td");
                            String host = elements.get(0).text();
                            int port = Integer.parseInt(elements.get(1).text());
                            proxyItemList.add(new ProxyItem(ProxyType.SOCKS4, new InetSocketAddress(host, port)));
                        } catch (Throwable ignored) {}
                    }
                }
            } catch (Throwable ignored) {}

            try {
                Document proxyList = Jsoup.connect("https://openproxylist.xyz/socks5.txt").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}

            try {
                Document proxyList = Jsoup.connect("https://api.proxyscrape.com/?request=displayproxies&proxytype=socks5").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}
            try {
                Document proxyList = Jsoup.connect("https://www.proxy-list.download/api/v1/get?type=socks5").get();
                proxyItemList.addAll(Arrays.stream(proxyList.text().split(" ")).distinct().map(proxy -> new ProxyItem(ProxyType.SOCKS5, new InetSocketAddress(proxy.split(":")[0], Integer.parseInt(proxy.split(":")[1])))).collect(Collectors.toList()));
            } catch (Throwable ignored) {}

            System.out.println(ProxyManager.proxyItemList.size() + " Proxies loaded.");
        }).start();
    }

    public static ProxyItem genProxy() {
        return ProxyManager.proxyItemList.get(MathUtil.random(0, ProxyManager.proxyItemList.size() - 1));
    }

    public static ProxyItem genProxy(ProxyType proxyType) {
        final ArrayList<ProxyItem> proxies = new ArrayList<>();
        for (ProxyItem proxyItem : proxyItemList) {
            if (proxyItem.type == proxyType)
                proxies.add(proxyItem);
        } return proxies.get(MathUtil.random(0, proxies.size() - 1));
    }

    @Data
    public static class ProxyItem {
        private final ProxyType type;
        private final InetSocketAddress address;
    }

}
