package com.lifetones.creditcardocr.shared

enum class CreditCardInfoType(val regex: String) {
    NUMBER("\\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d \\d\\d\\d\\d") {
        override fun isValid(text: String): Boolean {
            return this.regex.toRegex().containsMatchIn(text)
        }

        override fun extract(text: String): String {
            val pattern = Regex(this.regex)
            return pattern.find(text)?.value ?: ""
        }
    },
    NAME("[a-zA-Z] [a-zA-Z.]") {
        override fun isValid(text: String): Boolean {
            return this.regex.toRegex().containsMatchIn(text)
        }

        override fun extract(text: String): String {
            val pattern = Regex(this.regex)
            return pattern.find(text)?.value ?: ""
        }
    },
    DATE("\\d\\d/\\d\\d") {
        override fun isValid(text: String): Boolean {
            return this.regex.toRegex().containsMatchIn(text)
        }

        override fun extract(text: String): String {
            val pattern = Regex(this.regex)
            return pattern.find(text)?.value ?: ""
        }
    };

    abstract fun isValid(text: String): Boolean
    abstract fun extract(text: String): String

}
