package com.thisisshivamgupta.enews;

import com.thisisshivamgupta.enews.Models.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadlines> list,String message);

    void onError(String message);

}
