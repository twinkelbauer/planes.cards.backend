# ✈️ Planes Cards Backend

> **Hackathon Alert!** 🚨 This project was built during a hackathon, so expect some... *creative* coding practices and fun chaos!

## What is this?

This is the backend for a planes/aviation cards game built with Kotlin and Spring Boot. It's got PostgreSQL for data, WebSockets for real-time fun, monitoring with Prometheus + Grafana, and integration with Gemini AI for extra aviation facts!

## 🎯 Hackathon Disclaimer

**Fair Warning:** This code was written with:
- ⏰ Time pressure (because hackathons)
- ☕ Too much caffeine
- 🎉 Maximum fun as the priority
- 🚀 "Make it work" > "Make it perfect"

So yes, you'll find commits like "REMOVE THOSE CARSD PLS", "Make it slow", and "ADd bad coding pratices" - we're not ashamed! 😄

## 🚀 Quick Start

```bash
# Fire it up with Docker
docker-compose up

# Or if you want the dev setup
docker-compose -f dev-compose.yaml up

# Or build and run locally
./gradlew bootRun
```

## 🔧 What's Running?

- **Backend API**: `http://localhost:8081`
- **Grafana Dashboard**: `http://localhost:3001` (admin/admin)
- **Prometheus Metrics**: `http://localhost:9091`

*Note: All ports are +1 from the defaults because... well, they were already taken! 🤷‍♂️*

## 📁 Project Structure

- `src/` - The Kotlin/Spring Boot magic happens here
- `lufthansa-client/` - Generated API client (because why write it manually?)
- `monitoring/` - Grafana + Prometheus configs
- `db/` - Database init scripts
- `scripts/` - Utility scripts

## 🎮 Features

- Aviation data management
- Real-time WebSocket connections
- AI-powered aviation fun facts (thanks Gemini!)
- Metrics and monitoring (because we're professionals... sort of)
- Docker-ized everything

## 🛠️ Tech Stack

- **Kotlin** + **Spring Boot** (4.0.3)
- **PostgreSQL** (for the data)
- **WebSockets** (for the real-time magic)
- **Prometheus** + **Grafana** (for pretty graphs)
- **Gemini AI** (for the smart stuff)
- **Docker** (because containers are cool)

## 🤝 Contributing

Found some questionable code? That's the hackathon spirit! Feel free to:
1. Laugh at our commit messages
2. Improve the chaos (PRs welcome!)
3. Add more fun features

## ⚠️ Production Ready?

LOL, no. This was built for fun in limited time. If you want to use this seriously, maybe:
- Add proper error handling
- Write some tests (we know, we know...)
- Clean up those commit messages
- Refactor... well, everything

But hey, it works and it was a blast to build! 🎉

---

*Built with ❤️ and way too much coffee during a hackathon by 4 dumbasses*