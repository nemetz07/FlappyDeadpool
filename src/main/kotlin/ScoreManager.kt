import com.almasb.fxgl.dsl.getSettings
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.json.JSONArray
import java.io.File
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


class ScoreManager(private var fileName: String) {
    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    var scores: ObservableList<Score> = try {
        val jsonArray = JSONArray(File(fileName).readText())
        FXCollections.observableArrayList(jsonArray.toList()
            .asSequence()
            .map(HashMap::class.java::cast)
            .map {
                Score(
                    name = it["name"].toString(),
                    difficulty = it["difficulty"].toString(),
                    score = Integer.parseInt(it["score"].toString()),
                    date = it["date"].toString()
                )
            }.toList()
        )
    } catch (e: Exception) {
        FXCollections.observableArrayList()
    }

    fun addScore(name: String, score: Int) {
        scores.add(
            Score(
                name = name,
                difficulty = getSettings().gameDifficulty.toString(),
                score = score,
                date = dateFormat.format(Date())
            )
        )
    }

    @Throws(IOException::class)
    fun saveToFile() {
        File(fileName).writeText(JSONArray(scores).toString(1))
    }
}