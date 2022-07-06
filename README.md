
# zecmarketcap API

Backend API for zecmarketcap.com






## API Reference

#### Get coin info 

```http
  GET /getFeed
```
Returns top 80 coins fetched from coinmarketcap api.  Uses caching to ensure that data is up to date within the last 4 minutes under worst case scenario.

```http
  GET /getLastRefreshed
```
Returns epoch time of last time the cache was refreshed from coinmarketcap API

```http
  GET /getMostRecentMessage
```
Returns the shielded message of the latest transaction sent to the zec wallet

