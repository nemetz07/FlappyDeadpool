import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.dsl.components.DraggableComponent
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent
import com.almasb.fxgl.dsl.components.ProjectileComponent
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.input.UserAction
import com.almasb.fxgl.ui.FontType
import javafx.geometry.Orientation
import javafx.geometry.Point2D
import javafx.geometry.Rectangle2D
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.text.Text

class FlappyGame : GameApplication() {

    private lateinit var player: Entity
    private val pipes = arrayListOf<Entity>()
    lateinit var scoreManager: ScoreManager
    private lateinit var background: Entity
    private var pipeFrames = 76
    private var lastPipe = 0

    override fun onPreInit() {
        scoreManager = ScoreManager("scores.json")
        super.onPreInit()
    }

    override fun initGame() {
        super.initGame()

        background = FXGL.entityBuilder()
            .view(ScrollingBackground(FXGL.image("background.png"), 3840.0, 800.0, Orientation.HORIZONTAL))
            .zIndex(-1)
            .buildAndAttach()

        val scroller = FXGL.entityBuilder()
            .with(ProjectileComponent(Point2D(1.0, 0.0), 1.0))
            .buildAndAttach()

        player = FXGL.entityBuilder()
            .at(100.0, getAppHeight() / 2.0)
            .collidable()
            .with(Player())
            .with(DraggableComponent())
            .with(ProjectileComponent(Point2D(1.0, 0.0), 1.0))
            .buildAndAttach()

        FXGL.getGameScene().viewport.bindToEntity(scroller, 0.0, 0.0)
    }

    override fun initGameVars(vars: MutableMap<String, Any>) {
        vars["score"] = 0
    }

    override fun initUI() {
        val textScore: Text = getUIFactoryService().newText("", Color.WHITE, FontType.UI, 36.0).also {
            it.translateX = 10.0
            it.translateY = 35.0
            it.textProperty().bind(getWorldProperties().intProperty("score").asString())
        }
        getGameScene().addUINode(textScore)
    }

    override fun initSettings(settings: GameSettings) {
        with(settings) {
            width = 800
            height = 800
            title = "Flappy Deadpool"
            appIcon = "icon.png"
            version = "1.0"
            isMainMenuEnabled = true
            sceneFactory = CustomSceneFactory()
            isDeveloperMenuEnabled = true
//            applicationMode = ApplicationMode.RELEASE
//            isProfilingEnabled = true
        }
    }

    override fun initInput() {
        FXGL.getInput().addAction(object : UserAction("Jump") {
            override fun onActionBegin() {
                if (!player.getComponent(Player::class.java).isJumping) {
                    player.getComponent(Player::class.java).isJumping = true
                }
            }
        }, KeyCode.SPACE)
    }

    override fun onUpdate(tpf: Double) {
        if (pipeFrames > 75) {
            pipes.add(
                FXGL.entityBuilder()
                    .zIndex(100)
                    .with(Pipe())
                    .with(OffscreenCleanComponent())
                    .collidable()
                    .buildAndAttach()
            )
            pipeFrames = 0
        }
        pipeFrames++

        if (!player.isWithin(Rectangle2D(0.0, 0.0, getAppWidth().toDouble(), getAppHeight().toDouble()))) {
            gameOver()
        }

        getGameWorld().getEntitiesByComponent(Pipe::class.java).forEach {
            if ((it.position.x + PipeConstants.PIPE_WIDTH) <= player.x && lastPipe != it.hashCode()) {
                getWorldProperties().increment("score", 1)
                lastPipe = it.hashCode()
            }

            if (it.isColliding(player)) {
                gameOver()
            }
        }

        super.onUpdate(tpf)
    }

    private fun gameOver() {
        getDialogService().showInputBox("Name:") { name ->
            scoreManager.addScore(name, getWorldProperties().getInt("score"))
            scoreManager.saveToFile()
            FXGL.getGameController().gotoMainMenu()
        }
    }
}