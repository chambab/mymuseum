#!/bin/bash

sed -i "s/pgsql_host=/pgsql_host=$PGSQL_HOST/g" /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/context.properties
sed -i "s/pgsql_port=/pgsql_port=$PGSQL_PORT/g" /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/context.properties
sed -i "s/pgsql_dbname=/pgsql_dbname=$PGSQL_DBNAME/g" /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/context.properties
sed -i "s/pgsql_id=/pgsql_id=${PGSQL_ID}/g" /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/context.properties
sed -i "s/pgsql_pw=/pgsql_pw=$PGSQL_PW/g" /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/context.properties

exec "$@"
