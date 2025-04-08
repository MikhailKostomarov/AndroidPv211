package itstep.learning.androidpv211;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AnimActivity extends AppCompatActivity {
    Animation alphaAnimation;
    Animation rotateAnimation;
    Animation rotateAnimation2;
    Animation scaleAimation;
    Animation bellAimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_anim);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        alphaAnimation= AnimationUtils.loadAnimation(this,R.anim.anim_alpha);
        rotateAnimation=AnimationUtils.loadAnimation(this,R.anim.anim_rotate);
        rotateAnimation2=AnimationUtils.loadAnimation(this,R.anim.anim_rotate2);
        findViewById(R.id.anim_v_alpha).setOnClickListener(v->v.startAnimation(alphaAnimation));
        findViewById(R.id.anim_v_rotate).setOnClickListener(v->v.startAnimation(rotateAnimation));
        findViewById(R.id.anim_v_rotate2).setOnClickListener(v->v.startAnimation(rotateAnimation2));
        scaleAimation=AnimationUtils.loadAnimation(this,R.anim.anim_scale);
        findViewById(R.id.anim_v_scale).setOnClickListener(v->v.startAnimation(scaleAimation));

        bellAimation=AnimationUtils.loadAnimation(this,R.anim.dz_rotate);
        findViewById(R.id.anim_v_dz).setOnClickListener(v->v.startAnimation(bellAimation));

    }

}