package Utils;

import android.content.Context;

public class Toast {
    public static void show(Context context, String message) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
