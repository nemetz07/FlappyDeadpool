import com.almasb.fxgl.dsl.getSettings
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ScoreManager(private var fileName: String) {
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var scores: JSONArray

    init {
        try {
            scores = JSONArray(File(fileName).readText())
        } catch (e: Exception) {
            scores = JSONArray()
            println(this.fileName + " not found, creating empty!")
        }
    }

    fun addScore(name: String?, score: Int) {
        val newScore = JSONObject()
        newScore.put("name", name)
        newScore.put("score", score)
        newScore.put("difficulty", getSettings().gameDifficulty.toString())
        newScore.put("date", dateFormat.format(Date()))
        scores.put(newScore)
    }

    @Throws(IOException::class)
    fun saveToFile() {
        File(fileName).writeText(scores.toString(1))
    }
}