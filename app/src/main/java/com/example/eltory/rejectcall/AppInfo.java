package com.example.eltory.rejectcall;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;

/**
 * Created by eltory on 2017-04-23.
 */
public class AppInfo extends Font {
    // TODO: 업데이트 레이아웃 제대로 바꾸고 개발자 정보 넣기
    public String cVersion;
    public String lVersion;
    private Button sendEmail;
    TextView currVersion;
    TextView latestVersion;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_inform);

        // 마켓최신버젼 가져오기
        lVersion = getMarketVersion();
        cVersion = getCurrAppVersion();
        currVersion = (TextView) findViewById(R.id.current_version);
        latestVersion = (TextView) findViewById(R.id.lately_version);
        update = (Button) findViewById(R.id.update);
        currVersion.setText("현재 버전 : " + cVersion);
        latestVersion.setText("최신 버전 : " + lVersion);

        // 최신버전으로 업데이트 하기
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lVersion != null && (Float.valueOf(lVersion) - Float.valueOf(cVersion) > 0.0)) {
                    Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
                    marketLaunch.setData(Uri.parse("market://search?q=" + getPackageName()));
                    startActivity(marketLaunch);
                } else {
                    Toast.makeText(AppInfo.this, "이미 최신버전 입니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String getMarketVersion() {
        return CheckUpdate.getMarketVersion(getPackageName());
    }

    public String getCurrAppVersion() {
        String device_version = null;

        try {
            device_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return device_version;
    }
}


