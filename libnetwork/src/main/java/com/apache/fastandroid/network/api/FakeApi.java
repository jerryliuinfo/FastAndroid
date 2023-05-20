package com.apache.fastandroid.network.api;

import com.apache.fastandroid.network.model.FakeThing;
import com.apache.fastandroid.network.model.FakeToken;

import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

/**
 * Created by Jerry on 2021/9/9.
 */
public class FakeApi {

   Random random = new Random();

   private static String createToken() {
      return "fake_token_" + System.currentTimeMillis() % 10000;
   }

   public Observable<FakeThing> getFakeData(FakeToken fakeToken) {
      return Observable.just(fakeToken)
              .map(new Function<FakeToken, FakeThing>() {
                 @Override
                 public FakeThing apply(FakeToken fakeToken) {
                    // Add some random delay to mock the network delay
                    int fakeNetworkTimeCost = random.nextInt(500) + 500;
                    try {
                       Thread.sleep(fakeNetworkTimeCost);
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                    }

                    if (fakeToken.expired) {
                       throw new IllegalArgumentException("Token expired!");
                    }

                    FakeThing fakeData = new FakeThing();
                    fakeData.id = (int) (System.currentTimeMillis() % 1000);
                    fakeData.name = "FAKE_USER_" + fakeData.id;
                    return fakeData;
                 }
              });
   }
}
