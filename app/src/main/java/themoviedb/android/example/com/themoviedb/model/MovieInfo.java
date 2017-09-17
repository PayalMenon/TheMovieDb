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

    @SerializedName("vote_average")
    @Expose
    Double rating;

    @Expose
    Long id;

    @SerializedName("video")
    @Expose
    boolean isVideo;

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

    public Double getRating() {
        return rating;
    }

    public Long getId() {
        return id;
    }

    public boolean isVideo() {
        return isVideo;
    }
}
