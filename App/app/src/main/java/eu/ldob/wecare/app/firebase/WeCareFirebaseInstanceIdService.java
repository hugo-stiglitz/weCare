package eu.ldob.wecare.app.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import eu.ldob.wecare.service.logic.ServiceHandler;

public class WeCareFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        ServiceHandler.getService().setFirebaseToken(FirebaseInstanceId.getInstance().getToken());
    }
}
