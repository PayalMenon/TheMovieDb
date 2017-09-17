package themoviedb.android.example.com.themoviedb.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import themoviedb.android.example.com.themoviedb.Application;
import themoviedb.android.example.com.themoviedb.R;
import themoviedb.android.example.com.themoviedb.adapter.MovieListAdapter;
import themoviedb.android.example.com.themoviedb.model.MovieInfo;
import themoviedb.android.example.com.themoviedb.model.MovieList;
import themoviedb.android.example.com.themoviedb.services.MovieService;
import themoviedb.android.example.com.themoviedb.util.Constants;

public class ListFragment extends Fragment implements MainActivity.FragmentListener {

    private List<MovieInfo> movieList = new ArrayList<>();
    private RecyclerView listView;
    private MovieListAdapter adapter;

    @Inject
    MovieService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ((Application) getActivity().getApplication()).getNetworkComponent().inject(this);

        listView = getActivity().findViewById(R.id.list_view);

        adapter = new MovieListAdapter(getActivity(), movieList, this);
        listView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(manager);

        ((MainActivity) getActivity()).getSupportActionBar().show();
        ((MainActivity) getActivity()).setFragmentListener(this);
    }

    @Override
    public void getMovieList(Call<MovieList> call) {
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response != null && response.isSuccessful()) {
                    MovieList object = response.body();
                    movieList = object.getResults();
                    adapter.updateDateSet(movieList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.fetch_failed, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {

        MovieInfo info = movieList.get(position);

        Fragment detailsFragment = DetailFragment.newInstance(info.getId());
        getFragmentManager().beginTransaction()
                .addToBackStack(Constants.LIST_FRAGMENT)
                .replace(R.id.fragment_container, detailsFragment, Constants.DETAIL_FRAGMENT)
                .commit();
    }
}
