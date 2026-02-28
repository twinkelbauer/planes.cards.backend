

# Leg

A flight's leg is a smaller part of an overall journey which involves landing at an intermediate airport

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**sequenceNumber** | **Integer** | The sequence number of this leg in the associated itinerary |  |
|**origin** | **String** | The departure airport code |  |
|**destination** | **String** | The arrival airport code |  |
|**serviceType** | **String** | The service type of the leg. An uppercase letter. |  |
|**aircraftOwner** | **String** | The aircraft owner or administrative carrier (an airline code) of the leg |  [optional] |
|**aircraftType** | **String** | The fleet type identifier 3 characters, can contain letters and numbers. |  |
|**aircraftConfigurationVersion** | **String** | The Aircraft Configuration/Version. |  [optional] |
|**registration** | **String** | Aircraft Registration Information |  [optional] |
|**op** | **Boolean** | Signals whether this is an operating or a marketing leg |  [optional] |
|**aircraftDepartureTimeUTC** | **Integer** | The UTC Aircraft Scheduled Time of Departure for this leg in minutes |  [optional] |
|**aircraftDepartureTimeDateDiffUTC** | **Integer** | The date difference between the flight UTC date and the aircraft departure time of this leg in days. |  [optional] |
|**aircraftDepartureTimeLT** | **Integer** | The LT Aircraft Scheduled Time of Departure for this leg in minutes |  [optional] |
|**aircraftDepartureTimeDateDiffLT** | **Integer** | The date difference between the flight LT date and the aircraft departure time of this leg in days. |  [optional] |
|**aircraftDepartureTimeVariation** | **Integer** | The departure time difference between the LT and UTC time in minutes. |  [optional] |
|**aircraftArrivalTimeUTC** | **Integer** | The UTC Aircraft Scheduled Time of Arrival for this leg in minutes |  [optional] |
|**aircraftArrivalTimeDateDiffUTC** | **Integer** | The date difference between the flight UTC date and the aircraftarrival time of this leg in days. |  [optional] |
|**aircraftArrivalTimeLT** | **Integer** | The LT Aircraft Scheduled Time of Arrival for this leg in minutes |  [optional] |
|**aircraftArrivalTimeDateDiffLT** | **Integer** | The date difference between the flight LT date and the aircraft arrival time of this leg in days. |  [optional] |
|**aircraftArrivalTimeVariation** | **Integer** | The arrival time difference between the LT and UTC time in minutes. |  [optional] |



