import 'package:flutter/widgets.dart';

class ScoreCounter with ChangeNotifier {
  int _score = 0;

  int get score => _score;

  void increment() {
    _score++;
    notifyListeners();
  }

  void reset() {
    _score = 0;
    notifyListeners();
  }
}
