package Adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bai2.R;

import java.util.ArrayList;

import Model.Note;

public class NoteAdapter extends BaseAdapter {
    private final ArrayList<Note> listNote;

    public NoteAdapter() {
        this.listNote = new ArrayList<>();

        listNote.add(new Note(this.getCount() + 1,"Note 1", "This is the content of Note 1.", new ArrayList<Bitmap>(){}, "2022-01-02"));
        listNote.add(new Note(this.getCount() + 1,"Note 2", "This is the content of Note 2.", new ArrayList<Bitmap>(){}, "2022-04-12"));
        listNote.add(new Note(this.getCount() + 1,"Note 3", "This is the content of Note 3.", new ArrayList<Bitmap>(){}, "2022-08-05"));
    }

    public void addItem(Note newNote) {
        listNote.add(newNote);
        this.notifyDataSetChanged();
    }

    public void updateItem(Note item) {
        Note oldNote = this.findById(item.getId());
        listNote.set(listNote.indexOf(oldNote), item);
        this.notifyDataSetChanged();
    }

    public void removeItem(int position) {
        listNote.remove(position);
        this.notifyDataSetChanged();
    }

    public Note findById(long id) {
        for (Note note : listNote) {
            if (note.getId() == id) {
                return note;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return listNote.size();
    }

    @Override
    public Note getItem(int position) {
        return listNote.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listNote.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.note_item, null);
        }

        // Bind data into View
        Note note = getItem(position);
        ((TextView) convertView.findViewById(R.id.text_view_title)).setText(note.getTitle());
        ((TextView) convertView.findViewById(R.id.text_view_createdAt)).setText(note.getCreatedAt());
        ((TextView) convertView.findViewById(R.id.text_view_content)).setText(note.getContent());

        return convertView;
    }
}
