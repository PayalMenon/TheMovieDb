package themoviedb.android.example.com.themoviedb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CastDetails {

    @Expose
    String name;

    @SerializedName("profile_path")
    @Expose
    String profileUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getName() {
        return name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
