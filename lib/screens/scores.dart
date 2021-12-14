import 'package:flutter/material.dart';

class Scores extends StatelessWidget {
  const Scores({Key? key}) : super(key: key);

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
