package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.ui.components.GameMatchTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            GameMatchTopBar(
                title = "Tentang Aplikasi",
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                onProfilClick = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.gamelogo),
                contentDescription = "Logo GameMatch+",
                modifier = Modifier.size(120.dp)
            )


            Text(
                text = "GameMatch+",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6015EB)
            )

            Text(
                text = "Versi 1.0.0",
                fontSize = 12.sp,
                color = Color.Gray
            )

            HorizontalDivider()


            Text(
                text = "Tentang Aplikasi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "GameMatch+ adalah aplikasi mobile berbasis Android yang berfungsi sebagai platform sosial bagi para gamer untuk menemukan, menyimpan, dan membagikan pengalaman bermain game. Aplikasi ini membantu pengguna menemukan rekomendasi game berdasarkan preferensi mereka, sekaligus memungkinkan interaksi dengan komunitas gamer lainnya.",
                fontSize = 14.sp,
                textAlign = TextAlign.Justify,
                color = Color.DarkGray
            )

            HorizontalDivider()


            Text(
                text = "Fitur Utama",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            val features = listOf(
                "🎮  Rekomendasi game berdasarkan genre, mood, platform, rating, dan mode",
                "🔥  Trending games yang sedang populer",
                "📰  Feed komunitas gamer",
                "📸  Upload screenshot gameplay",
                "✍️  Review dan rating game",
                "❤️  Wishlist game favorit",
                "💬  Komentar pada postingan",
                "👤  Profil dan riwayat aktivitas"
            )

            features.forEach { feature ->
                Text(
                    text = feature,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.DarkGray
                )
            }

            HorizontalDivider()


            Text(
                text = "Teknologi yang Digunakan",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            val techs = listOf(
                "⚡  Jetpack Compose — UI modern Android",
                "🗄️  Room Database — penyimpanan data lokal",
                "☁️  Supabase — backend Feed & Postingan",
                "🌐  Retrofit + Moshi — koneksi API",
                "🖼️  Coil — loading gambar dari internet",
                "🔐  Google Sign-In via Credential Manager",
                "💾  DataStore — penyimpanan sesi pengguna"
            )

            techs.forEach { tech ->
                Text(
                    text = tech,
                    fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.DarkGray
                )
            }

            HorizontalDivider()


            Text(
                text = "Developer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Nama  : Riza Fahmi Muharram\nNIM    : 607062400093\nKelas  : D3RPLA-48-01\nProdi  : D3 Rekayasa Perangkat Lunak Aplikasi\nFakultas : Fakultas Ilmu Terapan\nUniversitas Telkom",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "© 2026 GameMatch+. All rights reserved.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}