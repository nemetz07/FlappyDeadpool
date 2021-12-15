import 'package:flame/components.dart';
import 'package:flame/game.dart';
import 'package:flame/input.dart';
import 'package:flame/parallax.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_hazi/components/pipe_component.dart';
import 'package:flutter_hazi/components/player_component.dart';
import 'package:flutter_hazi/provider/navigation_service.dart';
import 'package:flutter_hazi/provider/settings_state.dart';
import 'package:provider/provider.dart';

class GameRunner extends FlameGame
    with TapDetector, HasCollidables, KeyboardEvents, FPSCounter {
  PlayerComponent player = PlayerComponent();

  int pipeFrames = 125;

  @override
  Future<void> onLoad() async {
    debugMode = Provider.of<SettingsState>(
      NavigationService.navigatorKey.currentContext!,
      listen: false,
    ).isDebug;
    final parallax = await loadParallaxComponent(
      [ParallaxImageData("background.png")],
      repeat: ImageRepeat.repeatX,
      baseVelocity: Vector2(75, 0),
    );
    add(parallax);
    add(player);
    super.onLoad();
  }

  @override
  void update(double dt) {
    super.update(dt);

    pipeFrames++;
    if (pipeFrames > 125) {
      add(PipeComponent());
      pipeFrames = 0;
    }

    var pipes = children.whereType<PipeComponent>();
    for (PipeComponent pipe in pipes) {
      if (pipe.position.x < -pipe.size.x - 20) {
        remove(pipe);
      }
    }
  }

  @override
  void onTapDown(TapDownInfo info) {
    player.jump();
    super.onTapDown(info);
  }

  @override
  void renderTree(Canvas canvas) {
    TextPaint textPaint = TextPaint();
    super.renderTree(canvas);
    textPaint.render(canvas, "FPS: ${fps()}", Vector2(0, 0));
  }

  @override
  KeyEventResult onKeyEvent(
    RawKeyEvent event,
    Set<LogicalKeyboardKey> keysPressed,
  ) {
    final isKeyDown = event is RawKeyDownEvent;
    final isSpace = keysPressed.contains(LogicalKeyboardKey.space);

    if (isSpace && isKeyDown) {
      player.jump();
      return KeyEventResult.handled;
    }
    return KeyEventResult.ignored;
  }
}
