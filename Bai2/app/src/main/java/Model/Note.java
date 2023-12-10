package Model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class Note implements Parcelable {
    private long id;
    private String title;
    private String content;
    private final String createdAt;
    private final ArrayList<Bitmap> images;

    public Note() {
        this.id = -1;
        this.title = "";
        this.content = "";
        this.createdAt = LocalDate.now().toString();
        this.images = new ArrayList<>();
    }

    public Note(long id, String title, String content, ArrayList<Bitmap> images) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = LocalDate.now().toString();
        this.images = images;
    }

    public Note(long id, String title, String content, ArrayList<Bitmap> images, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.images = images;
    }

    protected Note(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        createdAt = in.readString();
        images = in.createTypedArrayList(Bitmap.CREATOR);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void addImage(Bitmap image) {
        images.add(image);
    }

    public ArrayList<Bitmap> getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(createdAt);
        dest.writeTypedList(images);
    }
}
