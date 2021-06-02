package ru.inversionkavkaz.rupay.data

import java.util.*

data class RegisterResponse(val reqID : Long,
                            val transactionID: Long,
                            val statusDate: Date,
                            val statusCode: Int,
                            val statusMessage: String)
