import com.almasb.fxgl.animation.Animation
import com.almasb.fxgl.animation.Interpolators
import com.almasb.fxgl.app.scene.FXGLMenu
import com.almasb.fxgl.app.scene.MenuType
import com.almasb.fxgl.core.math.FXGLMath
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.gameplay.GameDifficulty
import com.almasb.fxgl.input.Input
import com.almasb.fxgl.input.InputModifier
import com.almasb.fxgl.input.Trigger
import com.almasb.fxgl.input.UserAction
import com.almasb.fxgl.input.view.TriggerView
import com.almasb.fxgl.logging.Logger
import com.almasb.fxgl.particle.ParticleEmitters
import com.almasb.fxgl.particle.ParticleSystem
import com.almasb.fxgl.scene.SubScene
import javafx.animation.FadeTransition
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.effect.BlendMode
import javafx.scene.effect.GaussianBlur
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import javafx.scene.paint.*
import javafx.scene.shape.Polygon
import javafx.scene.shape.Rectangle
import javafx.util.Duration
import java.util.function.Supplier

class Menu(type: MenuType) : FXGLMenu(type) {
    companion object {
        private val log = Logger.get("FXGL.DefaultMenu")
    }

    private val particleSystem = ParticleSystem()

    private val titleColor = SimpleObjectProperty(Color.WHITE)
    private var t = 0.0

    private val menuRoot = Pane()
    private val menuContentRoot = Pane()

    private val EMPTY = MenuContent()

    private val pressAnyKeyState = PressAnyKeyState()

    private val menu: Node

    init {

        menu = if (type === MenuType.MAIN_MENU)
            createMenuBodyMainMenu()
        else
            createMenuBodyGameMenu()

        val menuX = 50.0
        val menuY = appHeight / 2.0 - menu.layoutHeight / 2

        menuRoot.translateX = menuX
        menuRoot.translateY = menuY

        menuContentRoot.translateX = appWidth - 500.0
        menuContentRoot.translateY = menuY

        menuRoot.children.addAll(menu)
        menuContentRoot.children.add(EMPTY)

        contentRoot.children.addAll(
            createBackground(getAppWidth().toDouble(), getAppHeight().toDouble()),
            createTitleView(getSettings().title),
            particleSystem.pane,
            menuRoot, menuContentRoot
        )
    }

    private val animations = arrayListOf<Animation<*>>()

    override fun onCreate() {
        animations.clear()

        val menuBox = menuRoot.children[0] as MenuBox

        menuBox.children.forEachIndexed { index, node ->

            node.translateX = -250.0

            val animation = FXGL.animationBuilder()
                .delay(Duration.seconds(index * 0.07))
                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                .duration(Duration.seconds(0.66))
                .translate(node)
                .from(Point2D(-250.0, 0.0))
                .to(Point2D(0.0, 0.0))
                .build()

            animations += animation

            animation.stop()
            animation.start()
        }
    }

    override fun onDestroy() {
        // the scene is no longer active so reset everything
        // so that next time scene is active everything is loaded properly
        switchMenuTo(menu)
        switchMenuContentTo(EMPTY)
    }

    override fun onUpdate(tpf: Double) {
        animations.forEach { it.onUpdate(tpf) }

        val frequency = 1.7

        t += tpf * frequency

        particleSystem.onUpdate(tpf)

        val color = Color.color(1.0, 1.0, 1.0, FXGLMath.noise1D(t))
        titleColor.set(color)
    }

    private fun createBackground(width: Double, height: Double): Node {
        val bg = Rectangle(width, height)
//        bg.fill = Color.rgb(10, 1, 1, if (type == MenuType.GAME_MENU) 0.5 else 1.0)
        bg.fill = Color.rgb(86, 1, 1)
        return bg
    }

