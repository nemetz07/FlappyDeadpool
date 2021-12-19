import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hazi/ui/custom_outlined_button.dart';
import 'package:google_sign_in/google_sign_in.dart';

class MenuScreen extends StatelessWidget {
  const MenuScreen({Key? key}) : super(key: key);

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
            const SizedBox(
              height: 25,
            ),
            CustomOutlinedButton(
              "Login",
              onPressed: () async {
                var result = await GoogleSignIn(clientId: '636764302936-rjt49aon9v4i577eh100rl20tcs45qt0.apps.googleusercontent.com').signIn();
                print(result);
              },
            ),
          ],
        ),
      ),
    );
  }
}
