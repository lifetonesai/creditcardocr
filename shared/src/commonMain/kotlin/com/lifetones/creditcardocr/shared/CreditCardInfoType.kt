package com.lifetones.creditcardocr.shared

enum class CreditCardInfoType(val regex: String) {
    NUMBER("\\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d"),
    NAME("[a-zA-Z. ]* [a-zA-Z.]*"),
    DATE("\\d\\d/\\d\\d")

}
