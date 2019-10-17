package com.labzhynskyi.reminder.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.labzhynskyi.reminder.R;
import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.model.Note;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DayListFragment extends Fragment {

    private static final String TAG = "DayListFragment";
    private static final String ARG_PERMISSION = "CharacterData";
    private RecyclerView mRecyclerView;
    private DayAdapter mDayAdapter;
    private CallbackOnClickListDay mCallbackOnClickListDay;
    private List<Day> mDays;
    private FloatingActionButton mFloatingActionButton;


    public static DayListFragment newInstance(List<Day> days) {
        final DayListFragment fragment = new DayListFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_PERMISSION, (Serializable) days);
        fragment.setArguments(arguments);
        return fragment;
    }

    interface CallbackOnClickListDay {
        void onClickedDay(int position, Day day);
        void onClickedFab();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallbackOnClickListDay = (CallbackOnClickListDay) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null && getArguments().containsKey(ARG_PERMISSION)) {
            mDays = new ArrayList<>();
            mDays = (List<Day>) getArguments().getSerializable(ARG_PERMISSION);
            Log.d(TAG, "onCreate" + mDays.size());
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_day_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.day_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_day);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbackOnClickListDay.onClickedFab();
                Log.d(TAG, "FABClick");
            }
        });

        updateUI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        updateUI();
    }


    public void updateUI() {
        Log.d(TAG, "updateUI");
        mDayAdapter = new DayAdapter(mDays);
        mRecyclerView.setAdapter(mDayAdapter);
    }

    private class DayHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextViewDayOfWeek;
        private TextView mTextViewDate;
        private Day mDay;
        private int mPosition;
        private List<Note> mNotes;
        private LinearLayout mLinearLayout;
        private TextView mTextViewNote;
        private SimpleDateFormat mSimpleDateFormat;
        private String mDate;
        private Set<Note> mSet;

        public DayHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_day, parent, false));
            itemView.setOnClickListener((View.OnClickListener) this);

            mTextViewDayOfWeek = itemView.findViewById(R.id.day_of_week);
            mTextViewDate = itemView.findViewById(R.id.day_date);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lineral_layout);
        }

        // bind DayHolder with ViedoItem
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("SetTextI18n")
        public void bind(Day day, int position) {


            mPosition = position;
            mDay = day;
            mSet = mDay.getNotes();
            mNotes = new ArrayList<>(mSet);
            Collections.sort(mNotes);
            mSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            mDate = mSimpleDateFormat.format(mDay.getDate());
            mTextViewDayOfWeek.setText(mDay.getName());
            mTextViewDate.setText(mDate);

            for (int i = 0; i < mNotes.size(); i++) {
                if (i < 3) {
                    mTextViewNote = new TextView(getActivity());
                    if (mNotes.get(i).getDescription().length() > 25) {
                        mTextViewNote.setText("- " + mNotes.get(i).getDescription().substring(0, 25));
                    } else {
                        mTextViewNote.setText("- " + mNotes.get(i).getDescription());
                    }
                    mTextViewNote.setTextColor(Color.parseColor("#000000"));
                    mTextViewNote.setTextSize(16);
                    mLinearLayout.addView(mTextViewNote);
                } else {
                    break;
                }
            }

        }

        @Override
        public void onClick(View v) {
            mCallbackOnClickListDay.onClickedDay(mPosition, mDay);
        }
    }


    private class DayAdapter extends RecyclerView.Adapter {


        private List<Day> mDayList;

        public DayAdapter(List<Day> dayList) {
            mDayList = dayList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new DayHolder(layoutInflater, parent);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Day day = mDayList.get(position);
            ((DayHolder) holder).bind(day, position);

        }

        @Override
        public int getItemCount() {
            return mDayList.size();
        }
    }
}
