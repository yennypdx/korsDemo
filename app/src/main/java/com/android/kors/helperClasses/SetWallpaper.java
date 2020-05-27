package com.android.kors.helperClasses;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.kors.R;

import java.io.IOException;

public class SetWallpaper {

    public void MyWall(int wallid, ViewGroup vg)
    {
        if(wallid == 1){
            vg.setBackgroundResource(R.mipmap.korswallpaper);
        } else if (wallid == 2){
            vg.setBackgroundResource(R.mipmap.undertale_wallpaper);
        } else if (wallid == 3){
            vg.setBackgroundResource(R.mipmap.tokyo_ghoul);
        } else if (wallid == 4){
            vg.setBackgroundResource(R.mipmap.lavender);
        } else {
            // set to default when user give outbound value
            vg.setBackgroundResource(R.mipmap.korswallpaper);
        }
    }
}
