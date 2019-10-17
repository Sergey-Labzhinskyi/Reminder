package com.labzhynskyi.reminder.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.labzhynskyi.reminder.R;

import java.util.Calendar;

public class NoteFragment extends Fragment implements INoteFragment {


    private static final String TAG = "NoteFragment";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private View mView;
    private Button mButtonSave;
    private EditText mEditText;
    private EditText mEditDate;
    private ImageView mImageViewAddDate;
    private DateFragment mDateFragment;
    private CallbackOnClickNote mCallbackOnClickDate;
    private TimeFragment mTimeFragment;
    private String mDescription;
    private Intent mIntent;
    private boolean descriptionClick;

    public static NoteFragment newInstance() {
        final NoteFragment fragment = new NoteFragment();
        final Bundle arguments = new Bundle();
        fragment.setArguments(arguments);
        return fragment;
    }


    interface CallbackOnClickNote {
        void onClickedDescription(boolean click);

        void onSaveDescription(String description);

        void onClickedSaveNote();

        void onClickedSelectDate();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallbackOnClickDate = (CallbackOnClickNote) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        mView = inflater.inflate(R.layout.fragment_note, container, false);

        mEditText = (EditText) mView.findViewById(R.id.edit_text);
        mButtonSave = (Button) mView.findViewById(R.id.button_save);
        mEditDate = (EditText) mView.findViewById(R.id.edit_date);
        mImageViewAddDate = (ImageView) mView.findViewById(R.id.date_add);
        mImageViewAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbackOnClickDate.onClickedSelectDate();

            }
        });

        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbackOnClickDate.onClickedSelectDate();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbackOnClickDate.onClickedSaveNote();
            }
        });

        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descriptionClick = true;
                mDescription = mEditText.getText().toString();
                mCallbackOnClickDate.onClickedDescription(descriptionClick);
            }
        });

        return mView;
    }


    @Override
    public void showDialogDate() {
        Log.d(TAG, "showDialogDate");
        FragmentManager manager = getFragmentManager();
        mDateFragment = DateFragment.newInstance();
        mDateFragment.show(manager, DIALOG_DATE);

    }

    @Override
    public void showDialogTime() {
        Log.d(TAG, "showDialogTime");
        FragmentManager manager = getFragmentManager();
        mTimeFragment = TimeFragment.newInstance();
        mTimeFragment.show(manager, DIALOG_TIME);
    }

    @Override
    public void setDate(String date) {
        mEditDate.setText(date);
    }

    @Override
    public void getDescription() {
        mCallbackOnClickDate.onSaveDescription(mDescription);
    }

    @Override
    public void returnActivity() {
        mIntent = new Intent(getActivity(), DayListActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
    }
}
