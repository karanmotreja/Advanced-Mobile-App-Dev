package com.example.karanmotreja.lab10;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TeamsMainActivity extends AppCompatActivity {

    List<Teams> teams = new ArrayList<>();


    DatabaseReference teamref = FirebaseDatabase.getInstance().getReference("teams");

    // adapter
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // query
        Query query = FirebaseDatabase.getInstance().getReference().child("teams");

       // parser
        SnapshotParser<Teams> parser = new SnapshotParser<Teams>() {
            @NonNull
            @Override
            public Teams parseSnapshot(@NonNull DataSnapshot snapshot) {
                Teams newTeam = new Teams(snapshot.getKey(),
                        Objects.requireNonNull(snapshot.child("name").getValue()).toString(),
                        snapshot.child("url").getValue().toString());
                teams.add(newTeam);
                return newTeam;
            }
        };


        FirebaseRecyclerOptions<Teams> options = new FirebaseRecyclerOptions.Builder<Teams>()
                .setQuery(query, parser)
                .build();

        // adapter
        adapter = new FirebaseRecyclerAdapter<Teams, TeamViewHolder>(options) {

            @NonNull
            @Override
            public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
                return new TeamViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final TeamViewHolder holder, int position, @NonNull final Teams model) {

                holder.setTeamName(model.getName());

                //onclick listener
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position = holder.getAdapterPosition();
                        String teamURL = teams.get(position).getUrl();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(teamURL));

                        startActivity(intent);
                    }
                });


                holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu menu, final View v, ContextMenu.ContextMenuInfo menuInfo) {

                        menu.setHeaderTitle("Delete " + model.getName());

                        menu.add(1, 1, 1, "Yes").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                int position = holder.getAdapterPosition();
                                String teamid = teams.get(position).getId();
                                teamref.child(teamid).removeValue();
                                Snackbar.make(v, "Item removed", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                                return false;
                            }
                        });
                        menu.add(2, 2, 2, "No");
                    }
                });
            }
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // add items via FAB
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(TeamsMainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameEditText = new EditText(TeamsMainActivity.this);
                nameEditText.setHint("Team Name");
                layout.addView(nameEditText);

                final EditText urlEditText = new EditText(TeamsMainActivity.this);
                urlEditText.setHint("URL");
                layout.addView(urlEditText);


                AlertDialog.Builder dialog = new AlertDialog.Builder(TeamsMainActivity.this);
                dialog.setTitle("Add Team");
                dialog.setView(layout);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String teamName = nameEditText.getText().toString();
                        String teamURL = urlEditText.getText().toString();
                        if (teamName.trim().length() > 0) {

                            String key = teamref.push().getKey();

                            Teams newTeam = new Teams(key, teamName, teamURL);

                            teamref.child(key).child("name").setValue(newTeam.getName());
                            teamref.child(key).child("url").setValue(newTeam.getUrl());
                        }
                    }
                });
                dialog.setNegativeButton("Cancel", null);
                dialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_teams_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
