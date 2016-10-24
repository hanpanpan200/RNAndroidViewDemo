package com.rnandroidviewdemo.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.facebook.react.bridge.*;

/**
 * Created by hanpanpan on 10/24/16.
 */
public class ImagePickerModule extends ReactContextBaseJavaModule implements ActivityEventListener{
    private static final int PICKER_IMAGE = 1;
    private Callback pickerSuccessCallback;
    private Callback pickerFailedCallback;

    public ImagePickerModule(ReactApplicationContext reactContext){
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "ImagePicker";
    }

    @ReactMethod
    public void openSelectDialog(ReadableMap config, Callback successCallback, Callback cancelCallback) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            cancelCallback.invoke("Activity does not exist.");
            return;
        }
        pickerSuccessCallback = successCallback;
        pickerFailedCallback = cancelCallback;

        try {
            final Intent galleryIntent = new Intent();

            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Pick an image");
            currentActivity.startActivityForResult(chooserIntent, PICKER_IMAGE);
        } catch (Exception e) {
            cancelCallback.invoke(e);
        }
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (pickerSuccessCallback != null) {
            if (resultCode == Activity.RESULT_CANCELED) {
                pickerFailedCallback.invoke("ImagePicker was cancelled");
            } else if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                if (uri == null) {
                    pickerFailedCallback.invoke("No image data found");
                } else {
                    try {
                        pickerSuccessCallback.invoke(uri);
                    } catch (Exception e) {
                        pickerFailedCallback.invoke("Error");
                    }
                }
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
