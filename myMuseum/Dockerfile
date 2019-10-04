FROM tomcat:8

MAINTAINER chambab@gmail.com

ENV PGSQL_HOST localhost
ENV PGSQL_PORT 6789
ENV PGSQL_DBNAME mymuseum
ENV PGSQL_ID id
ENV PGSQL_PW pw

RUN mkdir /usr/local/ant
WORKDIR /usr/local/ant
RUN curl -O -L http://apache.tt.co.kr//ant/binaries/apache-ant-1.9.14-bin.zip
RUN unzip apache-ant-1.9.14-bin.zip
RUN mv apache-ant-1.9.14/* /usr/local/ant/.
ENV PATH /usr/local/ant/bin:$PATH

RUN mkdir /usr/local/DIST
WORKDIR /usr/local/DIST
RUN git clone https://github.com/chambab/myMuseum.git

WORKDIR /usr/local/DIST/myMuseum


RUN ant -f ./myMuseum/ant/build_myMuseum.xml build
RUN mv /usr/local/tomcat/webapps/* /usr/local/tomcat/temp/

RUN mkdir /usr/local/tomcat/webapps/ROOT
RUN cp -R ./myMuseum/webapp/* /usr/local/tomcat/webapps/ROOT/


COPY mymuseum-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/mymuseum-entrypoint.sh
#RUN ln -s usr/local/bin/docker-entrypoint.sh /
ENTRYPOINT ["/usr/local/bin/mymuseum-entrypoint.sh"]

EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]
