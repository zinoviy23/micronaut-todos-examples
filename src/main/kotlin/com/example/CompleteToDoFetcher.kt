package com.example

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton

@Singleton
class CompleteToDoFetcher(
    private val toDoRepository: ToDoRepository,
) : DataFetcher<Boolean> {
    override fun get(environment: DataFetchingEnvironment): Boolean {
        val id = environment.getArgument<String>("id").toLong()

        return toDoRepository.findById(id)
            .map { setCompletedAndUpdate(it) }
            .orElse(false)
    }

    private fun setCompletedAndUpdate(toDo: ToDo): Boolean {
        toDo.completed = true
        toDoRepository.update(toDo)
        return true
    }
}