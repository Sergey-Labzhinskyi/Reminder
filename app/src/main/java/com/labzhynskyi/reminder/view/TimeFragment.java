package com.labzhynskyi.reminder.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.labzhynskyi.reminder.R;

import java.util.Calendar;

public class TimeFragment extends DialogFragment {

    private CallbackOnSelectTime mCallbackOnSelectTime;
    private TimePicker mTimePicker;
    private Calendar mCalendar;
    private int mHour;
    private int mMinute;

    public static TimeFragment newInstance() {
        Bundle args = new Bundle();
        TimeFragment fragment = new TimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface CallbackOnSelectTime {
        void onSelectTime(int hour, int minute);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mCallbackOnSelectTime = (CallbackOnSelectTime) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) mView.findViewById(R.id.dialog_time);
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                mCalendar.set(0,0,0, hourOfDay, minute);
            }

        });

        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(R.string.time_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("OK", "SAVE");
                        mCallbackOnSelectTime.onSelectTime(mHour, mMinute);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

}

