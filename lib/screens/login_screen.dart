import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hazi/ui/custom_outlined_button.dart';
import 'package:google_sign_in/google_sign_in.dart';

class LoginScreen extends StatelessWidget {
  LoginScreen({Key? key}) : super(key: key);

  final GoogleSignIn _googleSignIn = GoogleSignIn();

  Future<void> _handleSignIn() async {
    try {
      await _googleSignIn.signIn();
      print(FirebaseAuth.instance.currentUser);
    } catch (error) {
      print(error);
    }
  }

  @override
  Widget build(BuildContext context) {
    print(FirebaseAuth.instance.currentUser);
    return Scaffold(
      body: Center(
        child: CustomOutlinedButton(
          "Sign in",
          onPressed: () {
            _handleSignIn();
          },
        ),
      ),
    );
  }
}
