import 'package:flutter/cupertino.dart';

class DifficultyState extends ChangeNotifier {
  Difficulty _difficulty = Difficulty.HARD;

  Difficulty get difficulty => _difficulty;

  void setDifficulty(Difficulty difficulty) {
    _difficulty = difficulty;
    notifyListeners();
  }
}

enum Difficulty { EASY, NORMAL, HARD }
