import 'package:flutter/cupertino.dart';

class SettingsState extends ChangeNotifier {
  bool _isDebug = false;

  bool get isDebug => _isDebug;

  Difficulty _difficulty = Difficulty.hard;

  Difficulty get difficulty => _difficulty;

  void setDebugMode(bool mode) {
    _isDebug = mode;
    notifyListeners();
  }

  void setDifficulty(Difficulty difficulty) {
    _difficulty = difficulty;
    notifyListeners();
  }
}

enum Difficulty { easy, normal, hard }
