package Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bai2.R;

import java.util.ArrayList;

public class ImageAdapter extends ArrayAdapter<Bitmap> {
    private final ArrayList<Bitmap> images;

    public ImageAdapter(@NonNull Context context) {
        super(context, 0);
        this.images = new ArrayList<>();
    }

    public ImageAdapter(@NonNull Context context, ArrayList<Bitmap> images) {
        super(context, 0, images);
        this.images = images;
    }

    public void add(Bitmap bitmap) {
        images.add(bitmap);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.image_item, null);
        }

        if (position >= 0 && position < images.size()) {
            Bitmap bitmap = images.get(position);
            // Bind data into View
            ((ImageView) convertView.findViewById(R.id.imageView)).setImageBitmap(bitmap);
        }

        return convertView;
    }
}