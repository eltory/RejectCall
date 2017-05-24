package com.example.eltory.rejectcall;

import android.app.Activity;

import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by eltory on 2017-05-09.
 */
public class PopUpTest extends Activity {

    @BindView(R.id.tv_content)
    TextView tvCont;
    Context view = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int h, m, s;
        Calendar cal = Calendar.getInstance();
        h = cal.get(Calendar.HOUR_OF_DAY);
        m = cal.get(Calendar.MINUTE);
        // s = cal.get(Calendar.SECOND);
        String time = String.valueOf(h) + " : " + String.valueOf(m);
        //cal.set(Calendar.DAY_OF_YEAR, Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY,Calendar.MINUTE,Calendar.SECOND);
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.5f;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.popup_unanswered_list);
        final View view2 = this.getWindow().getDecorView();

        ListView tv = (ListView) findViewById(R.id.tv_content);
        UnansweredListAdapter adapter = new UnansweredListAdapter();
        adapter.addItem();
        tv.setAdapter(adapter);
        Button cancel = (Button) findViewById(R.id.btn_cancel);
        final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}