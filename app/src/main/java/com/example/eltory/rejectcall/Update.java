/*
package com.example.eltory.rejectcall;

*/
/**
 * Created by eltory on 2017-04-23.
 *//*


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class Update extends Activity {

    //새버전의 프로그램이 존재하는지 여부
    private int newver = 0;
    private int oldver = 0;
    private String strVer;

    private String fileName;
    private CharSequence updateMessage;
    private String updateTitle;

    // Progress Dialog
    private TextView textVersion;

    private TextView progressTitle;
    private TextView progressText;
    private ProgressBar progressBar;
    private RelativeLayout downloadUpdateLayout;

    //스피너  선언
    private Spinner spinnerLocation;

    //확인하고 싶은 패키지명 String
    private static final String CHECK_PACKAGE_NAME = "com.example.autoupate";

    public static final String MSG_TAG = "AutoupdateActivity";

    private static final String IP_ADDRESS = "서버주소";

    // Update Url
    private static final String APPLICATION_PROPERTIES_URL = "http://" + IP_ADDRESS + "/download/application.properties";
    private static final String APPLICATION_DOWNLOAD_URL = "http://" + IP_ADDRESS + "/download/";

    public static final int MESSAGE_DOWNLOAD_STARTING = 3;
    public static final int MESSAGE_DOWNLOAD_PROGRESS = 4;
    public static final int MESSAGE_DOWNLOAD_COMPLETE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_update_request);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressText = (TextView) findViewById(R.id.progressText);
        progressTitle = (TextView) findViewById(R.id.progressTitle);
        downloadUpdateLayout = (RelativeLayout) findViewById(R.id.layoutDownloadUpdate);

        spinnerLocation = (Spinner) findViewById(R.id.myspinner);

        textVersion = (TextView) findViewById(R.id.textVersion);

        // 업데이트 체크
        checkForUpdate();

        PackageInfo oldversionInfo;
        try {
            oldversionInfo = getPackageManager().getPackageInfo(CHECK_PACKAGE_NAME, PackageManager.GET_META_DATA);
            strVer = oldversionInfo.versionName;
        } catch (NameNotFoundException e1) {
            e1.printStackTrace();
        }

        textVersion.setText("버전 " + strVer);

        //createShortcut();    //바로가기 생성

        //접속 버튼
        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        btnConnect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MSG_TAG, "접속버튼 누름");
            }
        });


        // 접속정보 추가 버튼
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MSG_TAG, "추가버튼 누름");

            }
        });

        //접속정보 수정버튼
        Button btnModify = (Button) findViewById(R.id.btnModify);
        btnModify.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MSG_TAG, "수정버튼 누름");
            }
        });

        //접속정보 삭제버튼
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MSG_TAG, "삭제버튼 누름");
            }
        });
    }

    */
