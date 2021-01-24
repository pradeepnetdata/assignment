package com.barclays.stock.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.barclays.stock.data.IDataManager;
import com.barclays.stock.provider.SchedulerProvider;


public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

  private final IDataManager dataManager;
  private final SchedulerProvider schedulerProvider;


  public ViewModelProviderFactory(IDataManager dataManager,
                                  SchedulerProvider schedulerProvider) {
    this.dataManager = dataManager;
    this.schedulerProvider = schedulerProvider;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    return (T) new StockViewModel(dataManager,schedulerProvider);
     }
}