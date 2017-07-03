package com.example.eltory.rejectcall;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by eltory on 2017-04-23.
 */
public class AppInfo extends AppCompatActivity {

    public static String version = "2";
    public String lVersion;
    TextView currVersion;
    TextView latestVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_inform);
        PackageInfo pi = null;

        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version = pi.versionName;
        getVersion();
        currVersion = (TextView) findViewById(R.id.current_version);
        latestVersion = (TextView) findViewById(R.id.lately_version);

        currVersion.setText("현재 버전 : " + version);
        latestVersion.setText("최신 버젼 : " + lVersion);
    }

    public void getVersion() {
        this.lVersion = "1.1";
    }
}
