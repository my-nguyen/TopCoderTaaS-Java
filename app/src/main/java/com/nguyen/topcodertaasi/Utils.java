package com.nguyen.topcodertaasi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;

public class Utils {
    public static final int WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST = 101;

    // take a screen shot of the current fragment and share it
    public static void shareScreenShot(Fragment fragment) {
        getPermission(fragment);
        Bitmap bitmap = takeScreenshot(fragment.getActivity());
        File file = saveBitmap(fragment.getContext(), bitmap);
        shareImage(fragment, file);
    }

    // request permission to write data to external storage
    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(fragment.getContext(), "Write External Storage permission granted", Toast.LENGTH_SHORT).show();
        } else {
            if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                Toast.makeText(fragment.getContext(), "Write External Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // what to do when permission is granted
    private static void getPermission(Fragment fragment) {
        if (ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            }
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSIONS_REQUEST);
        }
    }

    // take a screen shot, except in screen with google map
    private static Bitmap takeScreenshot(Activity activity) {
        View rootView = activity.getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    // save a bitmap into a file
    public static File saveBitmap(Context context, Bitmap bitmap) {
        // File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        File imagePath = new File(context.getExternalFilesDir(null) + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    // share an image (based on its file path) with the apps available
    public static void shareImage(Fragment fragment, File imagePath) {
        Uri uri = FileProvider.getUriForFile(fragment.getContext(), fragment.getContext().getApplicationContext().getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.title_total_stats));
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            fragment.startActivity(Intent.createChooser(sharingIntent, fragment.getString(R.string.title_total_stats)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String toString(int number) {
        return String.format("%,d", number);
    }

    public static String toString(String number) {
        return number;
    }
}
