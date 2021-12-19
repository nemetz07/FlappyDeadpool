import 'package:flutter/material.dart';

class ScoresScreen extends StatelessWidget {
  const ScoresScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: const Text("Eredmények"),
        centerTitle: true,
        elevation: 0,
        backgroundColor: Colors.black12,
      ),
    );
  }
}
