package com.laioffer.tinnews.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.repository.NewsRepository;

public class HomeViewModel extends ViewModel { // 这个HomeViewModel后面是会放在home fragment里面用的

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }




    //根据 前面 ui的需求  来拆分 api  更加细化 独立控制
    // 在这一层，又拼接了一段pipe，这一段 输入参数country，
    // 另外一段插到下面的 repository::getTopHeadlines 的pipe， 下面的函数相当于高阶函数map stream，返回的getTopHeadlines
    //的返回的结果
    public void setCountryInput(String country) {
        countryInput.setValue(country);
    }

    public LiveData<NewsResponse> getTopHeadlines() {
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);//method reference
    }



}
