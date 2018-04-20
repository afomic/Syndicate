package com.afomic.syndicate.data;

import java.util.List;

public interface ListDataSourceCallback<T> extends DataSourceCallback {
    void onSuccess(List<T> response);
}
