package com.example.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.home.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add_Task extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewDate;
    private TextView textViewTime;

    private Calendar calendar;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        editTextTitle = findViewById(R.id.titleEditText);
        editTextDescription = findViewById(R.id.descriptionEditText);
        textViewDate = findViewById(R.id.dateValueText);
        textViewTime = findViewById(R.id.timeValueText);
        CardView cardViewDate = findViewById(R.id.dateCardView);
        CardView cardViewTime = findViewById(R.id.timeCardView);
        Button saveButton = findViewById(R.id.saveButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        cardViewDate.setOnClickListener(v -> showDatePicker());
        cardViewTime.setOnClickListener(v -> showTimePicker());

        saveButton.setOnClickListener(v -> saveTask());
        cancelButton.setOnClickListener(v -> finish());
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            setTitle("עריכת משימה");
            editTextTitle.setText(intent.getStringExtra("title"));
            editTextDescription.setText(intent.getStringExtra("description"));
            textViewDate.setText(intent.getStringExtra("date"));
            textViewTime.setText(intent.getStringExtra("time"));
        } else {
            setTitle("משימה חדשה");
            updateDateText();
            updateTimeText();
        }
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateDateText();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }
    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    updateTimeText();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateDateText() {
        textViewDate.setText(dateFormatter.format(calendar.getTime()));
    }

    private void updateTimeText() {
        textViewTime.setText(timeFormatter.format(calendar.getTime()));
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String date = textViewDate.getText().toString();
        String time = textViewTime.getText().toString();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "אנא הכנס כותרת", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("title", title);
        data.putExtra("description", description);
        data.putExtra("date", date);
        data.putExtra("time", time);

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            data.putExtra("id", id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}