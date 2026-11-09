package com.example.evently.features.attendance.data

// ─── Enum ────────────────────────────────────────────────────────────────────

enum class AttendanceCategory { CAMPUS, SEMINAR, FESTIVAL }
enum class EventCategory { LEADERSHIP, ACADEMIC, UKM_ARTS }
enum class AttendanceStatus { ATTENDED }
enum class CertificateStatus { READY_TO_CLAIM, ISSUED }

// ─── Data Model ──────────────────────────────────────────────────────────────

data class AttendanceRecord(
    val id: String,
    val eventName: String,
    val organizerLabel: String,       // e.g. "STUDENT SENATE", "ACADEMY", "UKM ARTS"
    val attendanceCategory: AttendanceCategory,
    val eventCategory: EventCategory,
    val status: AttendanceStatus,
    val date: String,
    val location: String,
    val certificateStatus: CertificateStatus?,
    val codeOrSeat: String,           // e.g. "#NCLS-2025", "READY TO CLAIM", "SEAT A-14 • LVL 2"
    val hasCertificateReadyPill: Boolean = false,
    val imageDescription: String = ""
)

data class CertificateRecord(
    val id: String,
    val eventName: String,
    val certificateId: String,
    val recipientName: String,
    val nim: String,
    val issueDate: String,
    val verifiedYear: String,
    val attendanceLog: String,
    val verificationStatus: String,
    val deanName: String,
    val deanTitle: String,
    val councilPresidentName: String,
    val councilPresidentTitle: String
)

// ─── Dummy Data ───────────────────────────────────────────────────────────────

val dummyAttendanceList = listOf(
    AttendanceRecord(
        id = "1",
        eventName = "National Campus Leadership...",
        organizerLabel = "STUDENT SENATE",
        attendanceCategory = AttendanceCategory.CAMPUS,
        eventCategory = EventCategory.LEADERSHIP,
        status = AttendanceStatus.ATTENDED,
        date = "Oct 12, 2025",
        location = "Graha Hall, Campus A",
        certificateStatus = null,
        codeOrSeat = "#NCLS-2025",
        hasCertificateReadyPill = false,
        imageDescription = "Campus auditorium stage"
    ),
    AttendanceRecord(
        id = "2",
        eventName = "Academic Writing & Research",
        organizerLabel = "ACADEMY",
        attendanceCategory = AttendanceCategory.SEMINAR,
        eventCategory = EventCategory.ACADEMIC,
        status = AttendanceStatus.ATTENDED,
        date = "Nov 04, 2025",
        location = "Central University Library",
        certificateStatus = CertificateStatus.READY_TO_CLAIM,
        codeOrSeat = "READY TO CLAIM",
        hasCertificateReadyPill = true,
        imageDescription = "Research seminar session"
    ),
    AttendanceRecord(
        id = "3",
        eventName = "Artropolis 2026",
        organizerLabel = "UKM ARTS",
        attendanceCategory = AttendanceCategory.FESTIVAL,
        eventCategory = EventCategory.UKM_ARTS,
        status = AttendanceStatus.ATTENDED,
        date = "Jan 15, 2026",
        location = "Open Amphitheater",
        certificateStatus = CertificateStatus.ISSUED,
        codeOrSeat = "SEAT A-14 • LVL 2",
        hasCertificateReadyPill = false,
        imageDescription = "Arts festival stage"
    )
)

val dummyCertificate = CertificateRecord(
    id = "1",
    eventName = "National Campus Leadership Summit 2025",
    certificateId = "CMP-2025-98421-LEAD",
    recipientName = "Alex Morgan",
    nim = "210852104",
    issueDate = "OCT 14, 2026",
    verifiedYear = "VERIFIED 2026",
    attendanceLog = "October 12, 2025 (8 Credit Hours / SKP)",
    verificationStatus = "University Registry Verified",
    deanName = "Prof. Dr. Elena Rostova",
    deanTitle = "Dean of Student Affairs",
    councilPresidentName = "Marcus Vance",
    councilPresidentTitle = "Student Council President"
)
