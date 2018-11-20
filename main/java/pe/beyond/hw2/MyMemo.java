package pe.beyond.hw2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MyMemo extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymemo);
        Intent it = getIntent();
        TextView tv = (TextView)findViewById(R.id.txt_shared);

        tv.setText(it.getStringExtra(Intent.EXTRA_TEXT));
    }
}
