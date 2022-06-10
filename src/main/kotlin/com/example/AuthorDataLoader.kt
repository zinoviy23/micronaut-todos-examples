package com.example

import io.micronaut.scheduling.TaskExecutors
import jakarta.inject.Named
import jakarta.inject.Singleton
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.ExecutorService

@Singleton
class AuthorDataLoader(
    private val authorRepository: AuthorRepository,
    @Named(TaskExecutors.IO) private val executorService: ExecutorService,
) : MappedBatchLoader<Long, Author> {
    override fun load(keys: Set<Long>): CompletionStage<Map<Long, Author>> =
        CompletableFuture.supplyAsync({
            authorRepository.findByIdIn(keys.toList()).associateBy { it.id!! }
        }, executorService)
}