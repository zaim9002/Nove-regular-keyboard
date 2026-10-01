package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.KeyboardThemeEntity
import com.example.data.NovaDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PrebuiltTheme(
    val id: String,
    val name: String,
    val description: String,
    val primaryColorHex: String,
    val backgroundColorHex: String,
    val keyColorHex: String,
    val glowColorHex: String,
    val badge: String
)

data class KeyboardLayout(
    val languageName: String,
    val row1: List<String>,
    val row2: List<String>,
    val row3: List<String>
)

class KeyboardViewModel(application: Application) : AndroidViewModel(application) {
    private val database = NovaDatabase.getDatabase(application)
    private val themeDao = database.themeDao()

    val customThemes: Flow<List<KeyboardThemeEntity>> = themeDao.getAllThemes()

    val prebuiltThemes = listOf(
        PrebuiltTheme(
            id = "cyber_neon",
            name = "سايبر نيون",
            description = "إضاءة سيان وماجينتا مستقبلية مع مفاتيح داكنة",
            primaryColorHex = "#00F0FF",
            backgroundColorHex = "#090D16",
            keyColorHex = "#161B2E",
            glowColorHex = "#00F0FF",
            badge = "الأكثر طلباً"
        ),
        PrebuiltTheme(
            id = "sunset_glow",
            name = "توهج الغروب",
            description = "تدرج برتقالي ووردي دافئ وساحر",
            primaryColorHex = "#FF007F",
            backgroundColorHex = "#140A1A",
            keyColorHex = "#261530",
            glowColorHex = "#FF5500",
            badge = "رائج"
        ),
        PrebuiltTheme(
            id = "rgb_gaming",
            name = "ألعاب RGB",
            description = "ألوان نيون خضراء مضيئة مخصصة للاعبين",
            primaryColorHex = "#00FF66",
            backgroundColorHex = "#0A120A",
            keyColorHex = "#122614",
            glowColorHex = "#00FF66",
            badge = "احترافي"
        ),
        PrebuiltTheme(
            id = "rose_gold",
            name = "روز جولد",
            description = "تصميم أنيق وراقي بلون الذهب الوردي",
            primaryColorHex = "#FFB6C1",
            backgroundColorHex = "#1A1015",
            keyColorHex = "#301C25",
            glowColorHex = "#FFB6C1",
            badge = "أناقة"
        ),
        PrebuiltTheme(
            id = "midnight_emerald",
            name = "زمرد منتصف الليل",
            description = "زمردي داكن مع توهج أخضر فخم",
            primaryColorHex = "#00FFCC",
            backgroundColorHex = "#051610",
            keyColorHex = "#0D2B20",
            glowColorHex = "#00FFCC",
            badge = "فخم"
        ),
        PrebuiltTheme(
            id = "pastel_dream",
            name = "حلم الباستيل",
            description = "ألوان هادئة وناعمة ومريحة للعين",
            primaryColorHex = "#D8B4FE",
            backgroundColorHex = "#13101C",
            keyColorHex = "#231B33",
            glowColorHex = "#C084FC",
            badge = "هادئ"
        )
    )

