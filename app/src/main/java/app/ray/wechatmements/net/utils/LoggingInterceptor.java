package app.ray.wechatmements.net.utils;

import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by Asura on 2017/6/5.
 */

public class LoggingInterceptor implements Interceptor {
    private final Charset charset = Charset.forName("UTF-8");

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.method() + " " + request.url().toString());
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        stringBuilder.append(" ").append(String.format("%d %sms", response.code(), tookMs));
        RequestBody requestBody = request.body();
        if (null != requestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            stringBuilder.append("\n").append(buffer.readString(charset));
        }
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            stringBuilder.append("\n").append(responseBody.contentLength());
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer().clone();
            if (isPlaintext(buffer)) {
                MediaType mediaType = responseBody.contentType();
                if (mediaType != null) {
                    stringBuilder.append("\n").append(buffer.readString(mediaType.charset(charset)));
                } else {
                    stringBuilder.append("\n").append(buffer.readString(charset));
                }
            }
        }
        Log.d("Http logger", stringBuilder.toString());
        return response;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }
}