package ru.inversionkavkaz.rupay.data

data class AccountResult(
        val cardnum: String?,
        val name : String?,
        val errorCode: Int?,
        val errorMessage: String?
)