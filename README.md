# API

Notes:

    - replace http://localhost:8080 with real host
    - responses well-formatted for readability
    - there are no space symbols in real responses
    - here, fake tokens just for example

## Registration

Request:
```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"username":"dima","password":"123456"}' \
    http://localhost:8080/register
```

Response:
```json
{"status": "OK"}
```

Request (send for the second time):
```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"username":"dima","password":"123456"}' \
    http://localhost:8080/register
```

Response (with error):
```json
{
  "errorMessage": "username is already taken",
  "status": "ERROR"
}
```


## Authentication:

Request:
```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"username":"dima","password":"123456"}' \
    http://localhost:8080/auth
```

Response:
```json
{
  "status": "OK",
  "token": "dima2019-04-10T19:25:07.916"
}
```

Request (wrong password):
```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"username":"dima","password":"_"}' \
    http://localhost:8080/auth
```
Response:
```json
{
  "errorMessage": "Wrong username or password",
  "status": "ERROR"
}
```

## Subscriptions:

Request (subscribe) (full podcast info):
```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"token":"dima2019-04-10T19:25:07.916","podcastId":1001642707,"podcastFeedUrl":"https://arzamas.academy/feed_v1/podcast.rss"}' \
    http://localhost:8080/subscriptions
```
Default status_ok response.

Request (subscribe) (with no podcast info):
```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"token":"dima2019-04-10T19:25:07.916","podcastId":1209828744}' \
    http://localhost:8080/subscriptions
```
Default status_ok response.

Request (get subscriptions):
```bash
curl -X GET \
    -H 'Content-Type: application/json' \
    --data '{"token":"dima2019-04-10T19:25:07.916"}' \
    http://localhost:8080/subscriptions
```

Response:
```json
{
  "status":"OK",
  "subscriptions": [
    {
      "id": 1001642707,
      "feedUrl": "https://arzamas.academy/feed_v1/podcast.rss"
    },
    {
      "id": 1209828744,
      "feedUrl": "http://feeds.soundcloud.com/users/soundcloud:users:291337106/sounds.rss"
    }
  ]
}
```

Request (unsubscribe):
```bash
curl -X DELETE \
    -H 'Content-Type: application/json' \
    --data '{"token":"dima2019-04-10T19:25:07.916","podcastId":1001642707}' \
    http://localhost:8080/subscriptions
```
Default status_ok response.

## History:
# TODO

## Progress:
Request (create or update progress):

Specify episode guid, time in seconds.

```bash
curl -X POST \
    -H 'Content-Type: application/json' \
    --data '{"token":"dima2019-04-10T19:25:07.916","guid":"tag:soundcloud,2010:tracks/602586567","time":10}' \
    http://localhost:8080/progress
```
# TODO

