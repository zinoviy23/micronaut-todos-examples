package com.example

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class ToDoDataFetcher(
    private val toDoRepository: ToDoRepository
) : DataFetcher<Iterable<ToDo?>> {
    override fun get(environment: DataFetchingEnvironment?): Iterable<ToDo?> {
        return toDoRepository.findAll()
    }
}