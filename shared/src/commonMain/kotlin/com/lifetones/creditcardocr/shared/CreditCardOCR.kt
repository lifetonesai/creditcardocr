package com.lifetones.creditcardocr.shared

expect fun recognizeCreditCardText(image: ByteArray, onSuccess: (CreditCard) -> Unit, onFailure: (Exception) -> Unit)
