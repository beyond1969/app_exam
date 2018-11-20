package pe.beyond.hw2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Location0 extends AppCompatActivity{
    String str_day;
    String str_place;
    String str_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_0);
        Intent it = getIntent();
        str_day = it.getStringExtra("day");
        str_place = it.getStringExtra("place");
        str_url = "http://openAPI.seoul.go.kr:8088/5254774178626579313030794a68564d/json/DailyAverageAirQuality/1/1/" + str_day + "/" + str_place;
        Task t = new Task();
        t.execute(str_url);
    }

    class Messages{
        double m_NO2;
        double m_O3;
        double m_CO;
        double m_SO3;
        double m_PM10;
        double m_PM25;
        Messages(double m1, double m2, double m3, double m4, double m5, double m6){
            this.m_NO2 = m1;
            this.m_O3 = m2;
            this.m_CO = m3;
            this.m_SO3 = m4;
            this.m_PM10 = m5;
            this.m_PM25 = m6;
        }
    }
    class Task extends AsyncTask<String, Void, List<Messages>>{

        @Override
        protected List<Messages> doInBackground(String... strings) {
            List<Messages> list = new ArrayList();
            try {
                InputStream input = new URL(strings[0]).openStream();
                list = readJsonStream(input);
            }catch(Exception e){

            }
            return list;
        }

        @Override
        protected void onPostExecute(List<Messages> list){
            for (Messages m: list) {
                TextView t0 = findViewById(R.id.txt_row_0_0);
                TextView t1 = findViewById(R.id.txt_row_0_1);
                TextView t2 = findViewById(R.id.txt_row_0_2);
                TextView t3 = findViewById(R.id.txt_row_0_3);
                TextView t4 = findViewById(R.id.txt_row_0_4);
                TextView t5 = findViewById(R.id.txt_row_0_5);
                t0.setText(Double.toString(m.m_NO2));
                t1.setText(Double.toString(m.m_O3));
                t2.setText(Double.toString(m.m_CO));
                t3.setText(Double.toString(m.m_SO3));
                t4.setText(Double.toString(m.m_PM10));
                t5.setText(Double.toString(m.m_PM25));
            }
        }


        Messages readDebugMessages(JsonReader jr) throws IOException{
            Messages m = null;
            double txt_NO2 = 0, txt_O3 = 0, txt_CO = 0, txt_SO2 = 0, txt_PM10 = 0, txt_PM25 = 0;
            jr.beginArray();
            jr.beginObject();
            while(jr.hasNext()){
                String name = jr.nextName();
                if(name.equals("NO2")){
                    txt_NO2 = jr.nextDouble();
                }
                else if(name.equals("O3")){
                    txt_O3 = jr.nextDouble();
                }
                else if(name.equals("CO")){
                    txt_CO = jr.nextDouble();
                }
                else if(name.equals("SO2")){
                    txt_SO2 = jr.nextDouble();
                }
                else if(name.equals("PM10")){
                    txt_PM10 = jr.nextDouble();
                }
                else if(name.equals("PM25")){
                    txt_PM25 = jr.nextDouble();
                }
                else{
                    jr.skipValue();
                }
            }
            jr.endObject();
            jr.endArray();
            m = new Messages(txt_NO2, txt_O3, txt_CO, txt_SO2, txt_PM10, txt_PM25);
            return m;
        }
        Messages readRow(JsonReader jr) throws IOException{
            Messages m = null;
            jr.beginObject();
            while(jr.hasNext()){
                String s = jr.nextName();
                if(s.equals("row")){
                    m = readDebugMessages(jr);
                }
                else{
                    jr.skipValue();
                }
            }
            jr.endObject();
            return m;
        }
        List<Messages> readDebug(JsonReader jr) throws IOException{
            List<Messages> messages = new ArrayList<Messages>();
            jr.beginObject();
            while(jr.hasNext()){
                String s = jr.nextName();
                if(s.equals("DailyAverageAirQuality")){
                    messages.add(readRow(jr));
                }
                else{
                    jr.skipValue();
                }
            }
            jr.endObject();
            return messages;
        }
        List<Messages> readJsonStream(InputStream in) throws IOException{
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            try{
                return readDebug(reader);
            } finally {
                reader.close();
            }
        }
    }
}
