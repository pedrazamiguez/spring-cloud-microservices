package eu.pedrazamiguez.microservices.api.gateway.filter

import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono

@Configuration
class ExchangeLoggingFilter {

    private val logger = LoggerFactory.getLogger(ExchangeLoggingFilter::class.java)

    @Bean
    fun preRequestLoggingFilter() = GlobalFilter { exchange, chain ->
        logger.info("REQ ${exchange.request.id}: Endpoint -> [${exchange.request.method}] ${exchange.request.path}")
        logger.info("REQ ${exchange.request.id}: Headers -> ${exchange.request.headers}")
        logger.info("REQ ${exchange.request.id}: Params -> ${exchange.request.queryParams}")
        logger.info("REQ ${exchange.request.id}: Body -> ${exchange.request.body}")
        logger.info("REQ ${exchange.request.id}: From -> ${exchange.request.remoteAddress}")
        chain.filter(exchange)
    }

    @Bean
    fun postRequestLoggingFilter() = GlobalFilter { exchange, chain ->
        chain.filter(exchange).then(
            Mono.fromRunnable {
                logger.info("RES ${exchange.request.id}: Status -> ${exchange.response.statusCode}")
                logger.info("RES ${exchange.request.id}: Headers -> ${exchange.response.headers}")
            }
        )
    }

}
