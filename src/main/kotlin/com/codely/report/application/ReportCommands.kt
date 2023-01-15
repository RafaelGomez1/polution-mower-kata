package com.codely.report.application

import com.google.maps.model.LatLng

sealed class ReportCommand

data class ScheduleReportCommand(val id: String) : ReportCommand()
data class DeleteReportScheduleCommand(val id: String) : ReportCommand()
data class CreateReportCommand(val id: String, val location: LatLng?) : ReportCommand()
