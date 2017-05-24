package com.example.eltory.rejectcall;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.gun0912.tedpermission.TedPermissionActivity;

import java.util.ArrayList;

/**
 * Created by eltory on 2017-04-26.
 */
public class Permissions extends Activity {

    // 어플에 필요한 모든 권한 관리
    public void permissionCheck(Context context) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            }
        };
        new TedPermission(context)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.CALL_PHONE
                        , Manifest.permission.SEND_SMS
                        , Manifest.permission.READ_CONTACTS
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.READ_CALL_LOG
                ).check();
    }
}
