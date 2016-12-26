package com.vanbios.footballforecast.common.utils.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Ihor Bilous
 */

class ToastManagerImpl implements ToastManager {

    private static Toast staticToast;

    @Override
    public void showClosableToast(Context context, String text, int duration) {
        if (staticToast != null) staticToast.cancel();
        staticToast = new Toast(context);

        switch (duration) {
            case SHORT:
                duration = Toast.LENGTH_SHORT;
                break;
            case LONG:
                duration = Toast.LENGTH_LONG;
                break;
        }

        staticToast = Toast.makeText(context, text, duration);
        staticToast.show();
    }
}