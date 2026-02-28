# ScheduleApi

All URIs are relative to *https://api.lufthansa.com/v1/flight-schedules*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**flightschedulesCargoGet**](ScheduleApi.md#flightschedulesCargoGet) | **GET** /flightschedules/cargo | Returns cargo flights |
| [**flightschedulesGet**](ScheduleApi.md#flightschedulesGet) | **GET** /flightschedules | Search all flights |
| [**flightschedulesPassengerGet**](ScheduleApi.md#flightschedulesPassengerGet) | **GET** /flightschedules/passenger | Returns passenger flights |



## flightschedulesCargoGet

> List&lt;FlightAggregate&gt; flightschedulesCargoGet(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes)

Returns cargo flights

This operation returns schedules related to cargo flights only. In case a flight is marked both as cargo and passenger it will also be returned.

### Example

```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ScheduleApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.lufthansa.com/v1/flight-schedules");

        ScheduleApi apiInstance = new ScheduleApi(defaultClient);
        List<String> airlines = Arrays.asList(); // List<String> | The list of airline codes
        String startDate = "startDate_example"; // String | The period start date. SSIM date format DDMMMYY
        String endDate = "endDate_example"; // String | The period end date. SSIM date format DDMMMYY
        String daysOfOperation = "daysOfOperation_example"; // String | The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: '  34 6 '
        TimeMode timeMode = TimeMode.fromValue("UTC"); // TimeMode | The time mode of the period of operations
        String flightNumberRanges = "flightNumberRanges_example"; // String | The flight number range filter string. e.g.: '-100, 200, 100-200, 300-'
        String origin = "origin_example"; // String | Search for flights departing from this station. 3 letter IATA airport code.
        String destination = "destination_example"; // String | Search for flights arriving at this station. 3 letter IATA airport code.
        List<String> aircraftTypes = Arrays.asList(); // List<String> | The list of aircraft types
        try {
            List<FlightAggregate> result = apiInstance.flightschedulesCargoGet(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ScheduleApi#flightschedulesCargoGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **airlines** | [**List&lt;String&gt;**](String.md)| The list of airline codes | |
| **startDate** | **String**| The period start date. SSIM date format DDMMMYY | |
| **endDate** | **String**| The period end date. SSIM date format DDMMMYY | |
| **daysOfOperation** | **String**| The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39; | |
| **timeMode** | [**TimeMode**](.md)| The time mode of the period of operations | [enum: UTC, LT] |
| **flightNumberRanges** | **String**| The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39; | [optional] |
| **origin** | **String**| Search for flights departing from this station. 3 letter IATA airport code. | [optional] |
| **destination** | **String**| Search for flights arriving at this station. 3 letter IATA airport code. | [optional] |
| **aircraftTypes** | [**List&lt;String&gt;**](String.md)| The list of aircraft types | [optional] |

### Return type

[**List&lt;FlightAggregate&gt;**](FlightAggregate.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation |  -  |
| **206** | Result truncated |  -  |
| **400** | Validation error |  -  |
| **401** | Authentication required |  -  |
| **404** | Flight not found |  -  |
| **500** | Server error |  -  |


## flightschedulesGet

> List&lt;FlightAggregate&gt; flightschedulesGet(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes)

Search all flights

Returns information about all flights based on available search criteria. Some criteria are mandatory to make a call to the API.

### Example

```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ScheduleApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.lufthansa.com/v1/flight-schedules");

        ScheduleApi apiInstance = new ScheduleApi(defaultClient);
        List<String> airlines = Arrays.asList(); // List<String> | The list of airline codes
        String startDate = "startDate_example"; // String | The period start date. SSIM date format DDMMMYY
        String endDate = "endDate_example"; // String | The period end date. SSIM date format DDMMMYY
        String daysOfOperation = "daysOfOperation_example"; // String | The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: '  34 6 '
        TimeMode timeMode = TimeMode.fromValue("UTC"); // TimeMode | The time mode of the period of operations
        String flightNumberRanges = "flightNumberRanges_example"; // String | The flight number range filter string. e.g.: '-100, 200, 100-200, 300-'
        String origin = "origin_example"; // String | Search for flights departing from this station. 3 letter IATA airport code.
        String destination = "destination_example"; // String | Search for flights arriving at this station. 3 letter IATA airport code.
        List<String> aircraftTypes = Arrays.asList(); // List<String> | The list of aircraft types
        try {
            List<FlightAggregate> result = apiInstance.flightschedulesGet(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ScheduleApi#flightschedulesGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **airlines** | [**List&lt;String&gt;**](String.md)| The list of airline codes | |
| **startDate** | **String**| The period start date. SSIM date format DDMMMYY | |
| **endDate** | **String**| The period end date. SSIM date format DDMMMYY | |
| **daysOfOperation** | **String**| The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39; | |
| **timeMode** | [**TimeMode**](.md)| The time mode of the period of operations | [enum: UTC, LT] |
| **flightNumberRanges** | **String**| The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39; | [optional] |
| **origin** | **String**| Search for flights departing from this station. 3 letter IATA airport code. | [optional] |
| **destination** | **String**| Search for flights arriving at this station. 3 letter IATA airport code. | [optional] |
| **aircraftTypes** | [**List&lt;String&gt;**](String.md)| The list of aircraft types | [optional] |

### Return type

[**List&lt;FlightAggregate&gt;**](FlightAggregate.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation |  -  |
| **206** | Result truncated |  -  |
| **400** | Validation error |  -  |
| **401** | Authentication required |  -  |
| **404** | Flight not found |  -  |
| **500** | Server error |  -  |


## flightschedulesPassengerGet

> List&lt;FlightAggregate&gt; flightschedulesPassengerGet(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes)

Returns passenger flights

This operation returns schedules related only to passenger flights. In case a flight is marked both as cargo and passenger it will also be returned.

### Example

```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ScheduleApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api.lufthansa.com/v1/flight-schedules");

        ScheduleApi apiInstance = new ScheduleApi(defaultClient);
        List<String> airlines = Arrays.asList(); // List<String> | The list of airline codes
        String startDate = "startDate_example"; // String | The period start date. SSIM date format DDMMMYY
        String endDate = "endDate_example"; // String | The period end date. SSIM date format DDMMMYY
        String daysOfOperation = "daysOfOperation_example"; // String | The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: '  34 6 '
        TimeMode timeMode = TimeMode.fromValue("UTC"); // TimeMode | The time mode of the period of operations
        String flightNumberRanges = "flightNumberRanges_example"; // String | The flight number range filter string. e.g.: '-100, 200, 100-200, 300-'
        String origin = "origin_example"; // String | Search for flights departing from this station. 3 letter IATA airport code.
        String destination = "destination_example"; // String | Search for flights arriving at this station. 3 letter IATA airport code.
        List<String> aircraftTypes = Arrays.asList(); // List<String> | The list of aircraft types
        try {
            List<FlightAggregate> result = apiInstance.flightschedulesPassengerGet(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ScheduleApi#flightschedulesPassengerGet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **airlines** | [**List&lt;String&gt;**](String.md)| The list of airline codes | |
| **startDate** | **String**| The period start date. SSIM date format DDMMMYY | |
| **endDate** | **String**| The period end date. SSIM date format DDMMMYY | |
| **daysOfOperation** | **String**| The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39; | |
| **timeMode** | [**TimeMode**](.md)| The time mode of the period of operations | [enum: UTC, LT] |
| **flightNumberRanges** | **String**| The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39; | [optional] |
| **origin** | **String**| Search for flights departing from this station. 3 letter IATA airport code. | [optional] |
| **destination** | **String**| Search for flights arriving at this station. 3 letter IATA airport code. | [optional] |
| **aircraftTypes** | [**List&lt;String&gt;**](String.md)| The list of aircraft types | [optional] |

### Return type

[**List&lt;FlightAggregate&gt;**](FlightAggregate.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Successful operation |  -  |
| **206** | Result truncated |  -  |
| **400** | Validation error |  -  |
| **401** | Authentication required |  -  |
| **404** | Flight not found |  -  |
| **500** | Server error |  -  |

