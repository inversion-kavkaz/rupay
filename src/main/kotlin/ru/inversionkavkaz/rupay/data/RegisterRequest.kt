package ru.inversionkavkaz.rupay.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class RegisterRequest(@NotNull @Min(1) @JsonProperty("id") val reqId: Long,
                           @NotNull @JsonProperty("time") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+3") val reqTime: Date,
                           @NotNull @JsonProperty("sum") val reqSum: BigDecimal,
                           @NotNull @Length(min = 16, max = 20) @JsonProperty("cardnum") val reqCardNum: String)
