package eugene.com.livelib;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;

public class LiveCall<RequestType> extends MediatorLiveData<Resource<RequestType>> {

    @MainThread
    @SuppressWarnings("ConstantConditions")
    public LiveCall(final LiveData<ApiResponse<RequestType>> apiCall) {
        setValue(Resource.loading(null));
        addSource(apiCall, response -> {
            removeSource(apiCall);
            if (response.isSuccessful()) {
                setValue(Resource.success(response.body));
            } else {
                setValue(Resource.error(response.errorMessage));
            }
        });
    }
}