/*
     * Update checking. We go to a predefined URL and fetch a properties style file containing
     * information on the update. These properties are:
     *
     * versionCode: An integer, version of the new update, as defined in the manifest. Nothing will
     *              happen unless the update properties version is higher than currently installed.
     * fileName: A string, URL of new update apk. If not supplied then download buttons
     *           will not be shown, but instead just a message and an OK button.
     * message: A string. A yellow-highlighted message to show to the user. Eg for important
     *          info on the update. Optional.
     * title: A string, title of the update dialog. Defaults to "Update available".
     *
     * Only "versionCode" is mandatory.
     *//*


    //앱 업데이트 검사
    public void checkForUpdate() {
        // if (this.isUpdatecDisabled()) {
        //     Log.d(MSG_TAG, "Update-checks are disabled!");
        //     return;
        // }
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();

                // 서버상의 Properties 얻기
                Properties updateProperties = queryForProperty(APPLICATION_PROPERTIES_URL);
                String verName;

                if (updateProperties != null && updateProperties.containsKey("versionCode")) {

                    int newversion = Integer.parseInt(updateProperties.getProperty("versionCode"));
                    //int installedVersion = TetherApplication.this.getVersionNumber();
                    fileName = updateProperties.getProperty("fileName", "");
                    updateMessage = updateProperties.getProperty("message", "");
                    updateTitle = updateProperties.getProperty("title", "업데이트가 가능합니다.");
                    verName = updateProperties.getProperty("versionName", "");

                    newver = newversion;

                    try {
                        //설치된 앱 정보 얻기
                        PackageInfo oldversionInfo = getPackageManager().getPackageInfo(CHECK_PACKAGE_NAME, PackageManager.GET_META_DATA);
                        oldver = Integer.valueOf(oldversionInfo.versionCode);

                        //다운로드 폴더 얻어오기
                        String localpath = getDownloadDirectory();

                        Log.d("앱버전", "앱버전 : " + oldver);
                        Log.d("서버상", "서버에 있는 파일 버전 : " + newver);
                        Log.d("받아온경로", "받아온 경로: " + localpath);

                        if (oldver < newver) {    //파일 버전비교
                            openUpdateDialog(APPLICATION_DOWNLOAD_URL + fileName, fileName, updateMessage, updateTitle, localpath);
                        } else {
                            Log.d(MSG_TAG, " 최신버전입니다. 버전 : " + verName);
                        }
                        //textVersion.setText(" Ver : " + verName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Looper.loop();
            }
        }).start();
    }

    //다운로드 폴더 얻기
    private String getDownloadDirectory() {
        String sdcardPath = "";
        String downloadpath = "";
        if (isUsableSDCard(true)) {    //외장메모리 사용가능할 경우
            sdcardPath = Environment.getExternalStorageDirectory().getPath();
            downloadpath = sdcardPath + "/download/";
        } else {                       //내장메모리 위치
            File file = Environment.getRootDirectory();
            sdcardPath = file.getAbsolutePath();
            downloadpath = sdcardPath + "/download";
        }
        return downloadpath;
    }

    //외장메모리 사용 가능여부 확인
    private boolean isUsableSDCard(boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (!requireWriteAccess &&
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    //서버의 application.properties 파일 읽어오기
    public Properties queryForProperty(String url) {
        Properties properties = null;

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(String.format(url));

        try {
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();

            if (status.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                properties = new Properties();
                properties.load(entity.getContent());
            }
        } catch (IOException e) {
            Log.d("오류", "Can't get property '" + url + "'.");
        }
        return properties;
    }

    //업데이트 할 것인지 확인
    public void openUpdateDialog(final String downloadFileUrl, final String fileName,
                                 final CharSequence message, final String updateTitle, final String localpath4down) {

        LayoutInflater li = LayoutInflater.from(this);
        Builder dialog;
        View view;

        view = li.inflate(R.layout.updateview, null);
        TextView messageView = (TextView) view.findViewById(R.id.updateMessage);
        TextView updateNowText = (TextView) view.findViewById(R.id.updateNowText);

        if (fileName.length() == 0)  // No filename, hide 'download now?' string
            updateNowText.setVisibility(View.GONE);

        messageView.setText(message);

        dialog = new AlertDialog.Builder(AutoupdateActivity.this)
                .setTitle(updateTitle)
                .setView(view);

        if (fileName.length() > 0) {
            // Display Yes/No for if a filename is available.
            dialog.setNeutralButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Log.d(MSG_TAG, "No pressed");
                }
            });

            dialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Log.d(MSG_TAG, "Yes pressed");
                    Log.d("경로명", "경로명 : " + downloadFileUrl + " 파일명 : " + fileName);
                    downloadUpdate(downloadFileUrl, fileName, localpath4down);
                }
            });
        } else {
            dialog.setNeutralButton("확인", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Log.d(MSG_TAG, "Ok pressed");
                }
            });
        }
        dialog.show();
    }

    //다운로드 받은 앱을 설치, 이전 실행 앱 종료
    public void downloadUpdate(final String downloadFileUrl, final String fileName, final String localpath) {
        new Thread(new Runnable() {
            public void run() {
                Message msg = Message.obtain();
                msg.what = MESSAGE_DOWNLOAD_STARTING;
                msg.obj = localpath + fileName;

                File apkFile = new File(localpath + fileName);
                Log.d("downloadUpdate", "경로1:" + localpath + fileName);
                viewUpdateHandler.sendMessage(msg);

                downloadUpdateFile(downloadFileUrl, fileName, localpath);

                //다운로드 받은 패키지를 인스톨한다.
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

              */
