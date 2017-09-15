package themoviedb.android.example.com.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class MovieInfo {

    @SerializedName("title")
    @Expose
    public String movieTitle;

    @SerializedName("overview")
    @Expose
    public String movieOverview;

    @SerializedName("poster_path")
    @Expose
    public String moviePoster;

    @SerializedName("backdrop_path")
    @Expose
    public String movieBackdrop;

    public String getMovieOverview() {
        return movieOverview;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }
}
