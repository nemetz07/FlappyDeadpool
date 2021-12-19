import 'package:firebase_core/firebase_core.dart';
import 'package:flame/flame.dart';
import 'package:flame/game.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hazi/provider/navigation_service.dart';
import 'package:flutter_hazi/provider/score_counter.dart';
import 'package:flutter_hazi/provider/settings_state.dart';
import 'package:flutter_hazi/screens/login_screen.dart';
import 'package:flutter_hazi/screens/menu_screen.dart';
import 'package:flutter_hazi/screens/scores_screen.dart';
import 'package:flutter_hazi/screens/settings_screen.dart';
import 'package:provider/provider.dart';

import 'game_runner.dart';

main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Flame.device.fullScreen();
  await Flame.device.setPortrait();

  await Firebase.initializeApp();

  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => ScoreCounter()),
        ChangeNotifierProvider(create: (_) => SettingsState())
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
        home: const MenuScreen(),
        routes: {
          '/login': (context) => LoginScreen(),
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
          '/settings': (context) => const SettingsScreen(),
          '/scores': (context) => const ScoresScreen(),
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
