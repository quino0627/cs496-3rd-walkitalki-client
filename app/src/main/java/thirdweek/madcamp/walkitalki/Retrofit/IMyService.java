package thirdweek.madcamp.walkitalki.Retrofit;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import thirdweek.madcamp.walkitalki.Model.Chat;
import thirdweek.madcamp.walkitalki.Model.Post;

public interface IMyService {
    @GET("/api/mapchat/all")
    Call<List<Chat>> getChats();

    @GET("/api/mappost/all")
    Call<List<Post>> getPosts();
}
