package com.example

import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import javax.validation.constraints.NotNull

@JdbcRepository(dialect = Dialect.POSTGRES)
abstract class AuthorRepository : CrudRepository<Author, Long> {
    abstract fun findByUsername(username: String): Author?

    abstract fun findByIdIn(id: Collection<Long>): Collection<Author>

    fun findOrCreate(username: String): Author {
        return findByUsername(username) ?: save(Author(username))
    }
}