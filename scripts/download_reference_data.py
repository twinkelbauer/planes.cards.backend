#!/usr/bin/env python3
"""Download Lufthansa aircraft and airport reference data as JSON."""

import json
import sys
from pathlib import Path

import requests

BASE_URL = "https://api.lufthansa.com/v1"
CLIENT_ID = "74ypmwy4bdsaxx2c6hjc66jbj"
CLIENT_SECRET = "9y3upVx3We"
RESOURCES_DIR = Path(__file__).resolve().parent.parent / "src" / "main" / "resources"
LIMIT = 100


def get_token() -> str:
    resp = requests.post(
        f"{BASE_URL}/oauth/token",
        data={
            "client_id": CLIENT_ID,
            "client_secret": CLIENT_SECRET,
            "grant_type": "client_credentials",
        },
    )
    resp.raise_for_status()
    token = resp.json()["access_token"]
    print(f"Obtained access token")
    return token


def fetch_paginated(token: str, path: str) -> list[dict]:
    """Fetch all pages from a paginated Lufthansa reference data endpoint."""
    headers = {
        "Authorization": f"Bearer {token}",
        "Accept": "application/json",
    }
    all_items = []
    offset = 0

    while True:
        url = f"{BASE_URL}{path}?limit={LIMIT}&offset={offset}"
        print(f"  GET {url}")
        resp = requests.get(url, headers=headers)
        if resp.status_code == 404:
            # API returns 404 when offset exceeds total count
            break
        resp.raise_for_status()
        data = resp.json()
        items = extract_items(data, path)

        if not items:
            break

        all_items.extend(items)
        print(f"    got {len(items)} items (total: {len(all_items)})")

        if len(items) < LIMIT:
            break

        offset += LIMIT

    return all_items


def extract_items(data: dict, path: str) -> list[dict]:
    """Extract the item list from the Lufthansa API response envelope."""
    try:
        resource = data.get("AircraftResource") or data.get("AirportResource") or {}
        if "AircraftSummaries" in resource:
            items = resource["AircraftSummaries"]["AircraftSummary"]
        elif "Airports" in resource:
            items = resource["Airports"]["Airport"]
        else:
            return []
        # API returns a single dict instead of a list when there's only one item
        if isinstance(items, dict):
            items = [items]
        return items
    except (KeyError, TypeError) as e:
        print(f"  Warning: could not extract items: {e}")
        return []


def parse_name(names_obj) -> str:
    """Extract English name from the Names structure."""
    if not names_obj:
        return ""
    name = names_obj.get("Name")
    if not name:
        return ""
    # Can be a single dict or a list of dicts with @LanguageCode
    if isinstance(name, list):
        for n in name:
            if isinstance(n, dict) and n.get("@LanguageCode") == "EN":
                return n.get("$", "")
        # fallback to first entry
        first = name[0]
        return first.get("$", str(first)) if isinstance(first, dict) else str(first)
    if isinstance(name, dict):
        return name.get("$", "")
    return str(name)


def transform_aircraft(raw: list[dict]) -> list[dict]:
    result = []
    for item in raw:
        result.append({
            "code": item.get("AircraftCode", ""),
            "name": parse_name(item.get("Names")),
            "airlineEquipCode": item.get("AirlineEquipCode"),
        })
    return result


def transform_airports(raw: list[dict]) -> list[dict]:
    result = []
    for item in raw:
        coord = (item.get("Position") or {}).get("Coordinate") or {}
        result.append({
            "code": item.get("AirportCode", ""),
            "name": parse_name(item.get("Names")),
            "cityCode": item.get("CityCode"),
            "countryCode": item.get("CountryCode"),
            "latitude": coord.get("Latitude"),
            "longitude": coord.get("Longitude"),
            "timeZoneId": item.get("TimeZoneId"),
        })
    return result


def write_json(data, filename: str):
    path = RESOURCES_DIR / filename
    with open(path, "w") as f:
        json.dump(data, f, indent=2, ensure_ascii=False)
    print(f"Wrote {len(data)} items to {path}")


def main():
    token = get_token()

    print("\nDownloading aircraft...")
    raw_aircraft = fetch_paginated(token, "/mds-references/aircraft")
    aircraft = transform_aircraft(raw_aircraft)
    write_json(aircraft, "lh-aircraft.json")

    print("\nDownloading airports...")
    raw_airports = fetch_paginated(token, "/mds-references/airports")
    airports = transform_airports(raw_airports)
    write_json(airports, "lh-airports.json")

    print(f"\nDone! {len(aircraft)} aircraft, {len(airports)} airports")


if __name__ == "__main__":
    main()
