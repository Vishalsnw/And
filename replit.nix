
{ pkgs }: {
  deps = [
    pkgs.openjdk17
    pkgs.android-tools
    pkgs.curl
    pkgs.unzip
  ];
  
  env = {
    JAVA_HOME = "${pkgs.openjdk17}/lib/openjdk";
  };
}
