import 'package:flame/flame.dart';
import 'package:flame/game.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hazi/provider/difficulty_state.dart';
import 'package:flutter_hazi/provider/navigation_service.dart';
import 'package:flutter_hazi/provider/score_counter.dart';
import 'package:flutter_hazi/screens/menu.dart';
import 'package:flutter_hazi/screens/scores.dart';
import 'package:flutter_hazi/screens/settings.dart';
import 'package:provider/provider.dart';

import 'game_runner.dart';

main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Flame.device.fullScreen();
  await Flame.device.setPortrait();

  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => ScoreCounter()),
        ChangeNotifierProvider(create: (_) => DifficultyState())
      ],
      child: MaterialApp(
        navigatorKey: NavigationService.navigatorKey,
        debugShowCheckedModeBanner: false,
        theme: ThemeData.from(
          colorScheme: ColorScheme.light(
            primary: Colors.red,
            background: Colors.red.shade900,
            secondary: Colors.white,
          ),
        ),
        home: const Menu(),
        routes: {
          '/game': (context) {
            Provider.of<ScoreCounter>(context, listen: false).reset();
            return GameWidget(
              game: GameRunner(),
              // overlayBuilderMap: {'score': createScoreOverlay},
              overlayBuilderMap: {
                'score': (context, game) => Align(
                      alignment: Alignment.topRight,
                      child: Padding(
                        padding: const EdgeInsets.symmetric(
                          vertical: 30.0,
                          horizontal: 15,
                        ),
                        child: Material(
                          color: Colors.transparent,
                          textStyle: const TextStyle(
                            color: Colors.white,
                            fontSize: 56,
                          ),
                          child: Text(
                            '${context.watch<ScoreCounter>().score}',
                          ),
                        ),
                      ),
                    )
              },
              initialActiveOverlays: const ['score'],
            );
          },
          '/settings': (context) => Settings(),
          '/scores': (context) => const Scores(),
        },
      ),
    ),
  );
}

Widget createScoreOverlay(context, game) {
  return Align(
    alignment: Alignment.topRight,
    child: Padding(
      padding: const EdgeInsets.symmetric(
        vertical: 30.0,
        horizontal: 15,
      ),
      child: Material(
        color: Colors.transparent,
        textStyle: const TextStyle(
          color: Colors.white,
          fontSize: 56,
        ),
        child: Text(
          '${context.watch<ScoreCounter>().score}',
        ),
      ),
    ),
  );
}
