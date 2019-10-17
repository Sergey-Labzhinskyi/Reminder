package com.labzhynskyi.reminder.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.labzhynskyi.reminder.R;

import java.util.Calendar;

public class DateFragment extends DialogFragment {

    private DatePicker mDatePicker;
    private Calendar mCalendar;
    private CallbackOnSelectDate mCallbackOnSelectDate;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;


    public static DateFragment newInstance() {
        Bundle args = new Bundle();
        DateFragment fragment = new DateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface CallbackOnSelectDate {
        void onSelectDate(int year, int month, int dayOfMonth);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallbackOnSelectDate = (CallbackOnSelectDate) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) mView.findViewById(R.id.dialog_date);
        mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mDatePicker.setMinDate(mCalendar.getTimeInMillis());
        mDatePicker.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDayOfMonth = dayOfMonth;
                    }
                });

        return new AlertDialog.Builder(getActivity())
                .setView(mView)
                .setTitle(R.string.date_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("OK", "SAVE");
                        mCallbackOnSelectDate.onSelectDate(mYear, mMonth, mDayOfMonth);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

}
