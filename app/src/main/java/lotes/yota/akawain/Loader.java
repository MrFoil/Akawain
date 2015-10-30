package lotes.yota.akawain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class Loader{
    //final private Context context;

    public static String readRawFile(final Context context, final int resourceId) {
        final InputStream input = context.getResources().openRawResource(resourceId);
        final InputStreamReader reader = new InputStreamReader(input);
        final BufferedReader buffer = new BufferedReader(reader);

        String retrieved;

        final StringBuilder builder = new StringBuilder();

        try {
            while((retrieved = buffer.readLine()) != null) {
                builder.append(retrieved);
                builder.append('\n');
            }
        } catch(IOException e) {
            Log.e("activity", "Not smart enough to read this file" + e.toString());
            return null;
        }
        return builder.toString();
    }
}