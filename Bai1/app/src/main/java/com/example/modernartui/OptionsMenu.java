package com.example.modernartui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.FragmentManager;

public class OptionsMenu {
    static DialogFrag dialog = new DialogFrag();
    public static boolean inflate(MenuInflater inflater, Menu menu, int disabledItemID) {
        inflater.inflate(R.menu.main_menu, menu);

        menu.findItem(disabledItemID).setEnabled(false);

        return true;
    }

    public static boolean handleSelectedItem(MenuItem item, Context context, FragmentManager fragmentManager) {
        switch (item.getItemId()) {
            case R.id.linear: {
                Intent intent = new Intent(context, LinearLayoutActivity.class);
                context.startActivity(intent);
                return true;
            }
            case R.id.relative: {
                Intent intent = new Intent(context, RelativeLayoutActivity.class);
                context.startActivity(intent);
                return true;
            }
            case R.id.table: {
                Intent intent = new Intent(context, TableLayoutActivity.class);
                context.startActivity(intent);
                return true;
            }
            case R.id.information:
                dialog.show(fragmentManager, "information");
                return true;
            default:
                return true;
        }
    }
}
