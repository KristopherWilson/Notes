package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class NotesActivity extends AppCompatActivity {

    RealmResults<Note> notesList;
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        ImageButton addNoteBtn = findViewById(R.id.addnewnotebutton);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterRealm(s);
                return false;
            }
        });

        addNoteBtn.setOnClickListener(view -> startActivity(new Intent(NotesActivity.this,AddNoteActivity.class)));

        notesList = realm.where(Note.class).findAll().sort("createdTime", Sort.DESCENDING);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter  =  new MyAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(notes -> myAdapter.notifyDataSetChanged());


    }

    private void filterRealm(String s) {
        Realm realm = Realm.getDefaultInstance();

        RealmQuery<Note> noteQuery = realm.where(Note.class)
                .contains("title",s)
                .or()
                .contains("description",s);
        RealmResults<Note> queryList = noteQuery.findAll().sort("createdTime", Sort.DESCENDING);
        myAdapter.setFilteredRealm(queryList);
    }
}