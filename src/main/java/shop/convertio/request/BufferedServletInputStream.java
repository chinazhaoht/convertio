package shop.convertio.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufferedServletInputStream extends ServletInputStream {

    private ByteArrayInputStream byteArrayInputStream;

    public BufferedServletInputStream(ByteArrayInputStream byteArrayInputStream){
        this.byteArrayInputStream = byteArrayInputStream;
    }
    @Override
    public int read() throws IOException {
        return byteArrayInputStream.read();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }
}