    val keyboardLayouts = mapOf(
        "العربية (Arabic)" to KeyboardLayout(
            languageName = "العربية (Arabic)",
            row1 = listOf("ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "د"),
            row2 = listOf("ش", "س", "ي", "ب", "ل", "ا", "ت", "ن", "م", "ك", "ط"),
            row3 = listOf("ئ", "ء", "ؤ", "ر", "لا", "ى", "ة", "و", "ز", "ظ")
        ),
        "English (US)" to KeyboardLayout(
            languageName = "English (US)",
            row1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
            row2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
            row3 = listOf("z", "x", "c", "v", "b", "n", "m")
        ),
        "Français (French)" to KeyboardLayout(
            languageName = "Français (French)",
            row1 = listOf("a", "z", "e", "r", "t", "y", "u", "i", "o", "p"),
            row2 = listOf("q", "s", "d", "f", "g", "h", "j", "k", "l", "m"),
            row3 = listOf("w", "x", "c", "v", "b", "n", "é", "è", "à")
        ),
        "Español (Spanish)" to KeyboardLayout(
            languageName = "Español (Spanish)",
            row1 = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "ñ"),
            row2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
            row3 = listOf("z", "x", "c", "v", "b", "n", "m", "¿", "¡")
        ),
        "Deutsch (German)" to KeyboardLayout(
            languageName = "Deutsch (German)",
            row1 = listOf("q", "w", "e", "r", "t", "z", "u", "i", "o", "p", "ü"),
            row2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l", "ö", "ä"),
            row3 = listOf("y", "x", "c", "v", "b", "n", "m", "ß")
        ),
        "Türkçe (Turkish)" to KeyboardLayout(
            languageName = "Türkçe (Turkish)",
            row1 = listOf("q", "w", "e", "r", "t", "y", "u", "ı", "o", "p", "ğ", "ü"),
            row2 = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l", "ş", "i"),
            row3 = listOf("z", "x", "c", "v", "b", "n", "m", "ö", "ç")
        ),
        "Русский (Russian)" to KeyboardLayout(
            languageName = "Русский (Russian)",
            row1 = listOf("й", "ц", "у", "к", "е", "н", "г", "ш", "щ", "з", "х", "ъ"),
            row2 = listOf("ф", "ы", "в", "а", "п", "р", "о", "л", "д", "ж", "э"),
            row3 = listOf("я", "ч", "с", "м", "и", "т", "ь", "б", "ю")
        ),
        "فارسی (Persian)" to KeyboardLayout(
            languageName = "فارسی (Persian)",
            row1 = listOf("ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج", "چ"),
            row2 = listOf("ش", "س", "ی", "ب", "ل", "ا", "ت", "ن", "م", "ک", "گ", "پ"),
            row3 = listOf("ظ", "ط", "ز", "ر", "ذ", "د", "و", "ژ", "ئ")
        ),
        "اردو (Urdu)" to KeyboardLayout(
            languageName = "اردو (Urdu)",
            row1 = listOf("ق", "و", "ع", "ر", "ت", "ي", "ء", "و", "پ", "ل", "ا", "ھ"),
            row2 = listOf("ا", "س", "د", "ف", "گ", "ه", "ج", "ک", "ل"),
            row3 = listOf("ز", "خ", "ص", "ش", "ب", "ن", "م", "ط")
        ),
        "हिन्दी (Hindi)" to KeyboardLayout(
            languageName = "हिन्दी (Hindi)",
            row1 = listOf("ऐ", "औ", "आ", "ई", "ऊ", "ऐ", "औ", "अ", "क", "ख", "ग"),
            row2 = listOf("च", "छ", "ज", "झ", "ट", "ठ", "ड", "ढ", "ण", "त", "थ"),
            row3 = listOf("द", "ध", "न", "प", "फ", "ब", "भ", "म", "य", "र")
        )
    )

    val supportedLanguages = keyboardLayouts.keys.toList()

    private val _currentThemeId = MutableStateFlow("cyber_neon")
    val currentThemeId: StateFlow<String> = _currentThemeId.asStateFlow()

    private val _simulatedText = MutableStateFlow("مرحباً بك في كيبورد نوفا! 🚀 اختر لغة الإدخال وابدأ الكتابة...")
    val simulatedText: StateFlow<String> = _simulatedText.asStateFlow()

    private val _soundEnabled = MutableStateFlow(true)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _vibrationEnabled = MutableStateFlow(true)
    val vibrationEnabled: StateFlow<Boolean> = _vibrationEnabled.asStateFlow()

    private val _autoCorrect = MutableStateFlow(true)
    val autoCorrect: StateFlow<Boolean> = _autoCorrect.asStateFlow()

    private val _selectedLanguage = MutableStateFlow("العربية (Arabic)")
    val selectedLanguage: StateFlow<String> = _selectedLanguage.asStateFlow()

    val currentLayout: StateFlow<KeyboardLayout> = MutableStateFlow(
        keyboardLayouts["العربية (Arabic)"]!!
    )

    fun applyPrebuiltTheme(themeId: String) {
        _currentThemeId.update { themeId }
    }

    fun typeKey(char: String) {
        _simulatedText.update { it + char }
    }

    fun backspace() {
        _simulatedText.update { if (it.isNotEmpty()) it.dropLast(1) else it }
    }

    fun clearText() {
        _simulatedText.update { "" }
    }

    fun toggleSound(enabled: Boolean) {
        _soundEnabled.update { enabled }
    }

    fun toggleVibration(enabled: Boolean) {
        _vibrationEnabled.update { enabled }
    }

    fun toggleAutoCorrect(enabled: Boolean) {
        _autoCorrect.update { enabled }
    }

    fun setLanguage(lang: String) {
        _selectedLanguage.update { lang }
        keyboardLayouts[lang]?.let { layout ->
            (currentLayout as MutableStateFlow).update { layout }
        }
    }

    fun saveCustomTheme(name: String, primaryHex: String, bgHex: String, keyHex: String, glowHex: String) {
        viewModelScope.launch {
            themeDao.insertTheme(
                KeyboardThemeEntity(
                    name = name,
                    primaryColorHex = primaryHex,
                    backgroundColorHex = bgHex,
                    keyColorHex = keyHex,
                    glowColorHex = glowHex,
                    isApplied = true
                )
            )
        }
    }
}
