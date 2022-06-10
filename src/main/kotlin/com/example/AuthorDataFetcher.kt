package com.example

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton
import java.util.concurrent.CompletionStage

@Singleton
class AuthorDataFetcher : DataFetcher<CompletionStage<Author>> {
    override fun get(environment: DataFetchingEnvironment): CompletionStage<Author> {
        val toDo = environment.getSource<ToDo>()
        val authorDataLoader = environment.getDataLoader<Long, Author>("author")
        return authorDataLoader.load(toDo.authorId)
    }
}