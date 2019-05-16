package market.ics.fiscalconfiguration.Driver.DataType;

import android.app.TimePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TimePickWithCustom extends TimePickerDialog {
    public TimePickWithCustom(@NonNull Context context, @Nullable OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView) {
        super(context, listener, hourOfDay, minute, is24HourView);
        setButton(BUTTON_POSITIVE, ("Установить выбранное время"), this);
        setButton(BUTTON_NEGATIVE, ("Отмена"), this);
    }
}
