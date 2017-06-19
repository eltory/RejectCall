package com.example.eltory.rejectcall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;

/**
 * Created by eltor on 2017-06-18.
 */
public class TestTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent inten = new Intent(Intent.ACTION_VIEW);
        inten.setType(CallLog.Calls.CONTENT_TYPE);
        startActivity(inten);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
