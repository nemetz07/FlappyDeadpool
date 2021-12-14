import 'package:flutter/material.dart';
import 'package:flutter_hazi/provider/difficulty_state.dart';
import 'package:provider/provider.dart';

class Settings extends StatelessWidget {
  Settings({Key? key}) : super(key: key);

  // Difficulty difficulty = Difficulty.NORMAL;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Beállítások"),
        centerTitle: true,
        elevation: 0,
        backgroundColor: Colors.black12,
      ),
      body: Material(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              ListTile(
                  title: Text(
                "Nehézség",
                style: TextStyle(color: Colors.white),
              )),
              ListTile(
                title:
                    const Text('Könnyű', style: TextStyle(color: Colors.white)),
                leading: Radio<Difficulty>(
                  value: Difficulty.EASY,
                  groupValue: context.watch<DifficultyState>().difficulty,
                  onChanged: (Difficulty? value) {
                    Provider.of<DifficultyState>(context, listen: false)
                        .setDifficulty(Difficulty.EASY);
                  },
                ),
              ),
              ListTile(
                title:
                    const Text('Normál', style: TextStyle(color: Colors.white)),
                leading: Radio<Difficulty>(
                  value: Difficulty.NORMAL,
                  groupValue: context.watch<DifficultyState>().difficulty,
                  onChanged: (Difficulty? value) {
                    Provider.of<DifficultyState>(context, listen: false)
                        .setDifficulty(Difficulty.NORMAL);
                  },
                ),
              ),
              ListTile(
                title:
                    const Text('Nehéz', style: TextStyle(color: Colors.white)),
                leading: Radio<Difficulty>(
                  value: Difficulty.HARD,
                  groupValue: context.watch<DifficultyState>().difficulty,
                  onChanged: (Difficulty? value) {
                    Provider.of<DifficultyState>(context, listen: false)
                        .setDifficulty(Difficulty.HARD);
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
