package com.example.modernartui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LinearLayoutActivity extends AppCompatActivity {
    View red, green, blue, teal, yellow;
    SeekBar sb;
    int sbMaxValue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout);
        addControl();
        addEvent();
    }

    private void addEvent() {
        sb.setMax(sbMaxValue);
        changeColorEvent();
    }

    private void changeColorEvent() {
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int[] redArray = {255, 0, 0};
                int[] greenArray = {0, 255, 0};
                int[] blueArray = {0, 0, 255};
                int[] yellowArray = {255, 255, 0};
                int[] tealArray = {0, 255, 255};

                // Make incremental color value changes
                // Effect: Progressively change to gray and then to desired (another) color

                redArray[0] = redArray[0] - (redArray[0] / sbMaxValue) * i;
                redArray[1] = redArray[1] + (229 / sbMaxValue) * i;
                redArray[2] = redArray[2] + (238 / sbMaxValue) * i;

                greenArray[0] = greenArray[0] + (123 / sbMaxValue) * i;
                greenArray[1] = greenArray[1] - (greenArray[1] / sbMaxValue) * i;
                greenArray[2] = greenArray[2] + (201 / sbMaxValue) * i;

                blueArray[0] = blueArray[0] + (197 / sbMaxValue) * i;
                blueArray[1] = blueArray[1] + (200 / sbMaxValue) * i;
                blueArray[2] = blueArray[2] - (blueArray[2] / sbMaxValue) * i;

                yellowArray[0] = yellowArray[0] - (yellowArray[0] / sbMaxValue) * i;
                yellowArray[1] = yellowArray[1] - (yellowArray[1] / sbMaxValue) * i;
                yellowArray[2] = yellowArray[2] + (255 / sbMaxValue) * i;

                tealArray[0] = tealArray[0] + (255 / sbMaxValue) * i;
                tealArray[1] = tealArray[1] - (tealArray[1] / sbMaxValue) * i;
                tealArray[2] = tealArray[2] - (tealArray[2] / sbMaxValue) * i;

                // Set the color

                red.setBackgroundColor(Color.rgb(redArray[0], redArray[1], redArray[2]));
                green.setBackgroundColor(Color.rgb(greenArray[0], greenArray[1], greenArray[2]));
                blue.setBackgroundColor(Color.rgb(blueArray[0], blueArray[1], blueArray[2]));
                yellow.setBackgroundColor(Color.rgb(yellowArray[0], yellowArray[1], yellowArray[2]));
                teal.setBackgroundColor(Color.rgb(tealArray[0], tealArray[1], tealArray[2]));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void addControl() {
        red = findViewById(R.id.red);
        green = findViewById(R.id.green);
        blue = findViewById(R.id.blue);
        teal = findViewById(R.id.teal);
        yellow = findViewById(R.id.yellow);
        sb = findViewById(R.id.slider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return OptionsMenu.inflate(getMenuInflater(), menu, R.id.linear);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return OptionsMenu.handleSelectedItem(item, this, getSupportFragmentManager());
    }
}