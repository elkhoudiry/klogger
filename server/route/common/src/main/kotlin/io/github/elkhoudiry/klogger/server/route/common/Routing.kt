package io.github.elkhoudiry.klogger.server.route.common

import io.ktor.server.application.ApplicationCall
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.util.pipeline.PipelineContext

fun Route.getInclusive(url: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit): Route {
    get("$url/", body)
    get(url, body)
    return this
}

fun Route.postInclusive(url: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit): Route {
    post("$url/", body)
    post(url, body)
    return this
}

fun Route.deleteInclusive(url: String, body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit): Route {
    delete("$url/", body)
    delete(url, body)
    return this
}