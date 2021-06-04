package com.filomenadeveloper.myunheiro.ui.notifications;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.filomenadeveloper.myunheiro.R;

import java.util.Calendar;

public class DatePickerFragmento extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year= c.get(Calendar.YEAR);
        int month= c.get(Calendar.MONTH);
        int dayofmonth=c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd= new DatePickerDialog(getContext(), R.style.TimePickerTheme, (DatePickerDialog.OnDateSetListener) getActivity(),year,month,dayofmonth);
        return dpd;


    }
}
