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

    /*  All Permissions for the application  */
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
                .setDeniedMessage("권한을 설정하지 않을시 앱의 기능을 사용하지 못할 수 있습니다. \n\n권한을 설정하시려면 [설정] > [앱 권한]을 설정하세요.")
                .setPermissions(Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.CALL_PHONE
                        , Manifest.permission.SEND_SMS
                        , Manifest.permission.READ_CONTACTS
                        , Manifest.permission.READ_CALL_LOG
                ).check();
    }
}
