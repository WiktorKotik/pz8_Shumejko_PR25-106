package com.example.pz8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pz8.ui.theme.Pz8Theme
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

/**
 * Краткое описание: главный экран приложения ПЗ.8.
 * На одном экране размещены часы в реальном времени (чч:мм:сс)
 * и текущая дата (день, месяц текстом полностью и четырёхзначный год).
 *
 * Дата создания: 26-11-2026
 * Автор создания: участник 1
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pz8Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClockScreen()
                }
            }
        }
    }
}

/** Формат времени: два знака на часы, минуты и секунды с разделителями. */
private val TIME_FORMAT: DateTimeFormatter =
    DateTimeFormatter.ofPattern("HH:mm:ss")

/** Экран с часами и датой. */
@Composable
fun ClockScreen(modifier: Modifier = Modifier) {
    // Текущее время: обновляется раз в секунду
    var time by remember { mutableStateOf(LocalTime.now()) }
    val date = remember { LocalDate.now() }

    LaunchedEffect(Unit) {
        while (true) {
            time = LocalTime.now()
            delay(1000)
        }
    }

    val monthName = date.month.getDisplayName(TextStyle.FULL, Locale("ru"))

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = time.format(TIME_FORMAT),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Текущая дата",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(top = 32.dp)
        )
        Text(
            text = "%02d %s %04d".format(date.dayOfMonth, monthName, date.year),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}
