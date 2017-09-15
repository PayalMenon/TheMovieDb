package themoviedb.android.example.com.themoviedb.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import themoviedb.android.example.com.themoviedb.model.MovieList;

public interface MovieService {

    @GET("{movieType}?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed")
    Call<MovieList> getMovies(@Path("movieType") String movieType);
}
