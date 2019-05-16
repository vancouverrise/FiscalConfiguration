package market.ics.fiscalconfiguration.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.threeten.bp.LocalDate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import market.ics.fiscalconfiguration.Communication.AsyncToast;
import market.ics.fiscalconfiguration.Communication.Commands;
import market.ics.fiscalconfiguration.Driver.DataType.DatePickerWithCustom;
import market.ics.fiscalconfiguration.Driver.DataType.TimePickWithCustom;
import market.ics.fiscalconfiguration.Driver.Response.DateResponse;
import market.ics.fiscalconfiguration.Driver.Response.TimeRepsone;
import market.ics.fiscalconfiguration.R;


public class fragment_operations extends Fragment {

    private TextInputLayout client_line1, client_line2;

    private String datestring;
    private String timestring;

    private Handler handler = new Handler();

    private ProgressDialog progressDialog;
    private Context context;
    public fragment_operations() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);

        context = getContext();

        Button zreport = view.findViewById(R.id.zReport_btn);
        Button xreport = view.findViewById(R.id.xReport_btn);

        Button dateButton = view.findViewById(R.id.datePick_btn);
        Button timeButton = view.findViewById(R.id.timePick_btn);
        Button clientSet1 = view.findViewById(R.id.dump_button);
        Button clientSet2 = view.findViewById(R.id.client_set2);
        Button shortReport = view.findViewById(R.id.short_report);
        Button longReport = view.findViewById(R.id.long_report);
        Button printVersion = view.findViewById(R.id.print_version);

        client_line1 = view.findViewById(R.id.dump_memory);
        client_line2 = view.findViewById(R.id.dump_adress);

        Button cutter = view.findViewById(R.id.cutter_button);
        Button lineFeed = view.findViewById(R.id.LineFeedBtn);
        Button cashboxsumm = view.findViewById(R.id.cashboxSumm);


        //X-Report
        xreport.setOnClickListener(v -> {
            handler.post(() -> showDialog("report"));
            new Thread(() -> {
                Commands.dayReport(0);
                handler.post(() -> {
                    progressDialog.dismiss();
                    showToast();
                });
            }).start();
        });

        //Z-Report
        zreport.setOnClickListener(v -> {
            handler.post(() -> showDialog("report"));
                new Thread(() -> {
                    Commands.dayClrReport(0);
                        handler.post(() -> {
                        progressDialog.dismiss();
                    showToast();
                });
            }).start();
        });

        //Set Date
        final DatePickerWithCustom.OnDateSetListener date = (view1, year, month, dayOfMonth) -> {};
        dateButton.setOnClickListener(v -> {
            new Thread(() -> {
                Commands.getDate();
                handler.post(() -> {
                    showToast();
                    if (!VariablesClass.getIfThereResponseFromPrinter()) {
                        System.out.println("Date Error");
                        return;
                    } else {
                        datestring = new DateResponse().getDate(VariablesClass.getPacket());

                        DatePickerWithCustom datePickerWithCustom = new DatePickerWithCustom(getContext(), date, Integer.parseInt(("20" + datestring.substring(4, 6))), (Integer.parseInt((datestring.substring(2, 4))) - 1), Integer.parseInt(datestring.substring(0, 2)));
                        datePickerWithCustom.show();
                        datePickerWithCustom.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                            datePickerWithCustom.dismiss();
                            showDialog("date");
                            new Thread(() -> {
                                Commands.setDate(datePickerWithCustom.getDatePicker().getDayOfMonth(), (datePickerWithCustom.getDatePicker().getMonth() + 1), datePickerWithCustom.getDatePicker().getYear());
                                handler.post(() -> {
                                    progressDialog.dismiss();
                                    showToast();
                                });
                            }).start();
                        });
                    }
                });
            }).start();
        });

        printVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commands.printVer();
            }
        });

        shortReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerWithCustom startDate = new DatePickerWithCustom(getContext(), date, LocalDate.now().getYear(), LocalDate.now().getMonthValue() -1 , LocalDate.now().getDayOfMonth());
                startDate.show();
                startDate.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                    LocalDate localDateFirst = LocalDate.of(startDate.getDatePicker().getYear(), startDate.getDatePicker().getMonth() + 1, startDate.getDatePicker().getDayOfMonth());
                    System.out.println("Start Date: " + localDateFirst);
                    startDate.dismiss();
                    DatePickerWithCustom endDate = new DatePickerWithCustom(getContext(), date, LocalDate.now().getYear(), LocalDate.now().getMonthValue() -1 , LocalDate.now().getDayOfMonth());
                    endDate.show();
                    endDate.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {
                        LocalDate localDateEnd = LocalDate.of(endDate.getDatePicker().getYear(), endDate.getDatePicker().getMonth() + 1, endDate.getDatePicker().getDayOfMonth());
                        System.out.println("Start Date: " + localDateFirst + " End Date: " + localDateEnd);
                        Commands.periodicReportShort(0L, localDateFirst, localDateEnd);
                        endDate.dismiss();
                    });
                });
            }
        });

        longReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerWithCustom startDate = new DatePickerWithCustom(getContext(), date, LocalDate.now().getYear(), LocalDate.now().getMonthValue() -1 , LocalDate.now().getDayOfMonth());
                startDate.show();
                startDate.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v1 -> {
                    LocalDate localDateFirst = LocalDate.of(startDate.getDatePicker().getYear(), startDate.getDatePicker().getMonth() + 1, startDate.getDatePicker().getDayOfMonth());
                    System.out.println("Start Date: " + localDateFirst);
                    startDate.dismiss();
                    DatePickerWithCustom endDate = new DatePickerWithCustom(getContext(), date, LocalDate.now().getYear(), LocalDate.now().getMonthValue() -1 , LocalDate.now().getDayOfMonth());
                    endDate.show();
                    endDate.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {
                        LocalDate localDateEnd = LocalDate.of(endDate.getDatePicker().getYear(), endDate.getDatePicker().getMonth() + 1, endDate.getDatePicker().getDayOfMonth());
                        System.out.println("Start Date: " + localDateFirst + " End Date: " + localDateEnd);
                        Commands.periodicReport(0L, localDateFirst, localDateEnd);
                        endDate.dismiss();
                    });
                });
            }
        });

        //Set Time
        TimePickWithCustom.OnTimeSetListener time = (view12, hourOfDay, minute) -> {
            handler.post(() -> showDialog("time"));
            new Thread(() -> {
                Commands.setTime(hourOfDay, minute);
                handler.post(() -> {
                    progressDialog.dismiss();
                    showToast();
                });
            }).start();};

        timeButton.setOnClickListener(v -> {
            new Thread(() -> {
                Commands.getTime();
                handler.post(() -> {
                    showToast();
                    if (!VariablesClass.getIfThereResponseFromPrinter()) {
                        System.out.println("Time Error");
                        return;
                    }
                    else
                        {
                            timestring = new TimeRepsone().getTime(VariablesClass.getPacket());
                            TimePickerDialog timePick = new TimePickerDialog(getContext(), time, Integer.parseInt(timestring.substring(0, 2)), Integer.parseInt(timestring.substring(3, 5)), true);
                            timePick.show();}});}).start();});

        clientSet1.setOnClickListener(v -> Commands.sendCustomer(true, client_line1.getEditText().getText().toString()));

        clientSet2.setOnClickListener(v -> Commands.sendCustomer(false, client_line2.getEditText().getText().toString()));

        cutter.setOnClickListener(v -> Commands.changeCutter());

        lineFeed.setOnClickListener(v -> {
            Commands.lineFeed();
            new AsyncToast(getContext()).execute();
        });

        cashboxsumm.setOnClickListener(v -> Commands.getBox()
        );


        return view;
    }


    private void showToast() {
        if (!VariablesClass.getIfThereResponseFromPrinter()) {
            DynamicToast.makeError(context, "Check Bluetooth connection!", 1).show();
        } else {
            DynamicToast.makeSuccess(context, "OK!", 1).show();
        }

    }

    private void showToastWithData(String comand) {
        if (!VariablesClass.getIfThereResponseFromPrinter()) {
            DynamicToast.makeError(context, "Check Bluetooth connection!", 1).show();
        } else {
            DynamicToast.makeSuccess(context, "OK!", 1).show();
        }

    }

    private void showDialog(String type) {
        switch (type) {
            case "date":
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Setting Date...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                break;
            case "time":
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Setting Time...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                break;
            case "report":
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Printing Report...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                break;
        }
    }
}
