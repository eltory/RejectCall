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

    private int num;

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

        ViewGroup vg = (ViewGroup) view;
        RelativeLayout titleView = (RelativeLayout) vg.getChildAt(1);
        TextView title = (TextView) view.findViewById(android.R.id.title);
        title.setTextSize(18);
        //title.setTextColor(getContext().getResources().getColor(R.color.title));

        // Ripple 효과주기
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
        titleView.setBackgroundResource(outValue.resourceId);

        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGoIntent();
            }
        });
    }

    public void setNum(int num) {
        this.num = num;
    }

    /*  Set each entrance */
    public void setGoIntent() {
        Log.d("진입완료", "선택");

        if (this.num == 1)
            getContext().startActivity(new Intent(getContext(), SetMessage.class));
        else if (this.num == 2)
            getContext().startActivity(new Intent(getContext(), SetTime.class));
        else if (this.num == 3)
            getContext().startActivity(new Intent(getContext(), SetExceptNumber.class));
        else
            getContext().startActivity(new Intent(getContext(), SetTime.class));
    }
}
