# cash-man

## POST `/transactions/slips`

Record the sale of a Betslip

### Request Parameters

|Name|Type|Description|
|---|---|---|
|venueId|string|Unique Id of the Venue where the Betslip was sold|
|deviceId|string|Unique Id of the Device that was used to process the Betslip|
|slipId|string|Unique Id of the Betslip sold|
|amount|currency|Total stake of the Betslip|

```
curl -X POST \
    -H 'content-type: application/json' \
    'localhost:8081/transactions/slips?venueId=V1&deviceId=T1.1&slipId=S1&amount=20.00'
```

## GET `/events/feed`

Returns a stream of messages representing significant events taking place within the 'Cash Management' domain.

content-type: `application/stream+json`

All events have a common set of attributes:

```json
{
  "eventType": "E<name>",
  "timestamp": 1534694759326,
  "venueId": "V1",
  "deviceId": "T1.1",
  ...
}
``` 

Additional attributes may be present based on the `eventType`

## `EBetslipSold`

A Betslip has been sold to a Customer

```json
{
  "eventType": "EBetslipSold",
  ...
  "slipId": "S1",
  "amount": 20.00
}
```

## `EBalanceChanged`

The balance of an account has changed

```json
{
  "eventType": "EBalanceChanged",
  ...
  "accountId": "A1",
  "amount": 20.00,
  "balance": 95.00
}
```
