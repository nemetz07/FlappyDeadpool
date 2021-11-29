import com.almasb.fxgl.dsl.getSettings
import org.json.JSONArray
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


class ScoreManager(private var fileName: String) {
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val scores: JSONArray = try {
        JSONArray(File(fileName).readText())
    } catch (e: Exception) {
        JSONArray()
    }

    fun addScore(name: String?, score: Int) {
        scores.put(
            mapOf(
                "name" to name,
                "score" to score,
                "difficulty" to getSettings().gameDifficulty.toString(),
                "date" to dateFormat.format(Date()),
            )
        )
    }

    @Throws(IOException::class)
    fun saveToFile() {
        File(fileName).writeText(scores.toString(1))
    }
}