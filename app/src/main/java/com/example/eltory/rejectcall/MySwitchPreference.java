package com.example.eltory.rejectcall;

import android.content.Context;
import android.content.Intent;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.ViewGroup;

/**
 * Created by eltory on 2017-05-30.
 */

public class MySwitchPreference extends SwitchPreference {

    public MySwitchPreference(Context context) {
        super(context);
    }

    public MySwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView title = (TextView) view.findViewById(android.R.id.title);
        title.setTextSize(18);
        //title.setTextColor(getContext().getResources().getColor(R.color.title));

        ViewGroup vg = (ViewGroup) view;
        RelativeLayout titleView = (RelativeLayout) vg.getChildAt(1);
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        titleView.setBackgroundResource(outValue.resourceId);

        Log.d("마이view", String.valueOf(view));
        // Log.d("마이msg",String.valueOf());

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO : 각 리스트마다 다른 진입 만들기
                if (view.equals(view.findViewById(R.id.pref_message)))
                    getContext().startActivity(new Intent(getContext().getApplicationContext(), SetMessage.class));
            }
        });
    }
}
