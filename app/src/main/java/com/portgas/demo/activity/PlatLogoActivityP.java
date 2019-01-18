package com.portgas.demo.activity;

import android.animation.TimeAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.View;
import android.widget.FrameLayout;

public class PlatLogoActivityP extends Activity {
  FrameLayout layout;
  TimeAnimator anim;
  PBackground bg;

  private class PBackground extends Drawable {
    private float maxRadius, radius, x, y, dp;
    private int[] palette;
    private int darkest;
    private float offset;

    public PBackground() {
      randomizePalette();
    }

    /**
     * set inner radius of "p" logo
     */
    public void setRadius(float r) {
      this.radius = Math.max(48*dp, r);
    }

    /**
     * move the "p"
     */
    public void setPosition(float x, float y) {
      this.x = x;
      this.y = y;
    }

    /**
     * for animating the "p"
     */
    public void setOffset(float o) {
      this.offset = o;
    }

    /**
     * rough luminance calculation
     * https://www.w3.org/TR/AERT/#color-contrast
     */
    public float lum(int rgb) {
      return ((Color.red(rgb) * 299f) + (Color.green(rgb) * 587f) + (Color.blue(rgb) * 114f)) / 1000f;
    }

    /**
     * create a random evenly-spaced color palette
     * guaranteed to contrast!
     */
    public void randomizePalette() {
      final int slots = 2 + (int)(Math.random() * 2);
      float[] color = new float[] { (float) Math.random() * 360f, 1f, 1f };
      palette = new int[slots];
      darkest = 0;
      for (int i=0; i<slots; i++) {
        palette[i] = Color.HSVToColor(color);
        color[0] += 360f/slots;
        if (lum(palette[i]) < lum(palette[darkest])) darkest = i;
      }

      final StringBuilder str = new StringBuilder();
      for (int c : palette) {
        str.append(String.format("#%08x ", c));
      }
      Log.v("PlatLogoActivity", "color palette: " + str);
    }

    @Override
    public void draw(Canvas canvas) {
      if (dp == 0) dp = getResources().getDisplayMetrics().density;
      final float width = canvas.getWidth();
      final float height = canvas.getHeight();
      if (radius == 0) {
        setPosition(width / 2, height / 2);
        setRadius(width / 6);
      }
      final float inner_w = radius * 0.667f;

      final Paint paint = new Paint();
      paint.setStrokeCap(Paint.Cap.BUTT);
      canvas.translate(x, y);

      Path p = new Path();
      p.moveTo(-radius, height);
      p.lineTo(-radius, 0);
      p.arcTo(-radius, -radius, radius, radius, -180, 270, false);
      p.lineTo(-radius, radius);

      float w = Math.max(canvas.getWidth(), canvas.getHeight())  * 1.414f;
      paint.setStyle(Paint.Style.FILL);

      int i=0;
      while (w > radius*2 + inner_w*2) {
        paint.setColor(0xFF000000 | palette[i % palette.length]);
        // for a slower but more complete version:
        // paint.setStrokeWidth(w);
        // canvas.drawPath(p, paint);
        canvas.drawOval(-w/2, -w/2, w/2, w/2, paint);
        w -= inner_w * (1.1f + Math.sin((i/20f + offset) * 3.14159f));
        i++;
      }

      // the innermost circle needs to be a constant color to avoid rapid flashing
      paint.setColor(0xFF000000 | palette[(darkest+1) % palette.length]);
      canvas.drawOval(-radius, -radius, radius, radius, paint);

      p.reset();
      p.moveTo(-radius, height);
      p.lineTo(-radius, 0);
      p.arcTo(-radius, -radius, radius, radius, -180, 270, false);
      p.lineTo(-radius + inner_w, radius);

      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeWidth(inner_w*2);
      paint.setColor(palette[darkest]);
      canvas.drawPath(p, paint);
      paint.setStrokeWidth(inner_w);
      paint.setColor(0xFFFFFFFF);
      canvas.drawPath(p, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter( ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
      return 0;
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    layout = new FrameLayout(this);
    setContentView(layout);

    bg = new PBackground();
    layout.setBackground(bg);

    layout.setOnTouchListener(new View.OnTouchListener() {
      final PointerCoords pc0 = new PointerCoords();
      final PointerCoords pc1 = new PointerCoords();

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
          case MotionEvent.ACTION_DOWN:
          case MotionEvent.ACTION_MOVE:
            if (event.getPointerCount() > 1) {
              event.getPointerCoords(0, pc0);
              event.getPointerCoords(1, pc1);
              bg.setRadius((float) Math.hypot(pc0.x - pc1.x, pc0.y - pc1.y) / 2f);
            }
            break;
        }
        return true;
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();

    bg.randomizePalette();

    anim = new TimeAnimator();
    anim.setTimeListener(
        new TimeAnimator.TimeListener() {
          @Override
          public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
            bg.setOffset((float) totalTime / 60000f);
            bg.invalidateSelf();
          }
        });

    anim.start();
  }

  @Override
  public void onStop() {
    if (anim != null) {
      anim.cancel();
      anim = null;
    }
    super.onStop();
  }
}
