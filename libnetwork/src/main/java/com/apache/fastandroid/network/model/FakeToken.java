// (c)2016 Flipboard Inc, All Rights Reserved.

package com.apache.fastandroid.network.model;

import java.io.Serializable;

public class FakeToken implements Serializable {
    public String token;
    public boolean expired;

    public FakeToken() {
    }

    public FakeToken(boolean expired) {
        this.expired = expired;
    }
}
