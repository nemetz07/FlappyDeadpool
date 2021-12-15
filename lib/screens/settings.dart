import 'package:flutter/material.dart';
import 'package:flutter_hazi/provider/settings_state.dart';
import 'package:flutter_hazi/ui/custom_outlined_button.dart';
import 'package:provider/provider.dart';

class Settings extends StatelessWidget {
  const Settings({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Beállítások"),
        centerTitle: true,
        elevation: 0,
        backgroundColor: Colors.black12,
      ),
      body: Theme(
        data: ThemeData.dark(),
        child: Center(
          child: Container(
            width: 300,
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const ListTile(
                  title: Text(
                    "Nehézség",
                    style: TextStyle(color: Colors.white),
                  ),
                ),
                ListTile(
                  title: const Text('Könnyű',
                      style: TextStyle(color: Colors.white)),
                  leading: Radio<Difficulty>(
                    value: Difficulty.easy,
                    groupValue: context.watch<SettingsState>().difficulty,
                    onChanged: (Difficulty? value) {
                      Provider.of<SettingsState>(context, listen: false)
                          .setDifficulty(Difficulty.easy);
                    },
                  ),
                ),
                ListTile(
                  title: const Text('Normál',
                      style: TextStyle(color: Colors.white)),
                  leading: Radio<Difficulty>(
                    value: Difficulty.normal,
                    groupValue: context.watch<SettingsState>().difficulty,
                    onChanged: (Difficulty? value) {
                      Provider.of<SettingsState>(context, listen: false)
                          .setDifficulty(Difficulty.normal);
                    },
                  ),
                ),
                ListTile(
                  title: const Text('Nehéz',
                      style: TextStyle(color: Colors.white)),
                  leading: Radio<Difficulty>(
                    value: Difficulty.hard,
                    groupValue: context.watch<SettingsState>().difficulty,
                    onChanged: (Difficulty? value) {
                      Provider.of<SettingsState>(context, listen: false)
                          .setDifficulty(Difficulty.hard);
                    },
                  ),
                ),
                const ListTile(
                  title: Text(
                    "Hibakeresés",
                    style: TextStyle(color: Colors.white),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 15.0),
                  child: Switch(
                    value: context.watch<SettingsState>().isDebug,
                    onChanged: (bool value) {
                      Provider.of<SettingsState>(context, listen: false)
                          .setDebugMode(value);
                    },
                  ),
                ),
                const SizedBox(
                  height: 50,
                ),
                CustomOutlinedButton(
                  "Kijelentkezés",
                  onPressed: () {},
                  minimumSize: const Size(250, 50),
                  fontSize: 24,
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
