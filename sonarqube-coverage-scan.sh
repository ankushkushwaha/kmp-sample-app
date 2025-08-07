#!/bin/bash

set -e

echo "🔁 Starting clean and full coverage pipeline..."

# Make sure Gradle is executable
chmod +x ./gradlew

#echo "🚀 Running unified coverage scan (androidApp + iosApp + shared)..."
./gradlew allKmpCoverage --no-configuration-cache


#echo "🚀 Running iOS + shared coverage scan (iosApp + shared)..."
#./gradlew iosCoverage --no-configuration-cache

#echo "🚀 Running android + shared coverage scan (androidApp + shared)..."
#./gradlew androidCoverage --no-configuration-cache


echo "✅ All done! Reports generated and sent to SonarQube."
