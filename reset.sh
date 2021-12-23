

# 一键打包+替换,然后手动在服务器命令行界面输入reload即可完成代码更新
rm -rf ~/Desktop/serve/plugins/minecraft-0.1.jar
/Users/tcyeee/Documents/utils/apache-maven-3.6.3/bin/mvn package -f ~/Desktop/pluginBenBen/pom.xml
mv ~/Desktop/pluginBenBen/target/minecraft-0.1.jar ~/Desktop/serve/plugins/


