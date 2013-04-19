package info.netinho.util;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSONfy {

    private static JSONfy instance;
    private String callback;
    private HttpServletRequest request;
    private HttpServletResponse response;

    private JSONfy() {
        this.callback = new String();
    }

    private JSONfy(String callback) {
        this.callback = callback;
    }

    public static synchronized JSONfy getInstance() {
        if (instance == null) {
            instance = new JSONfy();
        }
        return instance;
    }

    public static synchronized JSONfy getInstance(String callback) {
        if (instance == null) {
            instance = new JSONfy(callback);
        }
        return instance;
    }

    public static synchronized boolean isIstancied() {
        return instance != null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public boolean hasCallback() {
        return (this.callback != null) && (!this.callback.isEmpty());
    }

    public void show(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        this.request = request;
        this.response = response;
        show(object);
    }

    public void show(Object object)
            throws IOException {
        this.callback = this.request.getParameter("callback");
        if (this.callback == null) {
            this.callback = new String();
        }

        this.response.setContentType("text/javascript;charset=UTF-8");
        PrintWriter out = this.response.getWriter();
        try {
            if (!this.callback.isEmpty()) {
                out.print(this.callback + "(");
            }

            Gson gson = new Gson();
            out.print(gson.toJson(object));

            if (!this.callback.isEmpty()) {
                out.print(")");
            }
        } finally {
            out.close();
        }
    }

    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(java.util.Date.class, new JsonSerializer<java.util.Date>() {
            @Override
            public JsonElement serialize(java.util.Date src, Type typeOfSrc, JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(info.netinho.util.Date.formatDate(src));
            }
        });
        builder.registerTypeAdapter(java.util.Date.class, new JsonDeserializer<java.util.Date>() {
            @Override
            public java.util.Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                String date = json.getAsJsonPrimitive().getAsString();
                if (!date.contains(":")) {
                    date += " 15:00:00";
                }

                SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
                try {
                    return spf.parse(date);
                } catch (ParseException e) {
                    //e.printStackTrace();
                    return null;
                }
            }
        });

        return builder.setPrettyPrinting().create();
    }
}