package com.example.GroupProject;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.Map;

public class ViewHabitEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);

        ImageButton backEvent1  = findViewById(R.id.back_event);
        ImageButton editEvent1 = findViewById(R.id.edit_event);
        TextView viewTitle = findViewById(R.id.view_event_title);
        TextView viewComment = findViewById(R.id.view_event_comment);

        Intent intent = getIntent();
        HabitEvents event = (HabitEvents) intent.getSerializableExtra("EVENT");

        backEvent1.setOnClickListener(view -> {
            Intent intent1 = new Intent(ViewHabitEvent.this, HabitEventsMain.class);
            startActivity(intent1);
        });

        editEvent1.setOnClickListener(view -> {
            Intent intent12 = new Intent(ViewHabitEvent.this, EditHabitEvent.class);
            intent12.putExtra("EVENT", event);
            startActivity(intent12);
        });


        viewTitle.setText(event.getEventTitle());
        viewComment.setText(event.getComment());

    }
}
