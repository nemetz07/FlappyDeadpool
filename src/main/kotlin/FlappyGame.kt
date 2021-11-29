import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.components.DraggableComponent
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent
import com.almasb.fxgl.dsl.components.ProjectileComponent
import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getDialogService
import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.input.UserAction
import javafx.geometry.Orientation
import javafx.geometry.Point2D
import javafx.scene.input.KeyCode

class FlappyGame : GameApplication() {

    private lateinit var player: Entity
    private val pipes = arrayListOf<Entity>()
    lateinit var scoreManager: ScoreManager
    private lateinit var background: Entity
    private var pipeFrames = 76
    private var currentScore = 0
    private var lastPipe = 0

    override fun onPreInit() {
        scoreManager = ScoreManager("scores.json")
        super.onPreInit()
    }

    override fun initGame() {
        super.initGame()

        currentScore = 0

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

        getGameWorld().getEntitiesByComponent(Pipe::class.java).forEach {
            if (it.position.x <= player.x && lastPipe != it.hashCode()){
                currentScore++
                lastPipe = it.hashCode()
            }

            if (it.isColliding(player)) {
                getDialogService().showInputBox("Name:") { name ->
                    scoreManager.addScore(name, currentScore)
                    scoreManager.saveToFile()
                    FXGL.getGameController().gotoMainMenu()
                }
            }
        }

        super.onUpdate(tpf)
    }
}