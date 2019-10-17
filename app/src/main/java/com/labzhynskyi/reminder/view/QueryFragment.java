package com.labzhynskyi.reminder.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.labzhynskyi.reminder.R;

public class QueryFragment extends DialogFragment {

    private CallbackOnSelectQuery mCallbackOnSelectQuery;
    private Intent mIntent;


    public static QueryFragment newInstance() {
        Bundle args = new Bundle();
        QueryFragment fragment = new QueryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface CallbackOnSelectQuery {
        void onClickOk();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallbackOnSelectQuery = (CallbackOnSelectQuery) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Callback");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.query)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("OK", "SAVE");
                        mCallbackOnSelectQuery.onClickOk();
                        mIntent = new Intent(getActivity(), DayListActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mIntent);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

}
