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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Colors ──────────────────────────────────────────────────────────────────

private val OrangeMain = Color(0xFFF97316)
private val GreenBadge = Color(0xFF16A34A)
private val GreenLight = Color(0xFFDCFCE7)
private val GreenSoft = Color(0xFFF0FDF4)
private val TextGray = Color(0xFF6B7280)
private val TextDark = Color(0xFF111827)
private val DividerColor = Color(0xFFE5E7EB)
private val CreamBg = Color(0xFFFEF9EE)
private val CreamBorder = Color(0xFFE8D5A3)
private val OrangeLightBg = Color(0xFFFFF7ED)

// ─── Main Screen ─────────────────────────────────────────────────────────────

@Composable
fun CheckInFormScreen(
    onNavigateBack: () -> Unit = {}
) {
    var eventCode by remember { mutableStateOf("CMP-9041") }
    var codeApplied by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color(0xFFF9FAFB),
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
            CheckInTopBar(onNavigateBack = onNavigateBack)

            // ── Session Validation Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("SESSION VALIDATION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextGray, letterSpacing = 1.sp)
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(GreenLight)
                        .border(1.dp, GreenBadge.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(GreenBadge)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("LIVE CHECK-IN NODE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GreenBadge, letterSpacing = 0.5.sp)
                }
            }

            // ── Info Banner
            CheckInInfoBanner()

            Spacer(modifier = Modifier.height(16.dp))

            // ── Event Code Form
            EventCodeForm(
                eventCode = eventCode,
                onCodeChange = { eventCode = it; codeApplied = false },
                onApply = { codeApplied = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Verified Event Card (shown when code is applied)
            if (codeApplied) {
                VerifiedEventCard(code = eventCode)
                Spacer(modifier = Modifier.height(14.dp))
                StudentProfileCard()
                Spacer(modifier = Modifier.height(16.dp))
            }

            // ── Submit Button
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeMain)
                ) {
                    Text("Submit Check-In  →", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Footer status text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Shield, contentDescription = null, tint = TextGray, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("CAMPUS REGISTRY SYNC", fontSize = 10.sp, color = TextGray, letterSpacing = 0.5.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("⚡", fontSize = 10.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("INSTANT CERTIFICATE ISSUANCE", fontSize = 10.sp, color = TextGray, letterSpacing = 0.5.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("⚡", fontSize = 10.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@Composable
private fun CheckInTopBar(onNavigateBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, DividerColor, CircleShape)
                .clickable { onNavigateBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextDark, modifier = Modifier.size(20.dp))
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(OrangeMain)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("EVENTLY CAMPUS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OrangeMain, letterSpacing = 0.5.sp)
            }
            Text("Claim Certificate & Check-In", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
        }

        // Notification with orange dot
        Box(modifier = Modifier.size(36.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, DividerColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Notifications, contentDescription = "Notifications", tint = TextGray, modifier = Modifier.size(18.dp))
            }
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(OrangeMain)
                    .align(Alignment.TopEnd)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Avatar "AM"
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(OrangeMain),
            contentAlignment = Alignment.Center
        ) {
            Text("AM", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

// ─── Info Banner ─────────────────────────────────────────────────────────────

@Composable
private fun CheckInInfoBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CreamBg)
            .border(1.dp, CreamBorder, RoundedCornerShape(14.dp))
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(OrangeLightBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Badge, contentDescription = null, tint = OrangeMain, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text("Claim Certificate & Check-In", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Verify attendance at campus seminars, workshops, or university events to instantly unlock your verifiable certificate & SKP points.",
                fontSize = 12.sp,
                color = TextGray,
                lineHeight = 18.sp
            )
        }
    }
}

// ─── Event Code Form ──────────────────────────────────────────────────────────

@Composable
private fun EventCodeForm(
    eventCode: String,
    onCodeChange: (String) -> Unit,
    onApply: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Label + Required badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "# Enter Campus Event Code",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextDark
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(OrangeLightBg)
                    .border(1.dp, OrangeMain.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text("REQUIRED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = OrangeMain, letterSpacing = 0.5.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input field with inline APPLY button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, DividerColor, RoundedCornerShape(12.dp))
                .padding(start = 14.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🎫", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            BasicEventCodeField(
                value = eventCode,
                onValueChange = onCodeChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onApply,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangeMain),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                modifier = Modifier.height(42.dp)
            ) {
                Text("APPLY ✓", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("ⓘ", fontSize = 12.sp, color = TextGray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                "6-8 character code announced by the lecturer or committee at the end of the session.",
                fontSize = 11.sp,
                color = TextGray
            )
        }
    }
}

@Composable
private fun BasicEventCodeField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
    )
}

// ─── Verified Event Card ──────────────────────────────────────────────────────

@Composable
private fun VerifiedEventCard(code: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Status row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(GreenBadge)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("EVENT VERIFIED", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GreenBadge, letterSpacing = 0.5.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFFF3F4F6))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(code, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextGray)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("National Campus Leadership Summit 2025", fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
            Text("Department of Student Affairs • Annual Session", fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(top = 2.dp))

            Spacer(modifier = Modifier.height(12.dp))

            // Info boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Location
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF9FAFB))
                        .border(1.dp, DividerColor, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = TextGray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("LOCATION", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextGray, letterSpacing = 0.5.sp)
                    }
                    Text("Graha Mahasiswa", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark, modifier = Modifier.padding(top = 4.dp))
                }
                // Schedule
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFF9FAFB))
                        .border(1.dp, DividerColor, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Schedule, contentDescription = null, tint = TextGray, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("SCHEDULE", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextGray, letterSpacing = 0.5.sp)
                    }
                    Text("Today, 14:00 - 17:00", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextDark, modifier = Modifier.padding(top = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // SKP Points bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GreenSoft)
                    .border(1.dp, GreenLight, RoundedCornerShape(10.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🏅", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Eligible SKP Points", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = GreenBadge)
                }
                Text("+8 Credit Hours", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = GreenBadge)
            }
        }
    }
}

// ─── Student Profile Card ─────────────────────────────────────────────────────

@Composable
private fun StudentProfileCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar with green online dot
            Box(modifier = Modifier.size(50.dp)) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(OrangeMain),
                    contentAlignment = Alignment.Center
                ) {
                    Text("AM", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(GreenBadge)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Alex Morgan", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(modifier = Modifier.width(5.dp))
                    Icon(Icons.Filled.Verified, contentDescription = null, tint = Color(0xFF3B82F6), modifier = Modifier.size(16.dp))
                }
                Text("Faculty of Computer Science", fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(top = 2.dp))
            }

            // Verified pill
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(GreenLight)
                    .border(1.dp, GreenBadge.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(GreenBadge)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("VERIFIED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GreenBadge, letterSpacing = 0.5.sp)
            }
        }
    }
}

// ─── Preview ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun CheckInFormScreenPreview() {
    MaterialTheme {
        CheckInFormScreen()
    }
}
