package com.example.modernartui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RelativeLayoutActivity extends AppCompatActivity {
    View red, green, blue, teal, yellow;
    SeekBar sb;
    int sbMaxValue = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        positionColumns();
        positionElementsInFirstColumn();
        positionElementsInSecondColumn();
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
                redArray[1] = redArray[1] + (123 / sbMaxValue) * i;
                redArray[2] = redArray[2] + (182 / sbMaxValue) * i;

                greenArray[0] = greenArray[0] + (172 / sbMaxValue) * i;
                greenArray[1] = greenArray[1] - (greenArray[1] / sbMaxValue) * i;
                greenArray[2] = greenArray[2] + (152 / sbMaxValue) * i;

                blueArray[0] = blueArray[0] + (244 / sbMaxValue) * i;
                blueArray[1] = blueArray[1] + (111 / sbMaxValue) * i;
                blueArray[2] = blueArray[2] - (blueArray[2] / sbMaxValue) * i;

                yellowArray[0] = yellowArray[0] - (yellowArray[0] / sbMaxValue) * i;
                yellowArray[1] = yellowArray[1] - (yellowArray[1] / sbMaxValue) * i;
                yellowArray[2] = yellowArray[2] + (222 / sbMaxValue) * i;

                tealArray[0] = tealArray[0] + (177 / sbMaxValue) * i;
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

    private void positionColumns() {
        RelativeLayout container = findViewById(R.id.container);
        RelativeLayout firstColumn = findViewById(R.id.first_column);
        RelativeLayout secondColumn = findViewById(R.id.second_column);

        ViewTreeObserver vto = container.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int containerWidth = container.getMeasuredWidth();
                int containerHeight = container.getMeasuredHeight();
                int oneThirdWidth = containerWidth / 3;

                firstColumn.getLayoutParams().width = oneThirdWidth;
                firstColumn.getLayoutParams().height = containerHeight;

                secondColumn.getLayoutParams().width = containerWidth - oneThirdWidth;
                secondColumn.getLayoutParams().height = containerHeight;

                firstColumn.setLayoutParams(firstColumn.getLayoutParams());
                secondColumn.setLayoutParams(secondColumn.getLayoutParams());

                firstColumn.requestLayout();
                secondColumn.requestLayout();
            }
        });
    }

    private void positionElementsInFirstColumn() {
        RelativeLayout firstColumn = findViewById(R.id.first_column);

        ViewTreeObserver vto = firstColumn.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                firstColumn.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int secondColumnWidth = firstColumn.getLayoutParams().width;
                int halfHeight = firstColumn.getLayoutParams().height / 2;
                int childrenLength = firstColumn.getChildCount();

                for (int i = 0; i < childrenLength; i++) {
                    View child = firstColumn.getChildAt(i);
                    child.getLayoutParams().width = secondColumnWidth;
                    child.getLayoutParams().height = halfHeight;
                    child.setLayoutParams(child.getLayoutParams());
                }

                firstColumn.requestLayout();
            }
        });
    }

    private void positionElementsInSecondColumn() {
        RelativeLayout secondColumn = findViewById(R.id.second_column);

        ViewTreeObserver vto = secondColumn.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                secondColumn.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int secondColumnWidth = secondColumn.getLayoutParams().width;
                int oneThirdHeight = secondColumn.getLayoutParams().height / 3;
                int childrenLength = secondColumn.getChildCount();

                for (int i = 0; i < childrenLength; i++) {
                    View child = secondColumn.getChildAt(i);
                    child.getLayoutParams().width = secondColumnWidth;
                    child.getLayoutParams().height = oneThirdHeight;
                    child.setLayoutParams(child.getLayoutParams());
                }

                secondColumn.requestLayout();
            }
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
        return OptionsMenu.inflate(getMenuInflater(), menu, R.id.relative);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return OptionsMenu.handleSelectedItem(item, this, getSupportFragmentManager());
    }
}