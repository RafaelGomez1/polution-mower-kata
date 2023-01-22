package com.codely.report.application

import com.google.maps.model.LatLng
import java.util.UUID

sealed class ReportCommand

data class ScheduleReportCommand(val id: String) : ReportCommand()
data class DeleteReportScheduleCommand(val id: String) : ReportCommand()
data class CreateReportCommand(val id: UUID, val robotId: String, val location: LatLng?) : ReportCommand()
data class GenerateReportCommand(val robotId: String) : ReportCommand()
data class IncrementReportPollutionCommand(val robotId: String, val pollution: Int) : ReportCommand()
