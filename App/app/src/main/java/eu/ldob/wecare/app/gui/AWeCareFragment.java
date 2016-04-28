package eu.ldob.wecare.app.gui;

import android.support.v4.app.Fragment;

import eu.ldob.wecare.app.service.Service;

public abstract class AWeCareFragment extends Fragment {

    public abstract void setService(Service service);
}