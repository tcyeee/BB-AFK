

# 一键打包+替换,然后手动在服务器命令行界面输入reload即可完成代码更新
rm -rf /Users/tcyeee/Documents/cy_project/minecraft/server/plugins/BenBenAfk-1.0.jar
/Users/tcyeee/Documents/utils/apache-maven-3.6.3/bin/mvn package -f /Users/tcyeee/Documents/cy_project/minecraft/minecraft_benben/pom.xml
mv /Users/tcyeee/Documents/cy_project/minecraft/minecraft_benben/target/BenBenAfk-1.0.jar /Users/tcyeee/Documents/cy_project/minecraft/server/plugins/


