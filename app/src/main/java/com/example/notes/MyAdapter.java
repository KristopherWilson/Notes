package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DateFormat;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<Note> notesList;
    boolean descriptionBool = SettingsActivity.isDescriptionBool();

    public MyAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (!descriptionBool)
        {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_no_desc, parent, false));
        }
        else {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleOutput.setText(note.getTitle());

        if (descriptionBool)
        {
            holder.descriptionOutput.setText(note.getDescription());
        }
        else {
            holder.descriptionOutput.setText("");
        }

        String formattedTime = DateFormat.getDateTimeInstance().format(note.getCreatedTime());
        holder.timeOutput.setText(formattedTime);

        holder.itemView.setOnLongClickListener(view -> {

            PopupMenu menu = new PopupMenu(context,view);
            menu.getMenu().add("DELETE");
            menu.getMenu().add("EDIT");
            menu.setOnMenuItemClickListener(item -> {
                if (item.getTitle().equals("DELETE")){
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    note.deleteFromRealm();
                    realm.commitTransaction();
                    Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                }
                if (item.getTitle().equals("EDIT")){
                    Note note1 = notesList.get(holder.getAdapterPosition());

                    Intent intentEdit = new Intent(context, EditNoteActivity.class);
                    intentEdit.putExtra("TITLE", note1.getTitle());
                    intentEdit.putExtra("DESC", note1.getDescription());

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    note1.deleteFromRealm();
                    realm.commitTransaction();

                    intentEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentEdit);
                }
                return true;
            });
            menu.show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);

            titleOutput.setSelected(true);
        }
    }

}
