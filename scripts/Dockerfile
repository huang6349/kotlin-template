FROM registry.cn-hangzhou.aliyuncs.com/huang6349/amazoncorretto:8u412
ADD project-web-*.jar /app.jar
ADD wait /wait
RUN chmod +x /wait
EXPOSE 8085
EXPOSE 8088
CMD /wait && java -Djava.security.egd=file:/dev/./urandom -Xms1024M -Xmx1024M -Xmn256m -jar /app.jar --spring.profiles.active=prod
