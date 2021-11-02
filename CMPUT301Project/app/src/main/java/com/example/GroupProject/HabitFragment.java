package com.example.GroupProject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HabitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HabitFragment extends Fragment {

    Context thisContext;
    ListView habitList;
    ArrayAdapter<Habit> habitAdapter;
    ArrayList<Habit> habitDataList;
    FloatingActionButton btnAddHabit;

    public HabitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HabitFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HabitFragment newInstance() {
        HabitFragment fragment = new HabitFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        thisContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        habitList = view.findViewById(R.id.habit_list);
        habitList.setAdapter(habitAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Random Habits to be removed eventually
        String []names ={"Walk dog", "Brush Teeth", "Take vitamins"};
        String []reasons = {"Exercise", "Clean teeth", "Health"};
        Date[]dates = new Date[3];
        String []activeDays = {"Monday", "Wednesday"};



        // Add habit objects to list in listview
        habitDataList = new ArrayList<>();
        for(int i=0;i<names.length;i++){
            habitDataList.add((new Habit(names[i], reasons[i], dates[i], activeDays)));
        }
        habitAdapter = new CustomList(thisContext, habitDataList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_habit, null);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit, container, false);
    }

}