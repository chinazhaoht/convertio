package shop.convertio.request;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;


/**
 * 包装类，使得重复调用request.getInputStream能读到数据
 */
public class RepeatReadServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    public RepeatReadServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        body = IOUtils.toByteArray(super.getInputStream());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        BufferedServletInputStream bufferedServletInputStream = new BufferedServletInputStream(byteArrayInputStream);
        return bufferedServletInputStream;
    }
}
