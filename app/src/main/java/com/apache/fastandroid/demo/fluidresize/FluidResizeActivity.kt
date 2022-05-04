package com.apache.fastandroid.demo.fluidresize

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apache.fastandroid.R

class FluidResizeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fluid_resize)

    FluidContentResizer.listen(this)
  }
}
