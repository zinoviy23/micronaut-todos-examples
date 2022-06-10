package com.example

import graphql.GraphQL
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import io.micronaut.context.annotation.Factory
import io.micronaut.core.io.ResourceResolver
import jakarta.inject.Singleton

@Factory
class GraphQLFactory {

    @Singleton
    fun graphQl(
        resourceResolver: ResourceResolver,
        toDoDataFetcher: ToDoDataFetcher,
        createToDoDataFetcher: CreateToDoDataFetcher,
        completeToDoFetcher: CompleteToDoFetcher,
        authorDataFetcher: AuthorDataFetcher,
        newToDosDataFetcher: NewToDosDataFetcher,
    ): GraphQL {
        val schemaParser = SchemaParser()
        val schemaGenerator = SchemaGenerator()

        val schemaDefinition = resourceResolver.getResourceAsStream("classpath:schema.graphqls").orElseThrow()

        val typeRegistry = TypeDefinitionRegistry()
        typeRegistry.merge(schemaParser.parse(schemaDefinition.bufferedReader()))

        val runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type("Query") {
                it.dataFetcher("toDos", toDoDataFetcher)
            }
            .type("Mutation") {
                it.dataFetcher("createToDo", createToDoDataFetcher)
                    .dataFetcher("completeToDo", completeToDoFetcher)
            }
            .type("ToDo") {
                it.dataFetcher("author", authorDataFetcher)
            }
            .type("Subscription") {
                it.dataFetcher("newToDos", newToDosDataFetcher)
            }
            .build()

        val graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)

        return GraphQL.newGraphQL(graphQLSchema).build()
    }
}