#!/bin/bash
cmd='docker-compose -f docker-compose.yml'
for arg in "$@"
do
  # echo "Arg #$index = $arg"
  cmd+=" -f docker-compose.$arg.yml"
done
cmd+=' up -d'

# cmd='docker-compose -f docker-compose.yml -f docker-compose.ping.yml up -d'
echo $cmd
exec $cmd