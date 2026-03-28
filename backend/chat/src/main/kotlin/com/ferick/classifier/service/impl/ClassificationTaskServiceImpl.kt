package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskCreateResponse
import com.ferick.classifier.model.entity.ClassificationData
import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationTask
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.repository.ClassificationTaskRepository
import com.ferick.classifier.service.ClassificationTaskService
import com.ferick.classifier.service.documents.ExcelDocumentParser
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClassificationTaskServiceImpl(
    private val classificationTaskRepository: ClassificationTaskRepository,
    private val classificationSubtaskRepository: ClassificationSubtaskRepository,
    private val excelDocumentParser: ExcelDocumentParser
) : ClassificationTaskService {

    @Transactional
    override fun create(request: ClassificationTaskCreateRequest): ClassificationTaskCreateResponse {
        val resource = ByteArrayResource(request.data)
        val rows = excelDocumentParser.parse(resource)

        val task = classificationTaskRepository.save(
            ClassificationTask(meta = request.meta)
        )

        classificationSubtaskRepository.saveAll(
            rows.chunked(10) { batch ->
                val subtask = ClassificationSubtask(
                    classificationTask = task
                )
                batch.forEach {
                    subtask.data.add(
                        ClassificationData(text = it, classificationSubtask = subtask)
                    )
                }

                return@chunked subtask
            }
        )

        return ClassificationTaskCreateResponse(task.id!!)
    }
}
