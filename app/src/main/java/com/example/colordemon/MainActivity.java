package com.example.colordemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.SeekBar;

import com.example.colordemon.MainMenuAdditionals.DownloadActivity;
import com.example.colordemon.MainMenuAdditionals.MenuFragment;
import com.example.colordemon.MainMenuAdditionals.SettingsFragment;
import com.example.colordemon.MainMenuAdditionals.SettingsInfo;
import com.example.colordemon.MainMenuAdditionals.ShopFragment;
import com.example.colordemon.databinding.ActivityMainBinding;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DataPushing{
    // music
    ActivityMainBinding binding;

    MediaPlayer mPlayer;
    SeekBar volumeControl;
    AudioManager audioManager;
    // пока не используется
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //music
        mPlayer=MediaPlayer.create(this, R.raw.main_menu_music);
        mPlayer.start();
        //TEST
        Locale locale = new Locale(TestLocaleClass.language);
        changeLocale(locale);

        binding.pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        binding.pager.setCurrentItem(1);
        binding.mainMenuIconButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pager.setCurrentItem(0);
            }
        });
        binding.mainMenuIconButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pager.setCurrentItem(1);
            }
        });
        binding.mainMenuIconButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.pager.setCurrentItem(2);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void changeLocale(Locale locale){
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void DataPush(SettingsInfo settingsInfo) {
        //changeLocale(new Locale(settingsInfo.getLanguage()));
        TestLocaleClass.language=settingsInfo.getLanguage();
        // + настройка звука, можно будет тягать доп штуки с настроек
        Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
        startActivity(intent);
    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position%3) {
                case 0:
                    return new ShopFragment();
                case 1:
                    return new MenuFragment();
                case 2:
                    return new SettingsFragment(TestLocaleClass.language,100);
                default:
                    return new MenuFragment();
            }
        }
    }
}


