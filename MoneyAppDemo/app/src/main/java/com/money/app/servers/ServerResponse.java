package com.money.app.servers;

/**
 * Created by xyz on 13-12-2017.
 */

public interface ServerResponse<T> {
    void success(T t, int code);
    void error(T t, int code);

}
