#!/usr/bin/env bash
# extract the protocol
proto="`echo $DATABASE_URL | grep '://' | sed -e's,^\(.*://\).*,\1,g'`"
# remove the protocol
url=`echo $DATABASE_URL | sed -e s,$proto,,g`

# extract the user and password (if any)
userpass="`echo $url | grep @ | cut -d@ -f1`"
pass=`echo $userpass | grep : | cut -d: -f2`
if [ -n "$pass" ]; then
    user=`echo $userpass | grep : | cut -d: -f1`
else
    user=$userpass
fi

# extract the host -- updated
hostport=`echo $url | sed -e s,$userpass@,,g | cut -d/ -f1`
port=`echo $hostport | grep : | cut -d: -f2`
if [ -n "$port" ]; then
    host=`echo $hostport | grep : | cut -d: -f1`
else
    host=$hostport
fi

# extract the path (if any)
path="`echo $url | grep / | cut -d/ -f2-`"

completeUrl="jdbc:postgresql://$host:$port/$path"

export DB_URL=$completeUrl
export DATABASE_PASSWORD=$pass
export DATABASE_USERNAME=$user
export DATABASE_HOST=$host
export DATABASE_PORT=$port
export DATABASE_NAME=$path

export search_dir=/app/target

for entry in "$search_dir"/*
do
  echo "$entry"
done

# Run the jar file
java -Djava.security.egd=file:/dev/./urandom -jar /app/target/pdfsso.jar
