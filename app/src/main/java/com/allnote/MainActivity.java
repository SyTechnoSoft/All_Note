package com.allnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.allnote.Activity.Insert_Activity;
import com.allnote.Adapter.NotesAdatper;
import com.allnote.Model.Notes;
import com.allnote.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btn_add;
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    private TextView emptyView;
    NotesAdatper notesAdatper;
    TextView noFilter, HighToLow, LowToHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.newNotes);
        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_textView);
        noFilter = findViewById(R.id.no_filter);
        HighToLow = findViewById(R.id.highToLow);
        LowToHigh = findViewById(R.id.lowToHigh);

        noFilter.setBackgroundResource(R.drawable.filter_selected);
        noFilter.setOnClickListener(v -> {
            loadData(0);
            noFilter.setBackgroundResource(R.drawable.filter_selected);
            HighToLow.setBackgroundResource(R.drawable.filter_bg);
            LowToHigh.setBackgroundResource(R.drawable.filter_bg);

        });
        HighToLow.setOnClickListener(v -> {
            loadData(1);
            noFilter.setBackgroundResource(R.drawable.filter_bg);
            HighToLow.setBackgroundResource(R.drawable.filter_selected);
            LowToHigh.setBackgroundResource(R.drawable.filter_bg);
        });
        LowToHigh.setOnClickListener(v -> {
            loadData(2);
            noFilter.setBackgroundResource(R.drawable.filter_bg);
            HighToLow.setBackgroundResource(R.drawable.filter_bg);
            LowToHigh.setBackgroundResource(R.drawable.filter_selected);

        });


        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);

        inAppUpdate();
        btn_add.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, Insert_Activity.class)));

        /*notesViewModel.getAllNotes.observe(this, notes -> {
            //recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            notesAdatper = new NotesAdatper(MainActivity.this, notes);
            recyclerView.setAdapter(notesAdatper);
            if (notes.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                startActivity(new Intent(MainActivity.this, Insert_Activity.class));

            } else {
                recyclerView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        });*/
        notesViewModel.getAllNotes.observe(this, this::setAdapter);
    }

    private void loadData(int i) {
        if (i == 0) {
            notesViewModel.getAllNotes.observe(this, this::setAdapter);
        } else if (i == 1) {
            notesViewModel.hightolow.observe(this, this::setAdapter);
        } else if (i == 2) {
            notesViewModel.lowtohigh.observe(this, this::setAdapter);
        }
    }


    public void setAdapter(List<Notes> notes) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        notesAdatper = new NotesAdatper(MainActivity.this, notes);
        recyclerView.setAdapter(notesAdatper);
        if (notes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            startActivity(new Intent(MainActivity.this, Insert_Activity.class));

        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Awesome...\n Please install this App.");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Download \n" +
                    " *All Note* \n" +
                    " App from Google Play Store, this app is for Note any things \n" +
                    "https://play.google.com/store/apps/details?id=com.allnote");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            Toast.makeText(this, "Saurabh Yadav says Thanks.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Please share now.", Toast.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.rate) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.allnote"));
            intent.setPackage("com.android.vending");
            startActivity(intent);
        } else if (item.getItemId() == R.id.moreapps) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/collection/cluster?clp=igM4ChkKEzU0NTE5NTM4NDY2NTc2NTM3OTYQCBgDEhkKEzU0NTE5NTM4NDY2NTc2NTM3OTYQCBgDGAA%3D:S:ANO1ljLCYeQ&gsr=CjuKAzgKGQoTNTQ1MTk1Mzg0NjY1NzY1Mzc5NhAIGAMSGQoTNTQ1MTk1Mzg0NjY1NzY1Mzc5NhAIGAMYAA%3D%3D:S:ANO1ljK_aU8"));
            intent.setPackage("com.android.vending");
            startActivity(intent);
        } else if (item.getItemId() == R.id.app_bar_search) {

            SearchView searchView = (SearchView) item.getActionView();
            assert searchView != null;
            searchView.setQueryHint("Search notes here...");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(MainActivity.this, "" + query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    NotesFilter(newText);

                    return true;
                }
            });

        }

        return super.onOptionsItemSelected(item);

    }

    private void NotesFilter(String newText) {
        Toast.makeText(this, "" + newText, Toast.LENGTH_SHORT).show();

    }

    private void inAppUpdate() {

        final AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        // Returns an intent object that you use to check for an update.

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(result -> {
            if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, this, 111);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });//in app update
    }
}