package com.lifetones.creditcardocr.shared

expect fun recognizeText(image: ByteArray, onSuccess: (CreditCard) -> Unit, onFailure: (Exception) -> Unit)
