package eu.ldob.wecare.app.gui;

import android.support.v4.app.Fragment;

import eu.ldob.wecare.service.logic.Service;

public abstract class AWeCareFragment extends Fragment {

    public abstract void setService(Service service);
}