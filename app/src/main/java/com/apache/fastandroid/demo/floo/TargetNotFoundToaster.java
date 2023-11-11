/*
 * Copyright 2017 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.apache.fastandroid.demo.floo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.drakeet.floo.TargetNotFoundHandler;

/**
 * @author drakeet
 */
public class TargetNotFoundToaster implements TargetNotFoundHandler {

  @Override
  public boolean onTargetNotFound(
      @NonNull Context context,
      @NonNull Uri sourceUri,
      @NonNull Bundle extras,
      @Nullable Integer intentFlags) {

    Toast.makeText(context, "ERROR: " + sourceUri.toString(), Toast.LENGTH_SHORT).show();
    return true;
  }
}
