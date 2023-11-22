package com.example.bai2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

import Adapter.ImageAdapter;
import Model.Note;
import Utils.ResultCode;
import Utils.Toast;

public class NoteActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 100;
    private EditText et_title;
    private  EditText et_content;
    private ListView imageList;
    private ImageAdapter imageAdapter;
    private int position;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_details);

        addControl();
        setDefaultValues();
        setEvent();
    }

    private void addControl() {
        imageList = findViewById(R.id.images);
        et_title = findViewById(R.id.edit_text_title);
        et_content = findViewById(R.id.edit_text_content);
    }

    private void setEvent() {
        // Display tool bar
        Toolbar toolbar = findViewById(R.id.note_toolbar);
        setSupportActionBar(toolbar);

        Button selectImageBtn = findViewById(R.id.chooseImageButton);
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the pick image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        Button datePickerBtn = findViewById(R.id.chooseDateButton);
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(NoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Append selected date to content
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        et_content.setText(et_content.getText() + " " + date);
                    }
                }, year, month, day);

                // Show the dialog
                datePickerDialog.show();
            }
        });

        Button timePickerBtn = findViewById(R.id.chooseTimeButton);
        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current time
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Create TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(NoteActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Append selected time to content
                                String time = hourOfDay + ":" + minute;
                                et_content.setText(et_content.getText() + " " + time);
                            }
                        }, hour, minute, false);

                // Show the dialog
                timePickerDialog.show();
            }
        });
    }

    private void setDefaultValues() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (note == null) {
                // Parent doesn't pass the note data
                note = new Note();
                imageAdapter = new ImageAdapter(this);
            }
            else {
                // Parent pass the note data
                note = bundle.getParcelable("note");
                imageAdapter = new ImageAdapter(this, note.getImages());
            }

            imageList.setAdapter(imageAdapter);
            position = bundle.getInt("position");

            et_title.setText(note.getTitle());
            et_content.setText(note.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            onSave();
        }

        if (id == R.id.delete) {
            onDelete();
        }

        if (id == R.id.cancel) {
            finish();
        }

        if (id == R.id.alarm) {
            setAlarm();
        }

        if (id == R.id.remove_alarm) {
            removeAlarm();
        }

        return true;
    }

    private void removeAlarm() {
        ReminderBroadCast.cancelReminder(NoteActivity.this);
        Toast.show(this, "The alarm is removed");
    }

    private void setAlarm() {
        // Get current time
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(NoteActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Set alarm with the selected time
                        ReminderBroadCast.setReminder(NoteActivity.this, hourOfDay, minute);
                        Toast.show(NoteActivity.this, "Set alarm at " + hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);

        // Show the dialog
        timePickerDialog.show();
    }

    private void onSave() {
        // Get data
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

        // Override note's data with the new one
        note.setTitle(title);
        note.setContent(content);

        // Send result back to MainActivity
        Intent intent = new Intent();
        intent.putExtra("note", note);
        setResult(ResultCode.SAVE, intent);
        finish();
    }

    private void onDelete() {
        // Send position back to MainActivity
        Intent intent = new Intent();
        intent.putExtra("position", position);
        setResult(ResultCode.DELETE, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            try {
                // Get image
                Uri imageUri = data.getData();
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Resize the image to 100x100 (to reduce the size, Parcel is limit to 1MB: https://developer.android.com/reference/android/os/TransactionTooLargeException)
                int desiredSize = 100;
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(image, desiredSize, desiredSize, false);

                imageAdapter.add(resizedBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
