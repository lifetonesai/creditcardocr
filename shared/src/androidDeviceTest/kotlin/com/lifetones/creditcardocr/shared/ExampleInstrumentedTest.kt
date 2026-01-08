package com.lifetones.creditcardocr.shared

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    companion object {
        const val IMAGE_PATH = "shared/src/androidDeviceTest/assets/creditcard1.png"
    }

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        var inputStream: InputStream? = null
        try {
            inputStream = appContext.assets.open(IMAGE_PATH)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        assertNotNull(inputStream)
        val byteArray: ByteArray = inputStream.use { it!!.readBytes() }

        recognizeText(
            image = byteArray,
            onSuccess = {
                println(it)
            },
            onFailure = {
                println(it)
            }
        )

    }

    private fun assertNotNull(obj: Any?) {
        if (obj == null) {
            throw AssertionError("Object is null")
        }
    }
}