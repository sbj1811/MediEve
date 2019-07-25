package com.sjani.medieve.UI.MainList;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sjani.medieve.Models.Event;
import com.sjani.medieve.Models.User;
import com.sjani.medieve.R;
import com.sjani.medieve.UI.AddEvent.AddEventActivity;
import com.sjani.medieve.UI.AddEvent.AddEventFragment;
import com.sjani.medieve.UI.EventListViewModel;
import com.sjani.medieve.UI.ViewModelFactory;
import com.sjani.medieve.Utils.FactoryUtils;
import com.sjani.medieve.Utils.ListItemListerner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity with user card and medication event list
 */
public class MainActivity extends AppCompatActivity implements ListItemListerner {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.user_sex)
    TextView userSex;
    @BindView(R.id.user_dob)
    TextView userDob;
    @BindView(R.id.user_disease)
    TextView userDisease;
    EventListViewModel eventListViewModel;
    ListAdapter adapter;
    LinearLayoutManager layoutManager;
    AddEventFragment eventFragment;
    int mStackLevel = 0;

    /**
     * Creates the View
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        adapter = new ListAdapter(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ViewModelFactory factory = FactoryUtils.getFactory(this);
        eventListViewModel = ViewModelProviders.of(this,factory).get(EventListViewModel.class);
        eventListViewModel.getUsers().observe(this, Users -> {
            if(Users!=null && Users.size()!=0) updateUserCardUI(Users);
        });
        eventListViewModel.getEventsforUser().observe(this, Events -> {
            if(Events!=null && Events.size()!=0) updateList(Events);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showAddEvent());

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build()
        );
    }

    private void showAddEvent() {
        Intent intent = new Intent(this, AddEventActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent);
            overridePendingTransition(R.xml.slide_from_right, R.xml.slide_to_left);
        } else {
            startActivity(intent);
        }
    }

    /**
     * Updates Event List Recycler View
     * @param Events List of Events
     */
    private void updateList(List<Event> Events) {
        List<Event> reversedList = new ArrayList<>();
        for (int i = Events.size()-1; i >= 0; i--) {
            reversedList.add(Events.get(i));
        }
        Collections.sort(reversedList, (o1, o2) -> {
            String dateTime1 = o1.getDatetime();
            String dateTime2 = o2.getDatetime();
            return dateTime2.compareTo(dateTime1);
        });
        adapter.swapResults(reversedList);
        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * updated User Card view
     * @param Users List of Users
     */
    private void updateUserCardUI(List<User> Users) {
        userName.setText(Users.get(0).getName());
        String address = String.format("%s %s", Users.get(0).getAddress1(), Users.get(0).getAddress2());
        userAddress.setText(address);
        String sex = Users.get(0).getSex().substring(0,1).toUpperCase()+Users.get(0).getSex().substring(1);
        userSex.setText(sex);
        userDob.setText(Users.get(0).getDob());
        String disease = Users.get(0).getDisease().substring(0,1).toUpperCase()+Users.get(0).getDisease().substring(1);
        userDisease.setText(disease);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String name, String dateTime) {
        String message = "You took "+name+" at "+dateTime;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
