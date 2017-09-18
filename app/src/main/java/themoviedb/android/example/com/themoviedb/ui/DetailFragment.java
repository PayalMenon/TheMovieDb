package themoviedb.android.example.com.themoviedb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import themoviedb.android.example.com.themoviedb.Application;
import themoviedb.android.example.com.themoviedb.R;
import themoviedb.android.example.com.themoviedb.adapter.CastListAdapter;
import themoviedb.android.example.com.themoviedb.model.CastDetails;
import themoviedb.android.example.com.themoviedb.model.CastList;
import themoviedb.android.example.com.themoviedb.model.Genre;
import themoviedb.android.example.com.themoviedb.model.MovieDetails;
import themoviedb.android.example.com.themoviedb.model.VideoDetails;
import themoviedb.android.example.com.themoviedb.model.VideoList;
import themoviedb.android.example.com.themoviedb.services.MovieService;
import themoviedb.android.example.com.themoviedb.util.Constants;

public class DetailFragment extends Fragment {

    @BindView(R.id.detailsTitle)
    TextView titleView;
    @BindView(R.id.detailsReleaseDate)
    TextView releaseDateView;
    @BindView(R.id.detailsOverview)
    TextView overviewView;
    @BindView(R.id.detailsGenre)
    TextView genreView;
    @BindView(R.id.detailsRating)
    RatingBar ratingBarView;
    @BindView(R.id.detailsImage)
    ImageView imageView;
    @BindView(R.id.detailsPlayVideo)
    ImageView videoPlayView;
    @BindView(R.id.detailsCastList)
    RecyclerView castListView;
    private CastListAdapter adapter;
    private List<CastDetails> castList = new ArrayList<>();

    @Inject
    MovieService service;

    public static DetailFragment newInstance(Long id) {

        Bundle args = new Bundle();
        args.putLong(Constants.ID, id);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getNetworkComponent().inject(this);

        castListView.setNestedScrollingEnabled(false);
        adapter = new CastListAdapter(getActivity(), castList);
        castListView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        castListView.setLayoutManager(manager);

        ((MainActivity) getActivity()).getSupportActionBar().hide();

        getMovie();
    }

    private void getMovie() {
        Long id = getArguments().getLong(Constants.ID, 0L);
        Call<MovieDetails> call = service.getMovieDetails(id);
        getMovieDetails(call);
    }

    private void getMovieDetails(Call<MovieDetails> call) {

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful()) {
                    MovieDetails details = response.body();
                    int duration = details.getDuration();
                    String length = duration + "m";
                    if (duration > 60) {
                        length = duration / 60 + "h" + duration % 60 + "m";
                    }
                    titleView.setText(details.getTitle() + " ( " + length + " ) ");
                    releaseDateView.setText(getResources().getString((R.string.released_on), details.getReleased()));
                    overviewView.setText(details.getOverview());
                    ratingBarView.setRating((details.getRating().floatValue() / 2));

                    List<Genre> genre = details.getGenres();
                    if (genre != null && genre.size() > 0) {
                        StringBuilder genreList = new StringBuilder();
                        for (Genre genre1 : genre) {
                            genreList.append(genre1.getName() + ",");
                        }
                        genreView.setText(genreList);
                    }

                    String imageUrl = Constants.BACKDROP_IMAGE_URL + details.getBackdropUrl();
                    Picasso.with(getActivity()).load(imageUrl)
                            .placeholder(R.drawable.placeholder)
                            .into(imageView);

                    geVideoList();
                    getCastList();
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.fetch_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void geVideoList() {
        Long id = getArguments().getLong(Constants.ID, 0L);
        Call<VideoList> call = service.getVideoList(id);
        getVideosDetail(call);

    }

    private void getCastList() {
        Long id = getArguments().getLong(Constants.ID, 0L);
        Call<CastList> call = service.getMovieCastList(id);
        getMovieCaseList(call);
    }

    private void getMovieCaseList(Call<CastList> call) {

        call.enqueue(new Callback<CastList>() {
            @Override
            public void onResponse(Call<CastList> call, Response<CastList> response) {
                if (response.isSuccessful()) {
                    CastList cast = response.body();
                    castList = cast.getCast();
                    adapter.updateDateSet(castList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CastList> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.cast_fetch_failed), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getVideosDetail(Call<VideoList> call) {

        call.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                if (response != null && response.isSuccessful()) {
                    VideoList info = response.body();
                    List<VideoDetails> list = info.getResults();
                    if (list != null && list.size() > 0) {
                        videoPlayView.setVisibility(View.VISIBLE);
                        videoPlayView.setAlpha(0.2f);
                    } else {
                        videoPlayView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {

            }
        });
    }
}
