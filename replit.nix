
{ pkgs }: {
  deps = [
    pkgs.openjdk17
    pkgs.android-tools
    pkgs.gradle
    pkgs.androidenv.androidPkgs_9_0.platform-tools
    pkgs.androidenv.androidPkgs_9_0.build-tools_30_0_3
    pkgs.androidenv.androidPkgs_9_0.platforms.android-34
  ];
}
