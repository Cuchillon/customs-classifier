package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.MetaParameter
import com.ferick.classifier.model.dto.UserSearchRequest
import com.ferick.classifier.model.dto.UserSearchRequestMeta
import com.ferick.classifier.service.VectorStoreService
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.filter.Filter.Expression
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder
import org.springframework.stereotype.Service

@Service
class VectorStoreServiceImpl(
    private val vectorStore: VectorStore
) : VectorStoreService {

    override fun storeDocuments(documents: List<Document>) {
        vectorStore.add(documents)
    }

    override fun searchDocuments(request: UserSearchRequest): List<Document> =
        vectorStore.similaritySearch(getSearchRequest(request)) ?: emptyList()

    private fun getSearchRequest(request: UserSearchRequest): SearchRequest {
        val searchRequestBuilder = SearchRequest.builder()
            .query(request.query)
            .topK(request.topK)
            .similarityThreshold(request.similarityThreshold)
        request.meta?.let {
            searchRequestBuilder.filterExpression(getFilterExpression(it))
        }
        return searchRequestBuilder.build()
    }

    private fun getFilterExpression(meta: UserSearchRequestMeta): Expression {
        val builder = FilterExpressionBuilder()
        val clientExpression = getExpressionUnit(MetaParameter.CLIENT, meta.clients, builder)
        val specExpression = getExpressionUnit(MetaParameter.SPECIFICATION, meta.specifications, builder)
        return when {
            clientExpression == null -> specExpression!!.build()
            specExpression == null -> clientExpression.build()
            else -> builder.and(clientExpression, specExpression).build()
        }
    }

    private fun getExpressionUnit(
        parameter: MetaParameter,
        values: List<String>,
        builder: FilterExpressionBuilder
    ) = when {
        values.size == 1 -> builder.eq(parameter.key, values[0])
        values.size > 1 -> builder.`in`(parameter.key, values)
        else -> null
    }
}
