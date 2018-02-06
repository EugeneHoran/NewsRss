package eugene.com.newsrss.api;


import java.util.concurrent.TimeUnit;

import eugene.com.livelib.LiveDataCallAdapterFactory;
import eugene.com.newsrss.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RssClient {
    private static final int TIMEOUT = 15;
    private static RssClient instance = null;

    public static RssClient getInstance() {
        if (instance == null) {
            instance = new RssClient();
        }
        return instance;
    }
    

    public RssService create() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BuildConfig.RSS_URL);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(new LiveDataCallAdapterFactory());
        builder.client(http3Client());
        builder.build();
        return builder.build().create(RssService.class);
    }

    private OkHttpClient http3Client() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor());
        }
        return builder.build();
    }

    /**
     * Log responses
     * <p>
     * logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
     * logging.setLevel(HttpLoggingInterceptor.Level.BODY);
     * logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
     *
     * @return Logging Interceptor
     */
    private HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return logging;
    }
}
