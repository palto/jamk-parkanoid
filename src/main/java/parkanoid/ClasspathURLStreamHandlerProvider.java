package parkanoid;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.spi.URLStreamHandlerProvider;

public class ClasspathURLStreamHandlerProvider extends URLStreamHandlerProvider {

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if ("classpath".equals(protocol)) {
            return new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL u) throws IOException {
                    String path = u.getPath();
                    if(path.startsWith("/")) {
                        path = path.substring(1);
                    }
                    return ClassLoader.getSystemClassLoader().getResource(path).openConnection();
                }
            };
        }
        return null;
    }

}