package com.erikmobiledev.simplereminder.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00C853),       // Verde oscuro brillante
    secondary = Color(0xFF4CAF50),     // Verde más suave para acentos
    background = Color(0xFF121212),    // Fondo oscuro
    surface = Color(0xFF1E1E1E),       // Color de superficie oscuro
    onPrimary = Color.White,           // Blanco para elementos sobre el verde
    onSecondary = Color.White,         // Blanco para textos o iconos en secundario
    onBackground = Color(0xFFE0E0E0),  // Gris claro para textos
    onSurface = Color(0xFFE0E0E0)      // Gris claro para textos o íconos
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0089C8),       // Azul
    secondary = Color(0xFF669DBB),     // Azul más claro para acentos
    background = Color(0xFFFFFFFF),    // Fondo blanco
    surface = Color(0xFFF5F5F5),       // Superficie ligeramente grisácea
    onPrimary = Color.White,           // White para contraste en el azul
    onSecondary = Color.White,         // White para contraste en el secundario
    onBackground = Color(0xFF1C1B1F),  // Gris oscuro para texto
    onSurface = Color(0xFF1C1B1F)      // Gris oscuro para texto
)


@Composable
fun TaskappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}