package thirdweek.madcamp.walkitalki.Retrofit;

import retrofit2.Retrofit;

public class APIUtils {

    private APIUtils(){

    };

    public static final String API_URL = "http://143.248.140.106:1380";

    public static IMyService getUserService(){
        return RetrofitClient.getClient(API_URL).create(IMyService.class);
    }
}
