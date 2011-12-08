package info.netinho.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebServlet {

    public static void sendHeaders(String fileName, int contentLen, HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        String ua = request.getHeader("User-Agent").toLowerCase();
        boolean isIE = (ua.indexOf("msie 6.0") != -1) || (ua.indexOf("msie 7.0") != -1);

        String encName = URLEncoder.encode(fileName, "UTF-8");

        if (request.isSecure()) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Expires", "-1");
            response.addHeader("Cache-Control", "no-cache");
        } else {
            response.addHeader("Cache-Control", "private");
            response.addHeader("Pragma", "public");
        }

        if (isIE) {
            response.addHeader("Content-Disposition", "attachment;filename=\"" + encName + "\"");
            response.addHeader("Connection", "close");
            response.setContentType("application/force-download;name =\"" + encName + "\"");
        } else {
            response.addHeader("Content-Disposition", "attachment;filename=\"" + encName + "\"");
            response.setContentType("application/octet-stream;name=\"" + encName + "\"");
            if (contentLen > 0) {
                response.setContentLength(contentLen);
            }
        }
    }
}