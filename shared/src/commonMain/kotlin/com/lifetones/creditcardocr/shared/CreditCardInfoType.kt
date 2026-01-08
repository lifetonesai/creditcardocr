package com.lifetones.creditcardocr.shared

enum class CreditCardInfoType(val regex: String) {
    NUMBER("\\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d") {
        override fun isValid(text: String): Boolean {
            return this.regex.toRegex().containsMatchIn(text)
        }
    },
    NAME("[a-zA-Z.]") {
        override fun isValid(text: String): Boolean {
            return this.regex.toRegex().containsMatchIn(text)
        }
    },
    DATE("\\d\\d/\\d\\d") {
        override fun isValid(text: String): Boolean {
            return this.regex.toRegex().containsMatchIn(text)
        }
    };

    abstract fun isValid(text: String): Boolean

}
