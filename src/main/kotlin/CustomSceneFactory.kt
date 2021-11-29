import com.almasb.fxgl.app.scene.FXGLMenu
import com.almasb.fxgl.app.scene.MenuType
import com.almasb.fxgl.app.scene.SceneFactory


class CustomSceneFactory: SceneFactory() {

    override fun newMainMenu(): FXGLMenu {
        return Menu(MenuType.MAIN_MENU)
    }

    override fun newGameMenu(): FXGLMenu {
        return Menu(MenuType.GAME_MENU)
    }

}