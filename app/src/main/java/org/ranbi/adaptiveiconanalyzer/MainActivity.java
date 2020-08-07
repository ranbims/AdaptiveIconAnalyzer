package org.ranbi.adaptiveiconanalyzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<AppEntry>> {

    // This is the Adapter being used to display the list's data.
    AppGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.grid);
        mAdapter = new AppGridAdapter();
        recyclerView.setAdapter(mAdapter);
//        // Create an empty adapter we will use to display the loaded data.
//        mAdapter = new AppListAdapter(this);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<List<AppEntry>> onCreateLoader(int id, @Nullable Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader with no arguments, so it is simple.
        return AppListLoader.getInstance(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<AppEntry>> loader, List<AppEntry> data) {
        // Set the new data in the adapter.
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<AppEntry>> loader) {
        // Clear the data in the adapter.
        mAdapter.setData(null);
    }
}