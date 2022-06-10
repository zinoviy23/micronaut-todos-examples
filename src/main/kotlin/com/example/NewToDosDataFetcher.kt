package com.example

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.context.event.ApplicationEventListener
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Sinks

@Singleton
class NewToDosDataFetcher : DataFetcher<Publisher<ToDo>>, ApplicationEventListener<ToDoCreated> {
    private val events = Sinks.many().replay().latest<ToDoCreated>()

    override fun get(environment: DataFetchingEnvironment): Publisher<ToDo> {
        val author = environment.getArgument<String>("author")
        return events.asFlux().filter { it.author.username == author }.map { it.toDo }
    }

    override fun onApplicationEvent(event: ToDoCreated) {
        events.tryEmitNext(event)
    }
}