package com.apache.fastandroid.jetpack;


import com.tesla.framework.support.KidsException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/5/26.
 */
public class StateData<T> {

    @NonNull
    private DataStatus status;

    @Nullable
    private T data;

    @Nullable
    private KidsException error;

    public StateData() {
        this.status = DataStatus.CREATED;
        this.data = null;
        this.error = null;
    }

    public StateData<T> loading() {
        this.status = DataStatus.LOADING;
        this.data = null;
        this.error = null;
        return this;
    }

    public StateData<T> success(@NonNull T data) {
        this.status = DataStatus.SUCCESS;
        this.data = data;
        this.error = null;
        return this;
    }

    public StateData<T> empty() {
        this.status = DataStatus.EMPTY;
        this.data = null;
        this.error = null;
        return this;
    }

    public StateData<T> error(@NonNull KidsException error) {
        this.status = DataStatus.ERROR;
        this.data = null;
        this.error = error;
        return this;
    }

    public StateData<T> complete() {
        this.status = DataStatus.COMPLETE;
        return this;
    }

    @NonNull
    public DataStatus getStatus() {
        return status;
    }

    public boolean isLoading(){
        return DataStatus.LOADING == status;
    }

    public boolean isSuccess(){
        return DataStatus.SUCCESS == status;
    }

    public boolean isEmpty(){
        return DataStatus.EMPTY == status;
    }

    public boolean isError(){
        return DataStatus.ERROR == status;
    }

    public boolean isComplete(){
        return DataStatus.COMPLETE == status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public enum DataStatus {
        CREATED,
        LOADING,
        SUCCESS,
        ERROR,
        EMPTY,
        COMPLETE
    }
}