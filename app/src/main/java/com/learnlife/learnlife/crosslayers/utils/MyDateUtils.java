package com.learnlife.learnlife.crosslayers.utils;

import android.content.Context;
import android.util.Log;

import com.learnlife.learnlife.R;

import java.util.Calendar;

/**
 * Created by Cedric on 06/04/2018.
 */


/**
 * Class to manage date stuff
 */
public class MyDateUtils {

    /**
     * Checking today's date and returning the string
     * @param mContext context
     * @return the string of today's date
     */
    public static String fullDate(Context mContext) {
        Calendar calendar = Calendar.getInstance();
        String space = " ";
        StringBuilder sb = new StringBuilder();

        switch (calendar.get(Calendar.DAY_OF_WEEK)-1) {
            case 1:
                sb.append(mContext.getResources().getString(R.string.mon));
                break;
            case 2:
                sb.append(mContext.getResources().getString(R.string.tue));
                break;
            case 3:
                sb.append(mContext.getResources().getString(R.string.wed));
                break;
            case 4:
                sb.append(mContext.getResources().getString(R.string.thu));
                break;
            case 5:
                sb.append(mContext.getResources().getString(R.string.fri));
                break;
            case 6:
                sb.append(mContext.getResources().getString(R.string.sat));
                break;
            case 7:
                sb.append(mContext.getResources().getString(R.string.sun));
                break;
        }

        sb.append(space);

        sb.append(calendar.get(Calendar.DAY_OF_MONTH));

        sb.append(space);

        switch (calendar.get(Calendar.MONTH)+1) {
            case 1:
                sb.append(mContext.getResources().getString(R.string.jan));
                break;
            case 2:
                sb.append(mContext.getResources().getString(R.string.feb));
                break;
            case 3:
                sb.append(mContext.getResources().getString(R.string.mar));
                break;
            case 4:
                sb.append(mContext.getResources().getString(R.string.apr));
                break;
            case 5:
                sb.append(mContext.getResources().getString(R.string.may));
                break;
            case 6:
                sb.append(mContext.getResources().getString(R.string.jun));
                break;
            case 7:
                sb.append(mContext.getResources().getString(R.string.jul));
                break;
            case 8:
                sb.append(mContext.getResources().getString(R.string.aug));
                break;
            case 9:
                sb.append(mContext.getResources().getString(R.string.sep));
                break;
            case 10:
                sb.append(mContext.getResources().getString(R.string.oct));
                break;
            case 11:
                sb.append(mContext.getResources().getString(R.string.nov));
                break;
            case 12:
                sb.append(mContext.getResources().getString(R.string.dec));
                break;
        }

        sb.append(space);

        sb.append(calendar.get(Calendar.YEAR));

        return sb.toString();

    }
}
