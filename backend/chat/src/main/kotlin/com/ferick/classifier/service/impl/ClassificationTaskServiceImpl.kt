package com.ferick.classifier.service.impl

import com.ferick.classifier.model.dto.ClassificationTaskCreateRequest
import com.ferick.classifier.model.dto.ClassificationTaskCreateResponse
import com.ferick.classifier.model.dto.ClassificationTaskStatusResponse
import com.ferick.classifier.model.entity.ClassificationData
import com.ferick.classifier.model.entity.ClassificationSubtask
import com.ferick.classifier.model.entity.ClassificationTask
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.repository.ClassificationTaskRepository
import com.ferick.classifier.service.ClassificationTaskService
import com.ferick.classifier.service.FileStorageService
import com.ferick.classifier.service.documents.ExcelDocumentParser
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ClassificationTaskServiceImpl(
    private val classificationTaskRepository: ClassificationTaskRepository,
    private val classificationSubtaskRepository: ClassificationSubtaskRepository,
    private val excelDocumentParser: ExcelDocumentParser,
    private val fileStorageService: FileStorageService
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

    @Transactional(readOnly = true)
    override fun getStatus(id: Long): ClassificationTaskStatusResponse =
        classificationTaskRepository.findById(id).map {
            ClassificationTaskStatusResponse(id, it.status)
        }.orElseThrow { IllegalArgumentException("ClassificationTask with id $id not found") }

    @Transactional(readOnly = true)
    override fun download(id: Long): ByteArray =
        classificationTaskRepository.findById(id).map {
            val storageFileId = it.storageFileId
                ?: throw IllegalStateException("Storage file id for classification task $id not found")
            fileStorageService.get(storageFileId)
        }.orElseThrow { IllegalArgumentException("ClassificationTask with id $id not found") }
}
