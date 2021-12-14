import 'package:flutter/material.dart';
import 'package:flutter_hazi/ui/custom_outlined_button.dart';

class Menu extends StatelessWidget {
  const Menu({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Flappy Deadpool"),
        centerTitle: true,
        elevation: 0,
        backgroundColor: Colors.black12,
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            CustomOutlinedButton(
              "Játék",
              onPressed: () {
                Navigator.of(context).pushNamed("/game");
              },
            ),
            const SizedBox(
              height: 25,
            ),
            CustomOutlinedButton(
              "Eredmények",
              onPressed: () {
                Navigator.of(context).pushNamed("/scores");
              },
            ),
            const SizedBox(
              height: 25,
            ),
            CustomOutlinedButton(
              "Beállítások",
              onPressed: () {
                Navigator.of(context).pushNamed("/settings");
              },
            ),
          ],
        ),
      ),
    );
  }
}
