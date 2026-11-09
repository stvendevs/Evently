package com.example.evently.features.attendance.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evently.features.attendance.data.dummyCertificate

// ─── Colors ──────────────────────────────────────────────────────────────────

private val OrangeMain = Color(0xFFF97316)
private val GreenBadge = Color(0xFF16A34A)
private val GreenLight = Color(0xFFDCFCE7)
private val TextGray = Color(0xFF6B7280)
private val TextDark = Color(0xFF111827)
private val DividerColor = Color(0xFFE5E7EB)
private val CreamBg = Color(0xFFFEF9EE)
private val CreamBorder = Color(0xFFE8D5A3)
private val OrangeLightBg = Color(0xFFFFF7ED)

// ─── Main Screen ─────────────────────────────────────────────────────────────

@Composable
fun CertificateDetailScreen(
    onNavigateBack: () -> Unit = {}
) {
    val cert = dummyCertificate

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            EventlyBottomBar(
                selected = BottomNavDestination.TICKET,
                onItemSelected = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // ── Top Bar
            CertDetailTopBar(onNavigateBack = onNavigateBack)

            // ── Status + Header
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Credential Ready pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(GreenLight)
                        .border(1.dp, GreenBadge.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(GreenBadge)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("CREDENTIAL READY", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GreenBadge, letterSpacing = 0.5.sp)
                }

                Text(cert.verifiedYear, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextGray)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "National Campus Leadership\nSummit 2026",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = TextGray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("October 16, 2026", fontSize = 13.sp, color = TextGray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.LocationOn, contentDescription = null, tint = TextGray, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Auditorium Algoritma FILKOM, Brawijaya University", fontSize = 13.sp, color = TextGray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Certificate Preview Card
            CertificatePreviewCard(cert = cert)

            Spacer(modifier = Modifier.height(20.dp))

            // ── Credential Details Section
            Text(
                "Credential Details",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(modifier = Modifier.height(10.dp))

            CredentialDetailsCard(cert = cert)

            Spacer(modifier = Modifier.height(20.dp))

            // ── Action Buttons
            ActionButtons()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@Composable
private fun CertDetailTopBar(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .border(1.dp, DividerColor, CircleShape)
                .clickable { onNavigateBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark, modifier = Modifier.size(20.dp))
        }
        Text(
            "Event Details",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Filled.Bookmark, contentDescription = "Bookmark", tint = TextGray, modifier = Modifier.size(22.dp))
            Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = TextGray, modifier = Modifier.size(22.dp))
        }
    }
}

// ─── Certificate Preview Card ─────────────────────────────────────────────────

@Composable
private fun CertificatePreviewCard(cert: com.example.evently.features.attendance.data.CertificateRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CreamBg),
        border = CardDefaults.outlinedCardBorder().copy(width = 1.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(OrangeLightBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.School, contentDescription = null, tint = OrangeMain, modifier = Modifier.size(20.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("OFFICIAL CAMPUS CREDENTIAL", fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, color = OrangeMain, letterSpacing = 0.5.sp)
                        Text("Evently Academic Ledger", fontSize = 10.sp, color = TextGray)
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(cert.issueDate, fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(GreenBadge)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("ISSUED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GreenBadge)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Certificate title badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(CreamBorder.copy(alpha = 0.4f))
                    .border(1.dp, CreamBorder, RoundedCornerShape(8.dp))
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "CERTIFICATE OF ATTENDANCE\n& LEADERSHIP EXCELLENCE",
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF78350F),
                    letterSpacing = 0.5.sp,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("This acknowledges that", fontSize = 12.sp, color = TextGray, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.height(6.dp))
            Text(cert.recipientName, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
            Text("NIM: ${cert.nim}", fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(top = 2.dp))

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "has actively participated in and successfully\ncompleted the university leadership curriculum\nfor the National Campus Leadership Summit\n2025.",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color(0xFF374151),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = CreamBorder)
            Spacer(modifier = Modifier.height(14.dp))

            // Signatures
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Dean signature
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("E. Rostova", fontSize = 14.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = TextDark)
                    Spacer(modifier = Modifier.height(2.dp))
                    HorizontalDivider(modifier = Modifier.width(100.dp), color = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(cert.deanName, fontSize = 10.sp, color = TextGray)
                    Text(cert.deanTitle, fontSize = 9.sp, color = TextGray)
                }
                // Council President signature
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Marcus Vance", fontSize = 14.sp, fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic, color = TextDark)
                    Spacer(modifier = Modifier.height(2.dp))
                    HorizontalDivider(modifier = Modifier.width(100.dp), color = Color(0xFF9CA3AF))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(cert.councilPresidentName, fontSize = 10.sp, color = TextGray)
                    Text(cert.councilPresidentTitle, fontSize = 9.sp, color = TextGray)
                }
            }
        }
    }
}

