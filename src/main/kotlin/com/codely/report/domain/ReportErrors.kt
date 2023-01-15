package com.codely.report.domain

sealed class ScheduleReportError {
    class Unknown(val cause: Throwable) : ScheduleReportError()
}

sealed class DeleteReportScheduleError {
    class Unknown(val cause: Throwable) : DeleteReportScheduleError()
}

sealed class CreateReportError {
    object ReportAlreadyExists : CreateReportError()
    class Unknown(val cause: Throwable) : CreateReportError()
}