    private fun createTitleView(title: String): Node {
        val text = getUIFactoryService().newText(title.substring(0, 1), 50.0)
        text.fill = null
        text.strokeProperty().bind(titleColor)
        text.strokeWidth = 1.5

        val text2 = getUIFactoryService().newText(title.substring(1, title.length), 50.0)
        text2.fill = null
        text2.stroke = titleColor.value
        text2.strokeWidth = 1.5

        val textWidth = text.layoutBounds.width + text2.layoutBounds.width

        val border = Rectangle(textWidth + 30, 65.0, null)
        border.stroke = Color.WHITE
        border.strokeWidth = 4.0
        border.arcWidth = 25.0
        border.arcHeight = 25.0

        val emitter = ParticleEmitters.newExplosionEmitter(50)
        emitter.blendMode = BlendMode.ADD
        emitter.setSourceImage(image("particles/trace_horizontal.png", 64.0, 64.0))
        emitter.maxEmissions = Integer.MAX_VALUE
        emitter.setSize(18.0, 22.0)
        emitter.numParticles = 2
        emitter.emissionRate = 0.2
        emitter.setVelocityFunction { i ->
            if (i % 2 == 0)
                Point2D(FXGL.random(-10.0, 0.0), 0.0)
            else
                Point2D(FXGL.random(0.0, 10.0), 0.0)
        }
        emitter.setExpireFunction { Duration.seconds(FXGL.random(4.0, 6.0)) }
        emitter.setScaleFunction { Point2D(-0.03, -0.03) }
        emitter.setSpawnPointFunction { Point2D.ZERO }
        emitter.setAccelerationFunction { Point2D(FXGL.random(-1.0, 1.0), FXGL.random(0.0, 0.0)) }

        val box = HBox(text, text2)
        box.alignment = Pos.CENTER

        val titleRoot = StackPane(border, box)
        titleRoot.translateX = appWidth / 2.0 - (textWidth + 30) / 2
        titleRoot.translateY = 50.0

        if (!FXGL.getSettings().isNative)
            particleSystem.addParticleEmitter(emitter, appWidth / 2.0 - 30, titleRoot.translateY + border.height - 16)

        return titleRoot
    }

    private fun createMenuBodyMainMenu(): MenuBox {
        log.debug("createMenuBodyMainMenu()")

        val box = MenuBox()

        MenuButton("menu.newGame").also {
            it.setOnAction { fireNewGame() }
            box.add(it)
        }

        MenuButton("menu.highscores").also {
            it.setOnAction {
                println((getApp() as FlappyGame).scoreManager.scores)
            }
            box.add(it)
        }

        MenuButton("menu.options").also {
            it.setChild(createOptionsMenu(it))
            box.add(it)
        }

        MenuButton("menu.exit").also {
            it.setOnAction { fireExit() }
            box.add(it)
        }

        return box
    }

    private fun createMenuBodyGameMenu(): MenuBox {
        log.debug("createMenuBodyGameMenu()")

        val box = MenuBox()

        MenuButton("menu.resume").also {
            it.setOnAction { fireResume() }
            box.add(it)
        }

        MenuButton("menu.exit").also {
            it.setOnAction { fireExitToMainMenu() }
            box.add(it)
        }

        return box
    }

    private fun createOptionsMenu(menuButton: MenuButton): MenuBox {
        log.debug("createOptionsMenu()")

        menuButton.setMenuContent({ createContentGameplay() })

        return MenuBox(menuButton)
    }

    private fun switchMenuTo(menu: Node) {
        val oldMenu = menuRoot.children[0]

        val ft = FadeTransition(Duration.seconds(0.33), oldMenu)
        ft.toValue = 0.0
        ft.setOnFinished {
            menu.opacity = 0.0
            menuRoot.children[0] = menu
            oldMenu.opacity = 1.0

            val ft2 = FadeTransition(Duration.seconds(0.33), menu)
            ft2.toValue = 1.0
            ft2.play()
        }
        ft.play()
    }

    private fun switchMenuContentTo(content: Node) {
        menuContentRoot.children[0] = content
    }

    private class MenuBox(vararg items: MenuButton) : VBox() {

        val layoutHeight: Double
            get() = (10 * children.size).toDouble()

        init {
            for (item in items) {
                add(item)
            }
        }

        fun add(item: MenuButton) {
            item.setParent(this)
            children.addAll(item)
        }
    }

    private inner class MenuButton(stringKey: String) : Pane() {
        private var parent: MenuBox? = null
        private var cachedContent: MenuContent? = null

        private val p = Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0)
        val btn: Button = getUIFactoryService().newButton(localizedStringProperty(stringKey))

        private var isAnimating = false

