package com.example.bai2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import Adapter.NoteAdapter;
import Model.Note;
import Utils.ResultCode;
import Utils.Toast;

public class MainActivity extends AppCompatActivity {
    private NoteAdapter noteAdapter;
    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();
        setEvent();
    }

    private void addControl() {
        noteAdapter = new NoteAdapter();
    }

    private void setEvent() {
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ListView listViewNote = findViewById(R.id.list_note);
        listViewNote.setAdapter(noteAdapter);
        listViewNote.setOnItemClickListener(onItemClickListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Click adding option
        if (id == R.id.add) {
            Intent noteDetails = new Intent(getApplicationContext(), NoteActivity.class);
            startActivityForResult(noteDetails, REQUEST_CODE);
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Save option
        if (resultCode == ResultCode.SAVE && data != null) {
            Note note = data.getParcelableExtra("note");

            // Add (-1 is default value of id, the id is set based on the size of the list)
            if (note.getId() == -1) {
                // Set id by list size
                note.setId(noteAdapter.getCount() + 1);
                noteAdapter.addItem(note);
                Toast.show(this, "Successfully add note");
            }
            // Update
            else {
                noteAdapter.updateItem(note);
                Toast.show(this, "Successfully update note");
            }
        }

        // Delete option
        if (resultCode == ResultCode.DELETE && data != null) {
            int position = data.getIntExtra("position", -1);
            noteAdapter.removeItem(position);
            Toast.show(this, "Successfully delete a note");
        }
    }

    // Click a note will enter its details activity
    @NonNull
    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent noteDetailsIntent = new Intent(getApplicationContext(), NoteActivity.class);
                Bundle bundle = new Bundle();
                Note note = noteAdapter.getItem(position);

                bundle.putInt("position", position);
                bundle.putParcelable("note", note);

                noteDetailsIntent.putExtras(bundle);
                startActivityForResult(noteDetailsIntent, REQUEST_CODE);
            }
        };
    }
}