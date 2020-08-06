#!/bin/bash
export JWK_URL=https://core-qa.octanner.io/.well-known/jwks.json
export GRAPH_QL_URL=https://identity2-core-qa.alamoapp.octanner.io/graphql
export CORE_AUTH_CLIENT_ID=302f6bb7-c09e-48ab-9628-67311dcabd57
export CORE_AUTH_CLIENT_SECRET=c1a919cce00a03c44004db923ef026e0e6fec09ed6ef0a0700d888a1dbd9fea7
export TOKEN_URL=https://core-qa.octanner.io/sso/oauth/token
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