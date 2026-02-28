package org.openapitools.client.api;

import org.openapitools.client.ApiClient;

import org.openapitools.client.model.ErrorResponse;
import org.openapitools.client.model.FlightAggregate;
import org.openapitools.client.model.TimeMode;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-02-28T13:12:20.084199429+01:00[Europe/Berlin]", comments = "Generator version: 7.20.0")
public class ScheduleApi {
    private ApiClient apiClient;

    public ScheduleApi() {
        this(new ApiClient());
    }

    public ScheduleApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Returns cargo flights
     * This operation returns schedules related to cargo flights only. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return List&lt;FlightAggregate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec flightschedulesCargoGetRequestCreation(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'airlines' is set
        if (airlines == null) {
            throw new WebClientResponseException("Missing the required parameter 'airlines' when calling flightschedulesCargoGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'startDate' is set
        if (startDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'startDate' when calling flightschedulesCargoGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'endDate' is set
        if (endDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'endDate' when calling flightschedulesCargoGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'daysOfOperation' is set
        if (daysOfOperation == null) {
            throw new WebClientResponseException("Missing the required parameter 'daysOfOperation' when calling flightschedulesCargoGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeMode' is set
        if (timeMode == null) {
            throw new WebClientResponseException("Missing the required parameter 'timeMode' when calling flightschedulesCargoGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "airlines", airlines));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "flightNumberRanges", flightNumberRanges));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startDate", startDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endDate", endDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "daysOfOperation", daysOfOperation));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "timeMode", timeMode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "destination", destination));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "aircraftTypes", aircraftTypes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return apiClient.invokeAPI("/flightschedules/cargo", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns cargo flights
     * This operation returns schedules related to cargo flights only. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return List&lt;FlightAggregate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<FlightAggregate> flightschedulesCargoGet(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return flightschedulesCargoGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes).bodyToFlux(localVarReturnType);
    }

    /**
     * Returns cargo flights
     * This operation returns schedules related to cargo flights only. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return ResponseEntity&lt;List&lt;FlightAggregate&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<FlightAggregate>>> flightschedulesCargoGetWithHttpInfo(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return flightschedulesCargoGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes).toEntityList(localVarReturnType);
    }

    /**
     * Returns cargo flights
     * This operation returns schedules related to cargo flights only. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec flightschedulesCargoGetWithResponseSpec(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        return flightschedulesCargoGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes);
    }

    /**
     * Search all flights
     * Returns information about all flights based on available search criteria. Some criteria are mandatory to make a call to the API.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return List&lt;FlightAggregate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec flightschedulesGetRequestCreation(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'airlines' is set
        if (airlines == null) {
            throw new WebClientResponseException("Missing the required parameter 'airlines' when calling flightschedulesGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'startDate' is set
        if (startDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'startDate' when calling flightschedulesGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'endDate' is set
        if (endDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'endDate' when calling flightschedulesGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'daysOfOperation' is set
        if (daysOfOperation == null) {
            throw new WebClientResponseException("Missing the required parameter 'daysOfOperation' when calling flightschedulesGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeMode' is set
        if (timeMode == null) {
            throw new WebClientResponseException("Missing the required parameter 'timeMode' when calling flightschedulesGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "airlines", airlines));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "flightNumberRanges", flightNumberRanges));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startDate", startDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endDate", endDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "daysOfOperation", daysOfOperation));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "timeMode", timeMode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "destination", destination));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "aircraftTypes", aircraftTypes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return apiClient.invokeAPI("/flightschedules", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Search all flights
     * Returns information about all flights based on available search criteria. Some criteria are mandatory to make a call to the API.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return List&lt;FlightAggregate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<FlightAggregate> flightschedulesGet(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return flightschedulesGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes).bodyToFlux(localVarReturnType);
    }

    /**
     * Search all flights
     * Returns information about all flights based on available search criteria. Some criteria are mandatory to make a call to the API.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return ResponseEntity&lt;List&lt;FlightAggregate&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<FlightAggregate>>> flightschedulesGetWithHttpInfo(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return flightschedulesGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes).toEntityList(localVarReturnType);
    }

    /**
     * Search all flights
     * Returns information about all flights based on available search criteria. Some criteria are mandatory to make a call to the API.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec flightschedulesGetWithResponseSpec(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        return flightschedulesGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes);
    }

    /**
     * Returns passenger flights
     * This operation returns schedules related only to passenger flights. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return List&lt;FlightAggregate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    private ResponseSpec flightschedulesPassengerGetRequestCreation(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        Object postBody = null;
        // verify the required parameter 'airlines' is set
        if (airlines == null) {
            throw new WebClientResponseException("Missing the required parameter 'airlines' when calling flightschedulesPassengerGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'startDate' is set
        if (startDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'startDate' when calling flightschedulesPassengerGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'endDate' is set
        if (endDate == null) {
            throw new WebClientResponseException("Missing the required parameter 'endDate' when calling flightschedulesPassengerGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'daysOfOperation' is set
        if (daysOfOperation == null) {
            throw new WebClientResponseException("Missing the required parameter 'daysOfOperation' when calling flightschedulesPassengerGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // verify the required parameter 'timeMode' is set
        if (timeMode == null) {
            throw new WebClientResponseException("Missing the required parameter 'timeMode' when calling flightschedulesPassengerGet", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, null);
        }
        // create path and map variables
        final Map<String, Object> pathParams = new HashMap<String, Object>();

        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, String> cookieParams = new LinkedMultiValueMap<String, String>();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "airlines", airlines));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "flightNumberRanges", flightNumberRanges));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "startDate", startDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "endDate", endDate));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "daysOfOperation", daysOfOperation));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "timeMode", timeMode));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "origin", origin));
        queryParams.putAll(apiClient.parameterToMultiValueMap(null, "destination", destination));
        queryParams.putAll(apiClient.parameterToMultiValueMap(ApiClient.CollectionFormat.valueOf("multi".toUpperCase(Locale.ROOT)), "aircraftTypes", aircraftTypes));

        final String[] localVarAccepts = { 
            "application/json"
        };
        final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        final String[] localVarContentTypes = { };
        final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

        String[] localVarAuthNames = new String[] {  };

        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return apiClient.invokeAPI("/flightschedules/passenger", HttpMethod.GET, pathParams, queryParams, postBody, headerParams, cookieParams, formParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
    }

    /**
     * Returns passenger flights
     * This operation returns schedules related only to passenger flights. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return List&lt;FlightAggregate&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Flux<FlightAggregate> flightschedulesPassengerGet(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return flightschedulesPassengerGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes).bodyToFlux(localVarReturnType);
    }

    /**
     * Returns passenger flights
     * This operation returns schedules related only to passenger flights. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return ResponseEntity&lt;List&lt;FlightAggregate&gt;&gt;
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public Mono<ResponseEntity<List<FlightAggregate>>> flightschedulesPassengerGetWithHttpInfo(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        ParameterizedTypeReference<FlightAggregate> localVarReturnType = new ParameterizedTypeReference<FlightAggregate>() {};
        return flightschedulesPassengerGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes).toEntityList(localVarReturnType);
    }

    /**
     * Returns passenger flights
     * This operation returns schedules related only to passenger flights. In case a flight is marked both as cargo and passenger it will also be returned.
     * <p><b>200</b> - Successful operation
     * <p><b>206</b> - Result truncated
     * <p><b>400</b> - Validation error
     * <p><b>401</b> - Authentication required
     * <p><b>404</b> - Flight not found
     * <p><b>500</b> - Server error
     * @param airlines The list of airline codes
     * @param startDate The period start date. SSIM date format DDMMMYY
     * @param endDate The period end date. SSIM date format DDMMMYY
     * @param daysOfOperation The days of operation, i.e. the days of the week. Whitespace padded to 7 chars. E.g.: &#39;  34 6 &#39;
     * @param timeMode The time mode of the period of operations
     * @param flightNumberRanges The flight number range filter string. e.g.: &#39;-100, 200, 100-200, 300-&#39;
     * @param origin Search for flights departing from this station. 3 letter IATA airport code.
     * @param destination Search for flights arriving at this station. 3 letter IATA airport code.
     * @param aircraftTypes The list of aircraft types
     * @return ResponseSpec
     * @throws WebClientResponseException if an error occurs while attempting to invoke the API
     */
    public ResponseSpec flightschedulesPassengerGetWithResponseSpec(@javax.annotation.Nonnull List<String> airlines, @javax.annotation.Nonnull String startDate, @javax.annotation.Nonnull String endDate, @javax.annotation.Nonnull String daysOfOperation, @javax.annotation.Nonnull TimeMode timeMode, @javax.annotation.Nullable String flightNumberRanges, @javax.annotation.Nullable String origin, @javax.annotation.Nullable String destination, @javax.annotation.Nullable List<String> aircraftTypes) throws WebClientResponseException {
        return flightschedulesPassengerGetRequestCreation(airlines, startDate, endDate, daysOfOperation, timeMode, flightNumberRanges, origin, destination, aircraftTypes);
    }
}
