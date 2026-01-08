package com.lifetones.creditcardocr.shared



actual fun recognizeText(image: ByteArray, onSuccess: (CreditCard) -> Unit, onFailure: (Exception) -> Unit) {
}