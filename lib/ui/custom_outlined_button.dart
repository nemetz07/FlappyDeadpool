import 'package:flutter/material.dart';

class CustomOutlinedButton extends StatelessWidget {
  const CustomOutlinedButton(this.title,
      {required this.onPressed,
      Key? key,
      this.minimumSize = const Size(250, 20),
      this.fontSize = 36})
      : super(key: key);

  final String title;
  final Size minimumSize;
  final double fontSize;
  final Function() onPressed;

  @override
  Widget build(BuildContext context) {
    return OutlinedButton(
      onPressed: onPressed,
      style: OutlinedButton.styleFrom(
        side: const BorderSide(width: 2.0, color: Colors.white),
        minimumSize: minimumSize,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(25.0),
        ),
        padding: const EdgeInsets.symmetric(
          horizontal: 25,
          vertical: 5,
        ),
      ),
      child: Text(
        title,
        style: TextStyle(
          fontSize: fontSize,
          color: Colors.white,
        ),
      ),
    );
  }
}
