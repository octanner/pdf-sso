#!/bin/bash

for i in $(eval echo {1..$1})
do
  curl --request POST \
  --url https://pdfsso-core-qa.alamoapp.octanner.io/pdf/create \
  --header 'content-type: application/json' \
  --data '{
    "customerId": "f8a51d3d-9e56-4ece-9e87-f1d11646bd5c",
    "identityId": "4b20a97d-0116-4d59-8374-e46ba213561b",
    "password": "tester"
}'
done

ab -p post_loc.txt -T application/json -H 'Authorization: Token abcd1234' -c 10 -n 2000 http://example.com/api/v1/locations/
