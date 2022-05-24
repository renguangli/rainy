package com.rainy.common.encrypt;

import cn.hutool.core.io.IoUtil;
import com.rainy.common.util.Sm4Utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class EncryptionHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public EncryptionHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String body = IoUtil.read(super.getInputStream(), Charset.forName(this.getCharacterEncoding()));
        String decryptBody = Sm4Utils.decrypt(body);
        ByteArrayInputStream input = new ByteArrayInputStream(decryptBody.getBytes(getCharacterEncoding()));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return input.read();
            }
        };
    }

    @Override
    public String getParameter(String name) {
        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return super.getParameterMap();
    }

}
