package com.example.evently.features.attendance.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evently.features.attendance.data.*

// ─── Color palette ───────────────────────────────────────────────────────────

private val OrangeMain = Color(0xFFF97316)
private val GreenBadge = Color(0xFF16A34A)
private val GreenLight = Color(0xFFDCFCE7)
private val GreenSoft = Color(0xFFF0FDF4)
private val YellowPill = Color(0xFFFEF9C3)
private val YellowText = Color(0xFF854D0E)
private val CardBg = Color(0xFFFAFAFA)
private val DividerColor = Color(0xFFE5E7EB)
private val TextGray = Color(0xFF6B7280)
private val TextDark = Color(0xFF111827)
private val OrangeLightBg = Color(0xFFFFF7ED)

// ─── Main Screen ─────────────────────────────────────────────────────────────

@Composable
fun AttendanceListScreen(
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToForm: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(0) }   // 0 = Attendance, 1 = Certificates
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All Events") }

    val filterChips = listOf("All Events", "Leadership", "Academic", "Competition")

    val filteredList = dummyAttendanceList.filter { record ->
        val matchesSearch = searchQuery.isEmpty() ||
            record.eventName.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Leadership" -> record.eventCategory == EventCategory.LEADERSHIP
            "Academic"   -> record.eventCategory == EventCategory.ACADEMIC
            "Competition"-> false // no competition items in dummy
            else         -> true
        }
        matchesSearch && matchesFilter
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            EventlyBottomBar(
                selected = BottomNavDestination.EXPLORE,
                onItemSelected = {}
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // ── Top Bar
            item {
                AttendanceTopBar()
            }

            // ── Header + Claim Button
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "My Events & Credentials",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextDark
                        )
                        Text(
                            "Track your verified student attendance",
                            fontSize = 13.sp,
                            color = TextGray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(OrangeLightBg)
                            .clickable { onNavigateToForm() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Badge, contentDescription = "Claim", tint = OrangeMain, modifier = Modifier.size(22.dp))
                    }
                }
            }

            // ── Segmented Tab
            item {
                AttendanceTabSwitcher(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // ── Search + Filter
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                "Search events, seminars, or credentials...",
                                fontSize = 12.sp,
                                color = Color(0xFF9CA3AF)
                            )
                        },
                        leadingIcon = {
                            Icon(Icons.Filled.Search, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(18.dp))
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = OrangeMain,
                            unfocusedBorderColor = DividerColor,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 13.sp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, DividerColor, RoundedCornerShape(12.dp))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.FilterList, contentDescription = "Filter", tint = TextGray)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // ── Filter Chips
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    filterChips.forEach { chip ->
                        val isActive = chip == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(if (isActive) TextDark else Color.White)
                                .border(1.dp, if (isActive) TextDark else DividerColor, RoundedCornerShape(50.dp))
                                .clickable { selectedFilter = chip }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                chip,
                                fontSize = 13.sp,
                                fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isActive) Color.White else TextDark
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // ── Summary Banner
            item {
                AttendanceSummaryBanner()
                Spacer(modifier = Modifier.height(14.dp))
            }

            // ── Event Cards
            if (selectedTab == 0) {
                items(filteredList) { record ->
                    AttendanceCard(
                        record = record,
                        onViewDetail = { onNavigateToDetail(record.id) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("3 Certificates Available", color = TextGray, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

// ─── Top Bar ─────────────────────────────────────────────────────────────────

@Composable
private fun AttendanceTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo + Brand
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Icon(
                Icons.Filled.PinDrop,
                contentDescription = null,
                tint = OrangeMain,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text("EVENTLY", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OrangeMain, letterSpacing = 1.sp)
                Text("Certificates & Attendance", fontSize = 11.sp, color = TextGray)
            }
        }

        // Notification icon with orange dot
        Box(modifier = Modifier.size(36.dp)) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.dp, DividerColor, CircleShape)
                    .clickable { },
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
        // Avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(OrangeMain),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.size(20.dp))
        }
    }
}

// ─── Tab Switcher ────────────────────────────────────────────────────────────

@Composable
private fun AttendanceTabSwitcher(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF3F4F6))
            .padding(4.dp)
    ) {
        // Tab: Attendance History
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selectedTab == 0) OrangeMain else Color.Transparent)
                .clickable { onTabSelected(0) }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(
                    "Attendance History",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selectedTab == 0) Color.White else TextGray
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (selectedTab == 0) Color.White.copy(alpha = 0.3f) else Color(0xFFE5E7EB))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text("4", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (selectedTab == 0) Color.White else TextGray)
                }
            }
        }

        // Tab: Certificates
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(if (selectedTab == 1) OrangeMain else Color.Transparent)
                .clickable { onTabSelected(1) }
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text(
                    "Certificates",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (selectedTab == 1) Color.White else TextGray
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (selectedTab == 1) Color.White.copy(alpha = 0.3f) else Color(0xFFE5E7EB))
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                ) {
                    Text("3", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = if (selectedTab == 1) Color.White else TextGray)
                }
            }
        }
    }
}

// ─── Summary Banner ──────────────────────────────────────────────────────────

@Composable
private fun AttendanceSummaryBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(GreenSoft)
            .border(1.dp, GreenLight, RoundedCornerShape(14.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(GreenLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.Shield, contentDescription = null, tint = GreenBadge, modifier = Modifier.size(22.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("STUDENT ATTENDANCE: 100%", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = GreenBadge, letterSpacing = 0.5.sp)
            Text("4 of 4 campus certificates earned & verified", fontSize = 12.sp, color = Color(0xFF166534))
        }
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(GreenLight)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.IosShare, contentDescription = "Share", tint = GreenBadge, modifier = Modifier.size(18.dp))
        }
    }
}

// ─── Event Card ──────────────────────────────────────────────────────────────

@Composable
private fun AttendanceCard(record: AttendanceRecord, onViewDetail: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Thumbnail + meta
            Row(modifier = Modifier.fillMaxWidth()) {
                // Image placeholder with category label
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFF1F2937))
                ) {
                    Text(
                        record.attendanceCategory.name,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            record.organizerLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OrangeMain,
                            letterSpacing = 0.5.sp
                        )
                        // Attended badge
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(GreenLight)
                                .padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Verified, contentDescription = null, tint = GreenBadge, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text("Attended", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = GreenBadge)
                        }
                    }
                    Text(
                        record.eventName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        val dateIcon = when (record.attendanceCategory) {
                            AttendanceCategory.CAMPUS   -> "📅"
                            AttendanceCategory.SEMINAR  -> "📍"
                            AttendanceCategory.FESTIVAL -> "📅"
                        }
                        Text(dateIcon, fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${record.date} • ${record.location}",
                            fontSize = 11.sp,
                            color = TextGray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (record.hasCertificateReadyPill) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50.dp))
                                .background(YellowPill)
                                .border(1.dp, Color(0xFFFDE047), RoundedCornerShape(50.dp))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text("🏅 Certificate Ready", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = YellowText)
                        }
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = DividerColor)

            // Bottom row: code/seat + View Detail button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🎫", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        record.codeOrSeat,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextGray
                    )
                }
                OutlinedButton(
                    onClick = onViewDetail,
                    shape = RoundedCornerShape(50.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, OrangeMain),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("View Detail →", fontSize = 12.sp, color = OrangeMain, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

// ─── Preview ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun AttendanceListScreenPreview() {
    MaterialTheme {
        AttendanceListScreen()
    }
}

