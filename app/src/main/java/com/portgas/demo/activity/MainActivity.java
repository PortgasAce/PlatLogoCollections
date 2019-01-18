package com.portgas.demo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

  // source code:
  // http://androidxref.com/

  // platlogo: /frameworks/base/core/res/res/drawable-nodpi/
  // PlatLogoActivity: /frameworks/base/core/java/com/android/internal/app/PlatLogoActivity.java
  // AndroidManifest: /frameworks/base/core/res/AndroidManifest.xml

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.p).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityP.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.o).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityO.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.n).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityN.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.m).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityM.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.l).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityL.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.k).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityK.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.j).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityJ.class);
        startActivity(intent);
      }
    });

    findViewById(R.id.g).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, PlatLogoActivityG.class);
        startActivity(intent);
      }
    });


  }
}
