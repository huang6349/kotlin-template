FROM docker.io/amazoncorretto:8u352
ADD qwerty-*.jar /app.jar
ADD wait /wait
RUN chmod +x /wait
EXPOSE 8085
EXPOSE 8088
CMD /wait && java -Djava.security.egd=file:/dev/./urandom -Xms1024M -Xmx1024M -Xmn256m -jar /app.jar --spring.profiles.active=prod
