import 'dart:math';

import 'package:flame/components.dart';
import 'package:flame/geometry.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/painting.dart';
import 'package:flutter_hazi/game_runner.dart';

class PipeComponent extends PositionComponent
    with HasGameRef<GameRunner>, HasHitboxes, Collidable {
  static const double _PIPE_WIDTH = 170;
  static const double _MIN_GAP_HEIGHT = 300;
  static const double _MAX_GAP_HEIGHT = 500;
  static const double _MIN_GAP_START_Y = 50;

  double speedY = 0;

  late double gapHeight;
  late double gapStartY;
  late double maxGapStartY;

  static final strokePaint = Paint()
    ..color = const Color(0xFF000000)
    ..style = PaintingStyle.stroke
    ..strokeWidth = 2;

  static final fillPaint = Paint()
    ..shader = const LinearGradient(
      colors: [
        Color.fromARGB(255, 160, 0, 0),
        Color.fromARGB(255, 230, 0, 0),
        Color.fromARGB(255, 230, 0, 0),
        Color.fromARGB(255, 160, 0, 0),
      ],
      stops: [
        0.0,
        0.3,
        0.6,
        1.0,
      ],
    ).createShader(
      const Rect.fromLTWH(
        0,
        0,
        100,
        100,
      ),
    );

  static final random = Random();

  @override
  Future<void>? onLoad() {
    gapHeight = random.nextDouble() * (_MAX_GAP_HEIGHT - _MIN_GAP_HEIGHT) +
        _MIN_GAP_HEIGHT;
    maxGapStartY = gameRef.size.y - gapHeight - 50;
    gapStartY = random.nextDouble() * (maxGapStartY - _MIN_GAP_START_Y) +
        _MIN_GAP_START_Y;

    size = Vector2(_PIPE_WIDTH, gameRef.size.y);
    position = Vector2(gameRef.size.x + 20, 0);
    var screenHeight = gameRef.size.y;

    var topHitboxHeight = gapStartY / screenHeight;
    var topHitbox = HitboxRectangle(relation: Vector2(1, topHitboxHeight));
    topHitbox.relativeOffset = Vector2(0, -1 + topHitboxHeight);
    addHitbox(topHitbox);

    var bottomHitboxHeight =
        (screenHeight - gapStartY - gapHeight) / screenHeight;
    var bottomHitbox =
        HitboxRectangle(relation: Vector2(1, bottomHitboxHeight));
    bottomHitbox.relativeOffset = Vector2(0, 1 - bottomHitboxHeight);
    addHitbox(bottomHitbox);

    var scoreHitbox = ScoreHitbox()
      ..size = Vector2(10, screenHeight)
      ..position = Vector2(_PIPE_WIDTH, 0);
    add(scoreHitbox);

    return super.onLoad();
  }

  @override
  void renderHitboxes(Canvas canvas, {Paint? paint}) {
    for (var shape in hitboxes) {
      shape.render(
          canvas, Paint()..color = const Color.fromARGB(255, 0, 255, 0));
    }
  }

  @override
  void render(Canvas canvas) {
    super.render(canvas);

    var topRect = Rect.fromLTRB(0, 0, _PIPE_WIDTH, gapStartY);
    var topRectCap =
        Rect.fromLTRB(-20, gapStartY - 60, _PIPE_WIDTH + 20, gapStartY);
    var bottomRect =
        Rect.fromLTRB(0, gapStartY + gapHeight, _PIPE_WIDTH, gameRef.size.y);
    var bottomRectCap = Rect.fromLTRB(-20, gapStartY + gapHeight,
        _PIPE_WIDTH + 20, gapStartY + gapHeight + 60);
    canvas.drawRect(
      topRect,
      fillPaint,
    );
    canvas.drawRect(
      topRect,
      strokePaint,
    );
    canvas.drawRect(
      topRectCap,
      fillPaint,
    );
    canvas.drawRect(
      topRectCap,
      strokePaint,
    );
    canvas.drawRect(
      bottomRect,
      fillPaint,
    );
    canvas.drawRect(
      bottomRect,
      strokePaint,
    );
    canvas.drawRect(
      bottomRectCap,
      fillPaint,
    );
    canvas.drawRect(
      bottomRectCap,
      strokePaint,
    );
  }

  @override
  void update(double dt) {
    position.x -= 4;
  }
}

class ScoreHitbox extends PositionComponent with HasHitboxes, Collidable {
  @override
  Future<void>? onLoad() {
    addHitbox(HitboxRectangle());
    return super.onLoad();
  }
}