        init {
            btn.alignment = Pos.CENTER_LEFT
            btn.style = "-fx-background-color: transparent"

            p.isMouseTransparent = true

            val g = LinearGradient(
                0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
                Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)),
                Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
                Stop(1.0, Color.WHITE)
            )

            p.fillProperty().bind(
                Bindings.`when`(btn.pressedProperty()).then(Color.color(1.0, 0.8, 0.0, 0.75) as Paint).otherwise(g)
            )

            p.stroke = Color.color(0.1, 0.1, 0.1, 0.15)

            if (!getSettings().isNative) {
                p.effect = GaussianBlur()
            }

            p.visibleProperty().bind(btn.hoverProperty())

            children.addAll(btn, p)

            btn.focusedProperty().addListener { _, _, isFocused ->
                if (isFocused) {
                    val isOK = animations.none { it.isAnimating } && !isAnimating
                    if (isOK) {
                        isAnimating = true

                        FXGL.animationBuilder()
                            .onFinished { isAnimating = false }
                            .bobbleDown(this)
                            .buildAndPlay(this@Menu)
                    }
                }
            }
        }

        fun setOnAction(e: EventHandler<ActionEvent>) {
            btn.onAction = e
        }

        fun setParent(menu: MenuBox) {
            parent = menu
        }

        fun setMenuContent(contentSupplier: Supplier<MenuContent>, isCached: Boolean = true) {

            btn.addEventHandler(ActionEvent.ACTION) {
                if (cachedContent == null || !isCached)
                    cachedContent = contentSupplier.get()

                switchMenuContentTo(cachedContent!!)
            }
        }

        fun setChild(menu: MenuBox) {
            val back = MenuButton("menu.back")
            menu.children.add(0, back)

            back.addEventHandler(ActionEvent.ACTION) {
                switchMenuContentTo(VBox())
                switchMenuTo(this@MenuButton.parent!!)
            }

            btn.addEventHandler(ActionEvent.ACTION) { switchMenuTo(menu) }
        }
    }

    protected fun createContentGameplay(): MenuContent {
        log.debug("createContentGameplay()")

        val languageBox =
            getUIFactoryService().newChoiceBox(FXCollections.observableArrayList(getSettings().supportedLanguages))
        languageBox.value = getSettings().language.value
        getSettings().language.bindBidirectional(languageBox.valueProperty())

        val difficultyBox =
            getUIFactoryService().newChoiceBox(
                FXCollections.observableArrayList(
                    listOf(
                        GameDifficulty.EASY,
                        GameDifficulty.MEDIUM,
                        GameDifficulty.HARD
                    )
                )
            )
        difficultyBox.value = getSettings().gameDifficulty
        getSettings().gameDifficultyProperty().bindBidirectional(difficultyBox.valueProperty())

        return MenuContent(
            VBox(
                50.0,
                HBox(
                    25.0,
                    getUIFactoryService().newText(localizedStringProperty("menu.language").concat(":")),
                    languageBox
                ),
                HBox(
                    25.0,
                    getUIFactoryService().newText(localizedStringProperty("menu.difficulty").concat(":")),
                    difficultyBox
                )
            )
        )
    }

    private inner class PressAnyKeyState : SubScene() {

        var actionContext: UserAction? = null

        var isActive = false

        init {
            input.addEventFilter(KeyEvent.KEY_PRESSED, EventHandler { e ->
                if (Input.isIllegal(e.code))
                    return@EventHandler

                val rebound = getInput().rebind(actionContext!!, e.code, InputModifier.from(e))

                if (rebound) {
                    FXGL.getSceneService().popSubScene()
                    isActive = false
                }
            })

            input.addEventFilter(MouseEvent.MOUSE_PRESSED) { e ->
                val rebound = getInput().rebind(actionContext!!, e.button, InputModifier.from(e))

                if (rebound) {
                    FXGL.getSceneService().popSubScene()
                    isActive = false
                }
            }

            val rect = Rectangle(250.0, 100.0)
            rect.stroke = Color.color(0.85, 0.9, 0.9, 0.95)
            rect.strokeWidth = 10.0
            rect.arcWidth = 15.0
            rect.arcHeight = 15.0

            val text = getUIFactoryService().newText("", 24.0)
            text.textProperty().bind(localizedStringProperty("menu.pressAnyKey"))

            val pane = StackPane(rect, text)
            pane.translateX = getAppWidth() / 2.0 - 125
            pane.translateY = getAppHeight() / 2.0 - 50

            contentRoot.children.add(pane)
        }
    }

    class MenuContent(vararg items: Node) : VBox() {

        private var onOpen: Runnable? = null
        private var onClose: Runnable? = null

        var maxW = 0

        init {
            if (items.isNotEmpty()) {
                maxW = items[0].layoutBounds.width.toInt()

                for (n in items) {
                    val w = n.layoutBounds.width.toInt()
                    if (w > maxW)
                        maxW = w
                }

                for (item in items) {
                    children.addAll(item)
                }
            }

            sceneProperty().addListener { _, _, newScene ->
                if (newScene != null) {
                    onOpen()
                } else {
                    onClose()
                }
            }
        }

        private fun onOpen() {
            if (onOpen != null)
                onOpen!!.run()
        }

        private fun onClose() {
            if (onClose != null)
                onClose!!.run()
        }
    }
}