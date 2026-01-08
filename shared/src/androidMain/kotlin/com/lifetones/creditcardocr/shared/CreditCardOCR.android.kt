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
            val regexValidator  = RegexValidator()
            visionText.textBlocks.forEach {
                if(regexValidator.isValid(it.text, CreditCardInfoType.NUMBER)){
                    number = regexValidator.extract(it.text, CreditCardInfoType.NUMBER)
                }
                if(regexValidator.isValid(it.text, CreditCardInfoType.NAME)){
                    name = regexValidator.extract(it.text, CreditCardInfoType.NAME)
                }
                if(regexValidator.isValid(it.text, CreditCardInfoType.DATE)){
                    date = regexValidator.extract(it.text, CreditCardInfoType.DATE)
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