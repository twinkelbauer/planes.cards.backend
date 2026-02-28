

# DataElement

A data element is an additional flight attribute as defined in SSIM, Chapter 2 dealing with a variety of characteristics, e.g.: * Traffic Restriction: 8 * Codeshare - Duplicate leg cross-reference: 10 * Codeshare - Operational leg cross-reference: 50 * Departure Terminal: 99 * Arrival Terminal: 98 * Passenger Reservation Booking Designator (PRBD): 106 * Meal service note: 109 * Inflight Service: 503 * Electronic Ticket Indicator: 505 * etc. 

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**startLegSequenceNumber** | **Integer** | The sequence number of the leg where data element boardpoint belongs to |  |
|**endLegSequenceNumber** | **Integer** | The sequence number of the leg where data element offpoint belongs to |  |
|**id** | **Integer** | The data element identifier - see SSIM, Chapter 2 for additional information |  |
|**value** | **String** | The data element value itself |  [optional] |



