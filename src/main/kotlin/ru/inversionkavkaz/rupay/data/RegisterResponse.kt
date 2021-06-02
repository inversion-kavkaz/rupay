package ru.inversionkavkaz.rupay.data

import java.util.*

data class RegisterResponse(val reqID : Long,
                            var transactionID: Long,
                            var statusDate: Date,
                            var statusCode: Int,
                            var statusMessage: String)
