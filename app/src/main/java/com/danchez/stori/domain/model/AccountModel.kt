package com.danchez.stori.domain.model

import kotlin.random.Random

data class AccountModel(
    val id: String,
    val accountNumber: String,
    val balance: Long,
) {
    companion object {
        fun createInitialAccount(): Map<String, Any> {
            val accountNumber = generateAccountNumber()
            return mapOf(
                "accountNumber" to accountNumber,
                "balance" to 0,
            )
        }

        private fun generateAccountNumber(): String {
            val randomNumber = StringBuilder()
            repeat(16) {
                randomNumber.append(Random.nextInt(0, 10))
            }
            return randomNumber.toString()
        }
    }
}
