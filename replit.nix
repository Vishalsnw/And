
{ pkgs }: {
  deps = [
    pkgs.graalvm17-ce
    pkgs.android-studio
    pkgs.android-tools
  ];
  env = {
    ANDROID_HOME = "${pkgs.android-studio}/libexec/android-studio/sdk";
    ANDROID_SDK_ROOT = "${pkgs.android-studio}/libexec/android-studio/sdk";
  };
}
