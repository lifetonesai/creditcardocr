package com.lifetones.creditcardocr.shared


class RegexValidator {
    fun isValid(text: String, type: CreditCardInfoType): Boolean {
        return type.regex.toRegex().containsMatchIn(text)
    }

    fun extract(text: String, type: CreditCardInfoType): String {
        return Regex(type.regex).find(text)?.value ?: ""
    }
}
