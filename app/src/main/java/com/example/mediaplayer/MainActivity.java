package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button order=(Button)findViewById(R.id.order);//播放顺序
        order.setOnClickListener(this);
        ListView list=(ListView)findViewById(R.id.list);//歌曲列表
        Button last=(Button)findViewById(R.id.last);//上一首
        last.setOnClickListener(this);
        Button status=(Button)findViewById(R.id.status);//播放状态
        status.setOnClickListener(this);
        Button next=(Button)findViewById(R.id.next);//下一首
        next.setOnClickListener(this);
        Button _list=(Button)findViewById(R.id._list);//歌单列表
        _list.setOnClickListener(this);

        //判断是否有存储器读写权限
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            initSongList();
        }

        //点击歌曲列表，播放
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song=(Song) parent.getItemAtPosition(position);
                TextView ing=(TextView)findViewById(R.id.ing);//正在播放的歌曲
                ing.setText(song.getSong());
                initMediaPlayer(song.getPath());//初始化MediaPlayer
            }
        });
    }

    //按钮监听器
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order:{
                break;
            }
            case R.id.last:{
                break;
            }
            case R.id.status:{
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else {
                    mediaPlayer.start();
                }
                break;
            }
            case R.id.next:{
                break;
            }
            case R.id._list:{
                break;
            }
        }
    }

    //初始化歌曲列表
    public void initSongList(){
        List<Song> list=new ArrayList<>();
        //扫描sdk所有音频文件
        Cursor cursor = MainActivity.this.getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI , null, null, null,MediaStore.Audio.AudioColumns.IS_MUSIC);
        if(cursor!=null){
            while(cursor.moveToNext()){
                Song song=new Song();
                song.setSong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));//歌名
                song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));//路径
                song.setLength(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));//歌长
                list.add(song);
            }
            cursor.close();
        }

        ListView listView=(ListView)findViewById(R.id.list);
        SongAdapter adapter=new SongAdapter(MainActivity.this,R.layout.song_item,list);
        listView.setAdapter(adapter);
    }

    //初始化MediaPlayer
    private void initMediaPlayer(String playPath){
        File file=new File(playPath);
        try {
            if(mediaPlayer==null){
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(file.getPath());//指定音频文件的播放路径
                mediaPlayer.prepare();//MediaPlayer进入准备状态
            }
            else if(mediaPlayer.isPlaying()){
                try{
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(file.getPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                mediaPlayer=new MediaPlayer();
                mediaPlayer.setDataSource(file.getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //初次运行申请权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initSongList();
                }
                else {
                    Toast.makeText(MainActivity.this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }
}
