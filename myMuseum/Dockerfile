FROM tomcat:8

MAINTAINER chambab@gmail.com

RUN mkdir /usr/local/ant
WORKDIR /usr/local/ant
RUN curl -O -L http://apache.tt.co.kr//ant/binaries/apache-ant-1.9.14-bin.zip
RUN unzip apache-ant-1.9.14-bin.zip
RUN mv apache-ant-1.9.14/* /usr/local/ant/.

RUN mkdir /usr/local/DIST
WORKDIR /usr/local/DIST
RUN git clone https://github.com/chambab/myMuseum.git

RUN cp ./myMuseum.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
