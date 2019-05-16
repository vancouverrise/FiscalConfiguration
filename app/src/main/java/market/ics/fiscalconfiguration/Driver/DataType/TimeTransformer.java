package market.ics.fiscalconfiguration.Driver.DataType;

import org.threeten.bp.LocalTime;

public class TimeTransformer {
    final String getTimeFromRow;
    LocalTime time;

    public TimeTransformer(String getTimeFromRow) {
        this.getTimeFromRow = getTimeFromRow;
    }

    public LocalTime transform (){
        String [] rowFromTimeArray = getTimeFromRow.split(":");
        String hour = rowFromTimeArray[0];
        String minutes = rowFromTimeArray[1];
        String seconds = rowFromTimeArray[2];
        time = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minutes), Integer.parseInt(seconds));
        return time;


    }
}
