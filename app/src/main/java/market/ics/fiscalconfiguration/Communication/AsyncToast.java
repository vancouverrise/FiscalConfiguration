package market.ics.fiscalconfiguration.Communication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import market.ics.fiscalconfiguration.Fragments.VariablesClass;
import market.ics.fiscalconfiguration.Fragments.fragment_operations;

public class AsyncToast extends AsyncTask {
private Context mContext;
    public AsyncToast(Context context) {
        mContext = context;
    }

    @Override
    protected void onPostExecute(Object o) {

        super.onPostExecute(o);
        Toast.makeText(mContext, VariablesClass.getResult() + "\n" + VariablesClass.getReserv(), Toast.LENGTH_SHORT).show();

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
