package com.lifetones.creditcardocr.shared

import android.graphics.BitmapFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


actual fun recognizeText(
    image: ByteArray,
    onSuccess: (CreditCard) -> Unit,
    onFailure: (Exception) -> Unit
) {
    TextRecognizerOptions.CREDIT_CARD
    val image = BitmapFactory.decodeByteArray(image, 0, image.size)
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.Builder().build())
    val inputImage = InputImage.fromBitmap(image, 0)
    recognizer.process(inputImage)
        .addOnSuccessListener { visionText ->
            var number = ""
            var name = ""
            var date = ""
            visionText.textBlocks.forEach {
                if(CreditCardInfoType.NUMBER.isValid(it.text)){
                    number = CreditCardInfoType.NUMBER.extract(it.text)
                }
                if(CreditCardInfoType.NAME.isValid(it.text)){
                    name = it.text
                }
                if(CreditCardInfoType.DATE.isValid(it.text)){
                    date = CreditCardInfoType.DATE.extract(it.text)
                }
            }
            val creditCard = CreditCard(
                number = number,
                name = name,
                date = date
            )
            onSuccess(creditCard)
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }

}