package com.labzhynskyi.reminder.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.labzhynskyi.reminder.R;
import com.labzhynskyi.reminder.model.Note;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment implements INoteListFragment {

    private static final String TAG = "DayListFragment";
    private static final String ARG_PERMISSION = "CharacterData";
    private static final String DIALOG_QUERY = "DIALOG QUERY";
    private RecyclerView mRecyclerView;
    private NoteAdapter mNoteAdapter;
    private CallbackOnClickListNote mCallbackOnClickListNote;
    private List<Note> mNotes;
    private FloatingActionButton mFloatingActionButton;
    private View mView;
    private QueryFragment mQueryFragment;


    public static NoteListFragment newInstance(List<Note> notes) {
        final NoteListFragment fragment = new NoteListFragment();
        final Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_PERMISSION, (Serializable) notes);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showDialogQuery() {
        Log.d(TAG, "showDialogDate");
        FragmentManager manager = getFragmentManager();
        mQueryFragment = QueryFragment.newInstance();
        mQueryFragment.show(manager, DIALOG_QUERY);
    }


    interface CallbackOnClickListNote {
        void onClickedFabNote();

        void onClickedNote(Note note);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallbackOnClickListNote = (CallbackOnClickListNote) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (getArguments() != null && getArguments().containsKey(ARG_PERMISSION)) {
            mNotes = new ArrayList<>();
            mNotes = (List<Note>) getArguments().getSerializable(ARG_PERMISSION);
            Log.d(TAG, "onCreate" + mNotes.size());
        } else {
            throw new IllegalArgumentException("Must be created through newInstance(...)");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        mView = inflater.inflate(R.layout.fragment_note_list, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.note_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab_note);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbackOnClickListNote.onClickedFabNote();
                Log.d(TAG, "FABClick");
            }
        });

        updateUI();

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        updateUI();
    }

    public void updateUI() {
        Log.d(TAG, "updateUI");
        mNoteAdapter = new NoteAdapter(mNotes);
        mRecyclerView.setAdapter(mNoteAdapter);
    }

    private class NoteHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private TextView mTextViewDate;
        private TextView mTextViewDescription;
        private Note mNote;
        private int mPosition;
        private SimpleDateFormat mSimpleDateFormat;
        private String mTime;

        public NoteHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_note, parent, false));
            itemView.setOnLongClickListener((View.OnLongClickListener) this);

            mTextViewDate = itemView.findViewById(R.id.date);
            mTextViewDescription = itemView.findViewById(R.id.description);
        }

        // bind CharacterHolder with ViedoItem
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("SetTextI18n")
        public void bind(Note note, int position) {
            mPosition = position;
            mNote = note;
            mSimpleDateFormat = new SimpleDateFormat("HH:mm");
            mTime = mSimpleDateFormat.format(mNote.getCalendar().getTime());
            mTextViewDate.setText(mTime);
            mTextViewDescription.setText(mNote.getDescription());
        }


        @Override
        public boolean onLongClick(View v) {
            mCallbackOnClickListNote.onClickedNote(mNote);
            return true;
        }
    }


    private class NoteAdapter extends RecyclerView.Adapter {

        public NoteAdapter(List<Note> notes) {
            mNotes = notes;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new NoteHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Note note = mNotes.get(position);
            ((NoteHolder) holder).bind(note, position);

        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }
}