// ─── Credential Details Card ──────────────────────────────────────────────────

@Composable
private fun CredentialDetailsCard(cert: com.example.evently.features.attendance.data.CertificateRecord) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            // Row 1: Event Name
            CredentialDetailRow(
                label = "Event Name",
                value = cert.eventName,
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF3F4F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = TextGray, modifier = Modifier.size(16.dp))
                    }
                },
                trailingContent = {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = GreenBadge, modifier = Modifier.size(20.dp))
                }
            )
            HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 12.dp))

            // Row 2: Attendance Log
            CredentialDetailRow(
                label = "Attendance Log",
                value = cert.attendanceLog,
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF3F4F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Timer, contentDescription = null, tint = TextGray, modifier = Modifier.size(16.dp))
                    }
                },
                trailingContent = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .border(1.dp, GreenBadge, RoundedCornerShape(50.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Valid SKP", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GreenBadge)
                    }
                }
            )
            HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 12.dp))

            // Row 3: Certificate ID
            CredentialDetailRow(
                label = "Certificate ID",
                value = cert.certificateId,
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF3F4F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.Badge, contentDescription = null, tint = TextGray, modifier = Modifier.size(16.dp))
                    }
                },
                trailingContent = {
                    OutlinedButton(
                        onClick = { },
                        shape = RoundedCornerShape(50.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, OrangeMain),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Icon(Icons.Filled.ContentCopy, contentDescription = null, tint = OrangeMain, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copy", fontSize = 11.sp, color = OrangeMain, fontWeight = FontWeight.SemiBold)
                    }
                }
            )
            HorizontalDivider(color = DividerColor, modifier = Modifier.padding(horizontal = 12.dp))

            // Row 4: Verification Status
            CredentialDetailRow(
                label = "Verification Status",
                value = cert.verificationStatus,
                valueColor = GreenBadge,
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF3F4F6)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Shield, contentDescription = null, tint = TextGray, modifier = Modifier.size(16.dp))
                    }
                },
                trailingContent = {
                    Icon(Icons.Filled.Verified, contentDescription = null, tint = GreenBadge, modifier = Modifier.size(22.dp))
                }
            )
        }
    }
}

@Composable
private fun CredentialDetailRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF111827),
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent()
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 11.sp, color = TextGray)
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = valueColor)
        }
        Spacer(modifier = Modifier.width(8.dp))
        trailingContent()
    }
}

// ─── Action Buttons ───────────────────────────────────────────────────────────

@Composable
private fun ActionButtons() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Download Certificate
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OrangeMain)
        ) {
            Icon(Icons.Filled.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Download Certificate", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Share + LinkedIn row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
            ) {
                Text("🔗  Share", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            }
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, DividerColor)
            ) {
                Text("in  Add to LinkedIn", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
            }
        }
    }
}

// ─── Preview ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun CertificateDetailScreenPreview() {
    MaterialTheme {
        CertificateDetailScreen()
    }
}

