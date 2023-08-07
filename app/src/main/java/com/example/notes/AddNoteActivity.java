package com.example.notes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import io.realm.Realm;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        EditText titleInput = findViewById(R.id.titleinput);
        EditText descriptionInput = findViewById(R.id.descriptioninput);
        MaterialButton saveBtn = findViewById(R.id.savebutton);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        saveBtn.setOnClickListener(view -> {
            String title = titleInput.getText().toString();
            String description = descriptionInput.getText().toString();
            long createdTime = System.currentTimeMillis();

            realm.beginTransaction();
            Note note = realm.createObject(Note.class);
            note.setTitle(title);
            note.setDescription(description);
            note.setCreatedTime(createdTime);
            realm.commitTransaction();
            Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
            finish();

        });
    }
}