/*
               * 안드로이드 프로세스는  단지 finish() 만 호출 하면 죽지 않는다.
               * 만약 프로세스를 강제로 Kill 하기위해서는 화면에 떠있는 Activity를 BackGround로 보내고
               * 강제로 Kill하면 프로세스가  완전히 종료가 된다.
               * 종료 방법에 대한 Source는 아래 부분을 참조 하면 될것 같다.
               *//*

                moveTaskToBack(true);
                finish();
                android.os.Process.sendSignal(android.os.Process.myPid(), android.os.Process.SIGNAL_KILL);
            }
        }).start();
    }

    public Handler viewUpdateHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_DOWNLOAD_STARTING:
                    Log.d(MSG_TAG, "프로그레스바 시작");
                    progressBar.setIndeterminate(true);
                    progressTitle.setText((String) msg.obj + " 다운로드");
                    progressText.setText("시작 중...");
                    downloadUpdateLayout.setVisibility(View.VISIBLE);
                    break;
                case MESSAGE_DOWNLOAD_PROGRESS:
                    progressBar.setIndeterminate(false);
                    progressText.setText(msg.arg1 + "k /" + msg.arg2 + "k");
                    progressTitle.setText((String) msg.obj + fileName + "..로 다운로드 중입니다.");
                    progressBar.setProgress(msg.arg1 * 100 / msg.arg2);
                    break;
                case MESSAGE_DOWNLOAD_COMPLETE:
                    Log.d(MSG_TAG, "다운로드 완료.");
                    progressText.setText("");
                    progressTitle.setText("");
                    downloadUpdateLayout.setVisibility(View.GONE);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public boolean downloadUpdateFile(String downloadFileUrl, String destinationFilename, String localPath) {
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) == false) {
            return false;
        }

        File downloadDir = new File(localPath);
        Log.d("DOWNLOAD", "다운로드중");
        if (downloadDir.exists() == false) {
            downloadDir.mkdirs();
        } else {
            File downloadFile = new File(localPath + destinationFilename);
            if (downloadFile.exists()) {
                downloadFile.delete();
            }
        }
        return this.downloadFile(downloadFileUrl, localPath, destinationFilename);
    }

    //파일 다운로드 과정 표시
    public boolean downloadFile(String url, String destinationDirectory, String destinationFilename) {
        boolean filedownloaded = true;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(String.format(url));
        Message msg = Message.obtain();

        try {
            HttpResponse response = client.execute(request);
            StatusLine status = response.getStatusLine();
            Log.d(MSG_TAG, "Request returned status " + status);

            if (status.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                InputStream instream = entity.getContent();
                int fileSize = (int) entity.getContentLength();
                FileOutputStream out = new FileOutputStream(new File(destinationDirectory + destinationFilename));
                byte buf[] = new byte[8192];
                int len;
                int totalRead = 0;

                while ((len = instream.read(buf)) > 0) {
                    msg = Message.obtain();
                    msg.what = MESSAGE_DOWNLOAD_PROGRESS;
                    totalRead += len;
                    msg.arg1 = totalRead / 1024;
                    msg.arg2 = fileSize / 1024;
                    viewUpdateHandler.sendMessage(msg);
                    out.write(buf, 0, len);
                }
                out.close();
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            filedownloaded = false;
        }
        msg = Message.obtain();
        msg.what = MESSAGE_DOWNLOAD_COMPLETE;
        viewUpdateHandler.sendMessage(msg);
        return filedownloaded;
    }

}*/
