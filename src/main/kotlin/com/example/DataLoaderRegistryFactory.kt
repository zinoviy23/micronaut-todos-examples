package com.example

import io.micronaut.context.annotation.Factory
import io.micronaut.runtime.http.scope.RequestScope
import org.dataloader.DataLoader
import org.dataloader.DataLoaderRegistry
import org.slf4j.LoggerFactory

@Factory
class DataLoaderRegistryFactory {
    companion object {
        private val LOG = LoggerFactory.getLogger(DataLoaderRegistryFactory::class.java)
    }

    @RequestScope
    fun dataLoaderFactory(authorDataLoader: AuthorDataLoader): DataLoaderRegistry {
        val dataLoaderRegistry = DataLoaderRegistry()
        dataLoaderRegistry.register("author", DataLoader.newMappedDataLoader(authorDataLoader))

        LOG.trace("new author loader")

        return dataLoaderRegistry
    }
}