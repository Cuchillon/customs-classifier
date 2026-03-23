package com.ferick.classifier.service.handlers.task

import com.ferick.classifier.common.extensions.toResult
import com.ferick.classifier.model.entity.ClassificationSubtaskStatus
import com.ferick.classifier.model.entity.ClassificationTask
import com.ferick.classifier.model.entity.ClassificationTaskStatus
import com.ferick.classifier.repository.ClassificationSubtaskRepository
import com.ferick.classifier.repository.ClassificationTaskRepository
import com.ferick.classifier.service.documents.ExcelDocumentPrinter
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PrintHandler(
    private val classificationTaskRepository: ClassificationTaskRepository,
    private val classificationSubtaskRepository: ClassificationSubtaskRepository,
    private val excelDocumentPrinter: ExcelDocumentPrinter
) : ClassificationTaskHandler {
    override val startStatus = ClassificationTaskStatus.COMPLETED
    override val endStatus = ClassificationTaskStatus.DONE

    @Transactional
    override fun process(task: ClassificationTask) {
        task.id?.also {
            val subtasks = classificationSubtaskRepository.findByClassificationTaskId(it)
            excelDocumentPrinter.print(subtasks.toResult())
            subtasks.forEach { subtask ->
                subtask.status = ClassificationSubtaskStatus.DONE
            }
            task.status = ClassificationTaskStatus.DONE
            classificationSubtaskRepository.saveAll(subtasks)
            classificationTaskRepository.save(task)
        }
    }
}