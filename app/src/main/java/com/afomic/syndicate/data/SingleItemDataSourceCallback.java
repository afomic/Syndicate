package com.afomic.syndicate.data;

public interface SingleItemDataSourceCallback<T> extends DataSourceCallback {
    void onSuccess(T response);
}
