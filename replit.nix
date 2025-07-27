
{ pkgs }: {
  deps = [
    pkgs.openjdk17
    pkgs.gradle
    pkgs.android-tools
  ];
  
  env = {
    JAVA_HOME = "${pkgs.openjdk17}";
    ANDROID_HOME = "/home/runner/.android-sdk";
    PATH = "${pkgs.openjdk17}/bin:${pkgs.android-tools}/bin:$PATH";
  };
}
