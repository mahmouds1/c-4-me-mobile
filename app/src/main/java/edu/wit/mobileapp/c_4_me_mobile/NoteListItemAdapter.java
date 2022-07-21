package edu.wit.mobileapp.c_4_me_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteListItemAdapter extends ArrayAdapter<NoteListItem> {
    private LayoutInflater mInflater;
    public NoteListItemAdapter(Context context, int rid, List<NoteListItem> list){
        super(context, rid, list);
        mInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        // Retrieve data
        NoteListItem item = (NoteListItem) getItem(position);
        // Use layout file to generate View
        View view = mInflater.inflate(R.layout.note_list_item, null);

        // Set title
        TextView title;
        title = (TextView)view.findViewById(R.id.note_item_title);
        title.setText(item.title);

        // Set date
        TextView date;
        date = (TextView) view.findViewById(R.id.note_item_date);
        date.setText(item.date);

        // Set note_content
        TextView note_content;
        note_content = (TextView) view.findViewById(R.id.note_item_content);
        note_content.setText(item.note_content);
        return view;
    }
}
