import com.almasb.fxgl.core.math.FXGLMath
import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getAppWidth
import com.almasb.fxgl.dsl.getSettings
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.gameplay.GameDifficulty
import com.almasb.fxgl.physics.BoundingShape.Companion.box
import com.almasb.fxgl.physics.HitBox
import javafx.geometry.Point2D
import javafx.scene.paint.*
import javafx.scene.shape.Rectangle
import java.util.*
import kotlin.random.Random


class Pipe : Component() {

    private var gapHeight: Double
    private var gapStartY: Double

    init {
        val gameDifficulty = getSettings().gameDifficulty
        val gapHeightMin = when (gameDifficulty) {
            GameDifficulty.EASY -> {PipeConstants.GAP_HEIGHT_MIN_EASY}
            GameDifficulty.MEDIUM -> {PipeConstants.GAP_HEIGHT_MIN_MEDIUM}
            GameDifficulty.HARD -> {PipeConstants.GAP_HEIGHT_MIN_HARD}
            else -> {PipeConstants.GAP_HEIGHT_MIN_EASY}
        }

        val gapHeightMultiplier = when (gameDifficulty) {
            GameDifficulty.EASY -> {PipeConstants.GAP_HEIGHT_MULTIPLIER_EASY}
            GameDifficulty.MEDIUM -> {PipeConstants.GAP_HEIGHT_MULTIPLIER_MEDIUM}
            GameDifficulty.HARD -> {PipeConstants.GAP_HEIGHT_MULTIPLIER_HARD}
            else -> {PipeConstants.GAP_HEIGHT_MIN_EASY}
        }

        val gapStartYMultiplier = when (gameDifficulty) {
            GameDifficulty.EASY -> {PipeConstants.GAP_START_Y_MULTIPLIER_EASY}
            GameDifficulty.MEDIUM -> {PipeConstants.GAP_START_Y_MULTIPLIER_MEDIUM}
            GameDifficulty.HARD -> {PipeConstants.GAP_START_Y_MULTIPLIER_HARD}
            else -> {PipeConstants.GAP_HEIGHT_MIN_EASY}
        }

        gapHeight = gapHeightMin + FXGLMath.getRandom().nextDouble() * gapHeightMultiplier
        gapStartY = PipeConstants.GAP_START_Y_MIN + FXGLMath.getRandom().nextDouble() * gapStartYMultiplier
    }

    override fun onAdded() {
        entity.x = getAppWidth().toDouble()

        val pipeHeightTop = gapStartY
        val pipeHeightBottom = getAppHeight() - (gapStartY + gapHeight)

        val linearGradient: Paint = LinearGradient(
            0.0, 0.0, 1.0, 0.0,
            true,
            CycleMethod.REPEAT,
            listOf(
                Stop(0.0, Color.DARKRED),
                Stop(0.3, Color.RED),
                Stop(0.6, Color.RED),
                Stop(1.0, Color.DARKRED)
            )
        )

        val topRectangle = Rectangle(PipeConstants.PIPE_WIDTH, pipeHeightTop, linearGradient)
        val bottomRectangle = Rectangle(PipeConstants.PIPE_WIDTH, pipeHeightBottom, linearGradient)
        val topRectangleCap = Rectangle(PipeConstants.CAP_WIDTH, PipeConstants.CAP_HEIGHT, linearGradient)
        val bottomRectangleCap = Rectangle(PipeConstants.CAP_WIDTH, PipeConstants.CAP_HEIGHT, linearGradient)

        topRectangle.y = 0.0
        bottomRectangle.y = getAppHeight() - pipeHeightBottom
        topRectangleCap.y = gapStartY - PipeConstants.CAP_HEIGHT
        bottomRectangleCap.y = getAppHeight() - pipeHeightBottom
        topRectangleCap.x = (PipeConstants.CAP_WIDTH - PipeConstants.PIPE_WIDTH) / -2
        bottomRectangleCap.x = (PipeConstants.CAP_WIDTH - PipeConstants.PIPE_WIDTH) / -2

        topRectangle.stroke = Color.BLACK
        topRectangleCap.stroke = Color.BLACK
        bottomRectangle.stroke = Color.BLACK
        bottomRectangleCap.stroke = Color.BLACK

        entity.viewComponent.addChild(topRectangle)
        entity.viewComponent.addChild(bottomRectangle)
        entity.viewComponent.addChild(topRectangleCap)
        entity.viewComponent.addChild(bottomRectangleCap)

        entity.boundingBoxComponent.addHitBox(HitBox("TOP_PIPE", box(PipeConstants.PIPE_WIDTH, pipeHeightTop)))
        entity.boundingBoxComponent.addHitBox(
            HitBox(
                "BOTTOM_PIPE",
                Point2D(0.0, getAppHeight() - pipeHeightBottom),
                box(PipeConstants.PIPE_WIDTH, pipeHeightBottom)
            )
        )

        super.onAdded()
    }

    override fun onUpdate(tpf: Double) {
        entity.translateX(PipeConstants.SPEED)
        super.onUpdate(tpf)
    }
}

object PipeConstants {
    const val SPEED = -6.5
    const val PIPE_WIDTH = 120.0
    const val GAP_START_Y_MIN = 50.0
    const val GAP_HEIGHT_MIN_EASY = 275.0
    const val GAP_HEIGHT_MIN_MEDIUM = 225.0
    const val GAP_HEIGHT_MIN_HARD = 175.0
    const val GAP_HEIGHT_MULTIPLIER_EASY = 250.0
    const val GAP_HEIGHT_MULTIPLIER_MEDIUM = 200.0
    const val GAP_HEIGHT_MULTIPLIER_HARD = 150.0
    const val GAP_START_Y_MULTIPLIER_EASY = 200.0
    const val GAP_START_Y_MULTIPLIER_MEDIUM = 250.0
    const val GAP_START_Y_MULTIPLIER_HARD = 325.0
    const val CAP_HEIGHT = 50.0
    const val CAP_WIDTH = 150.0
}