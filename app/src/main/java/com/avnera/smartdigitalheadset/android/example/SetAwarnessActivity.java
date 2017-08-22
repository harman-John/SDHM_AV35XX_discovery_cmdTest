package com.avnera.smartdigitalheadset.android.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avnera.av35xx.LightX;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SetAwarnessActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_awarness);
        Bundle bundle = getIntent().getBundleExtra("BUNDLE");
        try {
            LightX mLigtx = (LightX) bytes2Object(bundle.getByteArray("Obj_byte_array"));
            mLigtx.writeAppANCEnable(false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    /**
     * Converting byte arrays to objects
     */
    static public Object bytes2Object( byte raw[] )
            throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream( raw );
        ObjectInputStream ois = new ObjectInputStream( bais );
        Object o = ois.readObject();
        return o;
    }
}
