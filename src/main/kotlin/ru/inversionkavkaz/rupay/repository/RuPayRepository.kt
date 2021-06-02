package ru.inversionkavkaz.rupay.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.inversionkavkaz.rupay.data.AccountResult
import ru.inversionkavkaz.rupay.data.RegisterRequest
import ru.inversionkavkaz.rupay.data.RegisterResponse
import ru.inversionkavkaz.rupay.log
import java.math.BigDecimal
import java.util.*


@Repository
class RuPayRepository(jdbcTemplate: JdbcTemplate) {
    final val SCHEMA_NAME = "XXI"
    final val CATALOG_NAME = "IK_RUPAY"
    val getAccount = SimpleJdbcCall(jdbcTemplate).withSchemaName(SCHEMA_NAME).withCatalogName(CATALOG_NAME).withProcedureName("getCardInfo")
    val register = SimpleJdbcCall(jdbcTemplate).withSchemaName(SCHEMA_NAME).withCatalogName(CATALOG_NAME).withProcedureName("register")

    @Transactional
    fun getCardInfo(cardnum: String): AccountResult {
        log("rupay getCardInfo - id=$cardnum")
        val ps = MapSqlParameterSource().addValue("pCardNum", cardnum)
        val out = TreeMap<String, Any>(String.CASE_INSENSITIVE_ORDER)
        out.putAll(getAccount.execute(ps))

        val errorCode = out["pErrorCode"] as BigDecimal?
        val errorMessage = out["pErrorMessage"] as String?
        val errorMessageExt = out["pErrorMessageExt"] as String?
        val cardname = out["pCardName"] as String?

        log("rupay getCardInfoResult - card=$cardnum, name=$cardname, errorCode=$errorCode, errorMessageExt=$errorMessageExt")
        return AccountResult(cardnum, cardname, errorCode?.toInt(), errorMessage)
    }

    /**
     * Произвести регистрацию перевода
     **
    procedure register(pReqID  varchar2  -- Уникальный идентификатор запроса на проведение платежа
    ,pReqTime           date          -- Дата платежа из запроса
    ,pReqSum            number        -- Сумма платежа из запроса
    ,pReqCardNum        varchar2      -- Номер карты зачисления платежа
    ,pTransactionID     OUT number    -- ID транзакции
    ,pStatusDate        OUT date      -- Дата статуса
    ,pStatusCode        OUT number    -- Код статуса
    ,pStatusMessage     OUT varchar2  -- Текст статуса
    ,pErrorMessageExt   OUT varchar2  -- Текст ошибки расширенный
    )
    */
    @Transactional
    fun register(registerRequest: RegisterRequest): RegisterResponse {
        log("rupay register - registerRequest=$registerRequest")
        val ps = MapSqlParameterSource()
                .addValue("pReqID", registerRequest.reqId)
                .addValue("pReqTime", registerRequest.reqTime)
                .addValue("pReqSum", registerRequest.reqSum)
                .addValue("pReqCardNum", registerRequest.reqCardNum)

        val out = TreeMap<String, Any>(String.CASE_INSENSITIVE_ORDER)
        out.putAll(register.execute(ps))

        val transactionID = out["pTransactionID"] as BigDecimal
        val statusDate = out["pStatusDate"] as Date
        val statusCode = out["pStatusCode"] as BigDecimal
        val statusMessage = out["pStatusMessage"] as String
        val errorMessageExt = out["pErrorMessageExt"] as String?

        log("rupay registerResult - transactionID=$transactionID, statusDate=$statusDate, statusCode=$statusCode, statusMessage=$statusMessage, errorMessageExt=$errorMessageExt")
        return RegisterResponse(registerRequest.reqId, transactionID.toLong(), statusDate, statusCode.toInt(), statusMessage)
    }
}

