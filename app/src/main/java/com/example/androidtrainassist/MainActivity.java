package com.example.androidtrainassist;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 10001;

    private ParcelFileDescriptor mPFD;
    private String mimeType;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAllPermission();
        imageView = (ImageView) findViewById(R.id.get_image_from_share);
    }

    public void getShareFile(View view) {
        //添加test.test是为了测试action的原理。可查看AndroidTrain
        Intent intent = new Intent(Intent.ACTION_PICK + ".test.test");
        intent.setType("image/jpg");
        startActivityForResult(intent, 101);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 101){
                if (resultCode != RESULT_OK){
                    return;
                }else {
                    Uri returnUri = data.getData();
                    imageView.setImageURI(returnUri);
                    mimeType = getContentResolver().getType(returnUri);
                    try {
                        mPFD = getContentResolver().openFileDescriptor(returnUri, "r");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
//                    Cursor cursor = getContentResolver().query(returnUri, null, null, null, null);
//                    Log.d("MainActivity", cursor.);
                }

            }

        FileDescriptor fileDescriptor = mPFD.getFileDescriptor();
    }

    private void checkAllPermission() {

        String[] permissions = new String[]{


        };

        boolean isGranted = checkPermissionAllGranted(permissions);
        //只要有一项权限未通过，就重新请求权限；请求所有权限，对于已经授权的权限自动忽略
        if (isGranted){

        }else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
        }
    }

    private boolean checkPermissionAllGranted(String[] strings) {
        //对所有权限进行检查，若有一项未通过，则返回false
        for(String permission: strings){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //对请求的结果进行处理。如果还是有不通过的，则让用户去应用管理开启权限。
        if (requestCode == REQUEST_PERMISSION_CODE){
            boolean isAllGranted = true;

            for (int result: grantResults){
                if (result != PackageManager.PERMISSION_GRANTED){
                    isAllGranted = false;
                }
            }

            if (isAllGranted){
                //
            }else {
                openAppDetail();
            }
        }
    }

    private void openAppDetail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("获取位置需要至应用管理中开启");
        builder.setPositiveButton("去应用管理", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
