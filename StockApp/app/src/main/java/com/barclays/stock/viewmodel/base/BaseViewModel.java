package com.barclays.stock.viewmodel.base;


import androidx.lifecycle.ViewModel;


import com.barclays.stock.data.IDataManager;
import com.barclays.stock.provider.SchedulerProvider;
import java.lang.ref.WeakReference;
import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseViewModel extends ViewModel {

    private final IDataManager mDataManager;

    private boolean mIsLoading = false;

    private final SchedulerProvider mSchedulerProvider;

    private CompositeDisposable mCompositeDisposable;



    public BaseViewModel(IDataManager dataManager,
                         SchedulerProvider schedulerProvider) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public IDataManager getDataManager() {
        return mDataManager;
    }

    public boolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading= isLoading;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }
}
