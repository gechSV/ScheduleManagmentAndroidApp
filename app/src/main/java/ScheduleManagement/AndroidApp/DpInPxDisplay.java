package ScheduleManagement.AndroidApp;

import android.content.Context;

public class DpInPxDisplay {
    public static float ConvertDpToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float ConvertPixelsToDp(Context context, float pixels) {
        return pixels / context.getResources().getDisplayMetrics().density;
    }
}
