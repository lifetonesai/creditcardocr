package com.lifetones.creditcardocr

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lifetones.creditcardocr.shared.CreditCard
import com.lifetones.creditcardocr.shared.recognizeText
import junit.framework.TestCase.assertEquals
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
    fun validateCard1() {
        validateCard(
            R.drawable.creditcard1,
            CreditCard(
                number = "2221 0012 3412 3456",
                name = "Lee M. Cardholder",
                date = "12/23"
            )
        )
    }

    @Test
    fun validateCard2() {
        validateCard(
            R.drawable.creditcard2,
            CreditCard(
                number = "4000 1234 5678 9010",
                name = "EISHA KHANNA",
                date = "12/20"
            )
        )

    }

    fun validateCard(
        @DrawableRes imageResourceId: Int,
        creditCard: CreditCard
    ) {
        val latch = CountDownLatch(1)
        val testContext = InstrumentationRegistry.getInstrumentation().targetContext
        var inputStream: InputStream? = null

        try {
            val drawable: Drawable = ContextCompat.getDrawable(testContext, imageResourceId)!!
            inputStream = drawableToInputStream(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        assertNotNull(inputStream)
        val byteArray: ByteArray = inputStream.use { it!!.readBytes() }

        recognizeText(
            image = byteArray,
            onSuccess = {
                assertEquals(creditCard.number, it.number)
                assertEquals(creditCard.name, it.name)
                assertEquals(creditCard.date, it.date)
                latch.countDown()
            },
            onFailure = {
                latch.countDown()
            }
        )

        // Wait up to 5 seconds for the latch to count down to zero
        assertTrue("Callback timed out", latch.await(20, TimeUnit.SECONDS));

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
        }
        return null // Handle null or unsupported cases as needed
    }
}