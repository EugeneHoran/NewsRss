package eugene.com.newsrss.api;

import android.arch.lifecycle.LiveData;

import eugene.com.livelib.ApiResponse;
import eugene.com.newsrss.model.RssResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RssService {

    @GET("v1/api.json")
    LiveData<ApiResponse<RssResponse>> getNews(@Query("rss_url") String url);
}
