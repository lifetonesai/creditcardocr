package com.lifetones.creditcardocr

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lifetones.creditcardocr.shared.recognizeText
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        val latch = CountDownLatch(1)
        val testContext = InstrumentationRegistry.getInstrumentation().targetContext
        var inputStream: InputStream? = null

        try {
            val drawable: Drawable = ContextCompat.getDrawable(testContext, R.drawable.creditcard1)!!
            inputStream = drawableToInputStream(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        assertNotNull(inputStream)
        val byteArray: ByteArray = inputStream.use { it!!.readBytes() }

        recognizeText(
            image = byteArray,
            onSuccess = {
                println(it)
                latch.countDown()
            },
            onFailure = {
                println(it)
                latch.countDown()
            }
        )

        // Wait up to 5 seconds for the latch to count down to zero
        assertTrue("Callback timed out", latch.await(5, TimeUnit.SECONDS));

    }

    private fun assertNotNull(obj: Any?) {
        if (obj == null) {
            throw AssertionError("Object is null")
        }
    }

    fun drawableToInputStream(drawable: Drawable): InputStream? {
        // If it's a BitmapDrawable, use the underlying bitmap directly for simplicity
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val stream = ByteArrayOutputStream()
            // Use a suitable compression format and quality (e.g., PNG for lossless)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return ByteArrayInputStream(stream.toByteArray())
        } else {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            // ... code to draw vector drawable to bitmap ...
            // A full implementation for VectorDrawable conversion is more complex.
            // For simple test cases, using a simple PNG or JPEG is often easier.
            // The Stack Overflow link below provides a basic approach for Bitmaps.
        }
        return null // Handle null or unsupported cases as needed
    }
}