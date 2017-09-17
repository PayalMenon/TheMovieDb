package themoviedb.android.example.com.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails {

    @Expose
    String overview;

    @Expose
    Long id;

    @SerializedName("backdrop_path")
    @Expose
    String backdropUrl;

    @SerializedName("poster_path")
    @Expose
    String posterUrl;

    @SerializedName("release_date")
    @Expose
    String released;

    @SerializedName("runtime")
    @Expose
    int duration;

    @Expose
    String title;

    @SerializedName("vote_average")
    @Expose
    Double rating;

    List<Genre> genres;

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public int getDuration() {
        return duration;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Long getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getReleased() {
        return released;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }
}
