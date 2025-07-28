package api.kotlinproject.backend.repo.cassandra

import api.kotlinproject.backend.repo.cassandra.model.MlCassandraDTO
import api.kotlinproject.common.repo.DbMlFilterRequest
import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class MlCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<MlCassandraDTO>
) {
    fun search(filter: DbMlFilterRequest): CompletionStage<Collection<MlCassandraDTO>> {
        var select = entityHelper.selectStart().allowFiltering()

        if (filter.titleFilter.isNotBlank()) {
            // Внимание! При использовании LIKE необходимо использовать SASI индексы.
            // При использовании SASI индекса типа StandardAnalyzer происходит токенизация текста по пробелам.
            // Оператор LIKE в этом случае должен быть НЕ LIKE '%<токен>%' а LIKE '<токен>%'
            select = select
                .whereColumn(MlCassandraDTO.COLUMN_TITLE)
                .like(QueryBuilder.literal("${filter.titleFilter}%"))
        }


        val asyncFetcher = AsyncFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(asyncFetcher)

        return asyncFetcher.stage
    }

    inner class AsyncFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<MlCassandraDTO>()
        private val future = CompletableFuture<Collection<MlCassandraDTO>>()
        val stage: CompletionStage<Collection<MlCassandraDTO>> = future

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> future.completeExceptionally(t)
                resultSet == null -> future.completeExceptionally(IllegalStateException("ResultSet should not be null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it, false) })
                    if (resultSet.hasMorePages())
                        resultSet.fetchNextPage().whenComplete(this)
                    else
                        future.complete(buffer)
                }
            }
        }
    }
}