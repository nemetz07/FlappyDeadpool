import 'package:flame/components.dart';
import 'package:flame/geometry.dart';
import 'package:flutter_hazi/components/pipe_component.dart';
import 'package:flutter_hazi/game_runner.dart';
import 'package:flutter_hazi/provider/navigation_service.dart';
import 'package:flutter_hazi/provider/score_counter.dart';
import 'package:flutter_hazi/provider/settings_state.dart';
import 'package:provider/provider.dart';

class PlayerComponent extends SpriteAnimationComponent
    with HasGameRef<GameRunner>, HasHitboxes, Collidable {
  double speedY = 0;

  var score = 0;

  @override
  Future<void>? onLoad() async {
    await super.onLoad();

    var spriteAnimationData = SpriteAnimationData.sequenced(
      amount: 4,
      stepTime: 0.15,
      textureSize: Vector2(78, 66),
    );
    animation = await gameRef
        .loadSpriteAnimation('deadpool.png', spriteAnimationData)
        .then((value) {
      value.loop = false;
      value.setToLast();
      return value;
    });
    size = Vector2(78, 66) * 1.2;
    position = Vector2(150, 300);
    anchor = Anchor.center;
    var hitbox = HitboxRectangle(relation: Vector2(0.4, 0.85))
      ..relativeOffset = Vector2(0.1, 0);
    addHitbox(hitbox);
  }

  @override
  void onCollision(Set<Vector2> intersectionPoints, Collidable other) {
    if (other is ScoreHitbox) {
      score++;
      Provider.of<ScoreCounter>(
        NavigationService.navigatorKey.currentContext!,
        listen: false,
      ).increment();
      other.removeHitbox(other.hitboxes.first);
    }
    super.onCollision(intersectionPoints, other);
  }

  void jump() {
    speedY = -750;
    jumpAnimation();
  }

  void jumpAnimation() {
    animation!.reset();
  }

  @override
  void update(double dt) {
    super.update(dt);

    if (Provider.of<SettingsState>(
      NavigationService.navigatorKey.currentContext!,
      listen: false,
    ).isDebug) {
      if (position.y > gameRef.size.y) {
        position.y = 0;
      }
    }

    // if (speedY > 0) {
    //   angle += 0.008;
    // } else {
    //   angle = -0.2;
    // }

    speedY += 15;
    position.y += speedY * dt;
  }
}
