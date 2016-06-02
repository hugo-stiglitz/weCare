package eu.ldob.wecare.app.gui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.ldob.wecare.app.R;

public class DummyFragment extends Fragment {

    public DummyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dummy, container, false);

        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText("abc");

        return view;
    }
}
