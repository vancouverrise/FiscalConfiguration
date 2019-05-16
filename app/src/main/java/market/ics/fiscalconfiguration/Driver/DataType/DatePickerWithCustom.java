package market.ics.fiscalconfiguration.Driver.DataType;

import android.app.DatePickerDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatePickerWithCustom extends DatePickerDialog {
    public DatePickerWithCustom(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        setButton(BUTTON_POSITIVE, ("Установить выбранную дату"), this);
        setButton(BUTTON_NEGATIVE, ("Отмена"), this);
    }
}
