package com.example

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import io.micronaut.context.event.ApplicationEventPublisher
import jakarta.inject.Singleton
import javax.transaction.Transactional

data class ToDoCreated(val author: Author, val toDo: ToDo)

@Singleton
open class CreateToDoDataFetcher(
    private val toDoRepository: ToDoRepository,
    private val authorRepository: AuthorRepository,
    private val eventPublisher: ApplicationEventPublisher<ToDoCreated>,
) : DataFetcher<ToDo> {

    @Transactional
    override fun get(environment: DataFetchingEnvironment): ToDo {
        val title = environment.getArgument<String>("title")
        val username = environment.getArgument<String>("author")
        val author = authorRepository.findOrCreate(username)
        val toDo = ToDo(title, author.id!!)
        return toDoRepository.save(toDo).also {
            eventPublisher.publishEvent(ToDoCreated(author, toDo))
        }
    }
}