package ru.inversionkavkaz.rupay

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import java.io.File

@PropertySource(value = ["file:./application.properties"], ignoreResourceNotFound = true)
@SpringBootApplication
class RupayApplication

//@PostConstruct
//fun init() { // Setting Spring Boot SetTimeZone
//    //TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
//    TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
//}

fun main(args: Array<String>) {
	setupTnsNames()
	runApplication<RupayApplication>(*args)
}


/**
 * Устанавливает параметр oracle.net.tns_admin для общения с бд через tnsnames.
 * **/
fun setupTnsNames() {
	var tnsAdmin: String? = System.getenv("TNS_ADMIN")
	if (tnsAdmin == null) {
		val oracleHome = System.getenv("ORACLE_HOME") ?: return
		tnsAdmin = oracleHome + File.separatorChar + "network" + File.separatorChar + "admin"
	}
	System.setProperty("oracle.net.tns_admin", tnsAdmin)
}

inline fun <reified T> T.log(text: String, vararg variable: String) {
	LoggerFactory.getLogger(T::class.java).info(text, variable)
}

inline fun <reified T> T.logD(text: String, vararg variable: String) {
	LoggerFactory.getLogger(T::class.java).info(text, variable)
}

inline fun <reified T> T.logW(text: String, vararg variable: String) {
	LoggerFactory.getLogger(T::class.java).info(text, variable)
}

inline fun <reified T> T.logE(text: String, vararg variable: String) {
	LoggerFactory.getLogger(T::class.java).error(text, variable)
}

inline fun <reified T> T.logE(text: String, e: Throwable) {
	LoggerFactory.getLogger(T::class.java).error(text, e)
}
