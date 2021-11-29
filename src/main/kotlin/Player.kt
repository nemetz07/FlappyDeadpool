import com.almasb.fxgl.dsl.FXGL
import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.physics.BoundingShape
import com.almasb.fxgl.physics.HitBox
import com.almasb.fxgl.texture.AnimatedTexture
import com.almasb.fxgl.texture.AnimationChannel
import javafx.geometry.Point2D
import javafx.util.Duration

class Player : Component() {
    var isJumping = false
    private var speedY = 1.0
    private var jumpFrame = 0
    private val texture: AnimatedTexture
    private val animIdle: AnimationChannel = AnimationChannel(
        FXGL.image("deadpool.png"),
        4,
        78,
        66,
        Duration.seconds(1.0),
        1,
        1
    )
    private val animWalk: AnimationChannel = AnimationChannel(
        FXGL.image("deadpool.png"),
        4,
        78,
        66,
        Duration.seconds(1.0),
        0,
        3
    )

    override fun onAdded() {
        entity.transformComponent.scaleOrigin = Point2D(39.0, 33.0)
        entity.viewComponent.addChild(texture)
        entity.boundingBoxComponent.addHitBox(HitBox(Point2D(22.0, 5.0), BoundingShape.box(38.0, 56.0)))
        entity.setRotationOrigin(Point2D(39.0, 33.0))
    }

    override fun onUpdate(tpf: Double) {
        if (entity.position.y > getAppHeight()) {
            entity.setPosition(entity.position.x, 0.0)
        }

        if (isJumping) {
            if (jumpFrame > 3) {
                isJumping = false
                jumpFrame = 0
            }
            speedY = -600.0
            jumpFrame++
            entity.rotation = -12.0
        } else {
            speedY += 18.0
            entity.rotateBy(0.3)
        }

        if (speedY > 0) {
            idleAnimation()
        } else {
            jumpAnimation()
        }

        entity.translateY(speedY * tpf)
    }

    private fun jumpAnimation() {
        if (texture.animationChannel != animWalk) {
            texture.loopAnimationChannel(animWalk)
        }
    }

    private fun idleAnimation() {
        if (texture.animationChannel != animIdle) {
            texture.loopAnimationChannel(animIdle)
        }
    }

    init {
        texture = AnimatedTexture(animIdle)
    }
}