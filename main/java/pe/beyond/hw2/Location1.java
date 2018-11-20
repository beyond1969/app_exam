package pe.beyond.hw2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Location1 extends AppCompatActivity{
    String str_day;
    String str_place;
    String str_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_1);
        Calendar cal = Calendar.getInstance();
        int a = cal.get(Calendar.YEAR);
        int b = cal.get(Calendar.MONTH) + 1;
        int c = cal.get(Calendar.DATE);
        str_day = "" + Integer.toString(a) + Integer.toString(b) + Integer.toString(c);
        Log.d("THIS_IS_WORLD","READ_ " + str_day);
        Intent it = getIntent();
        str_place = it.getStringExtra("place");
        str_url = "http://openAPI.seoul.go.kr:8088/5254774178626579313030794a68564d/json/DailyAverageAirQuality/1/1/" + str_day + "/" + str_place;
        Task t = new Task();
        t.execute(str_url);
    }
    class Messages{
        double m_PM10;
        double m_PM25;
        Messages(double m1, double m2){
            this.m_PM10 = m1;
            this.m_PM25 = m2;
        }
    }

    class Task extends AsyncTask<String, Void, List<Messages>> {

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
                TextView t0 = findViewById(R.id.txt_row_1_0);
                TextView t1 = findViewById(R.id.txt_row_1_1);
                t0.setText(Double.toString(m.m_PM10));
                t1.setText(Double.toString(m.m_PM25));
            }
        }


        Messages readDebugMessages(JsonReader jr) throws IOException {
            Messages m = null;
            double txt_PM10 = 0, txt_PM25 = 0;
            jr.beginArray();
            jr.beginObject();
            while(jr.hasNext()){
                String name = jr.nextName();
                if(name.equals("PM10")){
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
            m = new Messages(txt_PM10, txt_PM25);
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
