

# FlightAggregate

A FlightAggregate is a date-wise aggregation of otherwise single-dated flights. I.e. flights with identical attributes are aggregated into periods of operation.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**airline** | **String** | The airline code of the flight |  [optional] |
|**flightNumber** | **Integer** | The flight number |  [optional] |
|**suffix** | **String** | Operational suffix. One character or empty string. |  [optional] |
|**periodOfOperationUTC** | [**PeriodOfOperation**](PeriodOfOperation.md) |  |  [optional] |
|**periodOfOperationLT** | [**PeriodOfOperation**](PeriodOfOperation.md) |  |  [optional] |
|**legs** | [**List&lt;Leg&gt;**](Leg.md) | The legs |  [optional] |
|**dataElements** | [**List&lt;DataElement&gt;**](DataElement.md) | The data elements |  [optional] |



