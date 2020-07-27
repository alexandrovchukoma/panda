package org.cocos2dx.cpp;

import android.os.Bundle;
import org.cocos2dx.lib.Cocos2dxActivity;


import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import android.support.v4.content.FileProvider;

import com.yourcompany.animalcrossing.BuildConfig; //add your package name after write .BuildConfig
import com.yourcompany.animalcrossing.R;           //add your package name after write .R

public class AppActivity extends Cocos2dxActivity {


    private static AppActivity _this;
    public static boolean admobfullpageavailable =  false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setEnableVirtualButton(false);
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            return;
        }

       // admanager =  new AdManager(this);
        _this = this;

    }

    public static void bannerEnabled(){

        _this. runOnUiThread(new Runnable() {
            @Override
            public void run() {


            }
        });

    }

    public static  void HideBanner(){


    }


    public static boolean isInterstitialAvailable(){
        return false;
    }

    public static void showInterstitial(){

    }

    public static void shareGame(final boolean visible){

        ContextWrapper c = new ContextWrapper(_this);
        String path = c.getFilesDir().getPath() + "/Image_Save.png";
        System.out.println("Paht to check --"+path);
        File imgFile = new  File(path);

        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        ArrayList<Uri> uris = new ArrayList<Uri>();
        uris.add(Uri.parse(path));

        OutputStream output;
        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath()
                + "/DCIM/Camera/");
        dir.mkdirs();

        // Create a name for the saved image
        File file = new File(dir, "Image_Save.png");

        try {

            output = new FileOutputStream(file);

            // Compress into png format image from 0% - 100%
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);


            MediaStore.Images.Media.insertImage(_this.getContentResolver(), myBitmap,"New" , "Wedding Game Description");

            output.flush();
            output.close();
        }

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        Intent intent = new Intent();

        intent.setType("text/plain");
        String rateURL = getContext().getApplicationContext().getString(R.string.rate_game_url);

        intent.putExtra(Intent.EXTRA_TEXT, "Try this amazing app \n"+rateURL);


        Uri pngUri = FileProvider.getUriForFile(_this, BuildConfig.APPLICATION_ID + ".provider",file);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, pngUri);
        intent.setType("image/jpeg, image/png");


        _this.startActivity(Intent.createChooser(intent, ""));
    }

    public static void OpenMoreGame()
    {
        String url="";
        Intent storeintent=null;


        String moreURL = getContext().getApplicationContext().getString(R.string.more_game_url);
        url = moreURL;
        storeintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        storeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        _this.startActivity(storeintent);

    }

    public static void openRateGame()
    {
        String url="";
        Intent storeintent=null;

        String rateURL = getContext().getApplicationContext().getString(R.string.rate_game_url);
        url = rateURL;
        storeintent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        storeintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        _this.startActivity(storeintent);

    }

    public static void BackButtonClicked(){

        _this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(_this,
                        R.style.MyAlertDialogStyle);
                builder.setTitle(_this.getResources().getString(R.string.app_name));
                builder.setCancelable(false);
                builder.setMessage("Do you want to EXIT");
                builder  .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _this.finish();
                    }

                })
                        .setNegativeButton("No", null);

                builder.show();
            }
        });

    }




}
