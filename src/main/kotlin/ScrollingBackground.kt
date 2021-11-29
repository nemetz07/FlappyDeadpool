import com.almasb.fxgl.texture.ScrollingView
import javafx.geometry.Orientation
import javafx.scene.image.Image

class ScrollingBackground(image: Image, viewWidth: Double, viewHeight: Double, orientation: Orientation) :
    ScrollingView(image, viewWidth, viewHeight, orientation) {
    override fun onUpdate(tpf: Double) {
        scrollX += 1.0
    }
}