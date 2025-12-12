#!/bin/sh
java -jar "fitnesse-for-appian.jar" -p 8980 -f "configs/custom.properties" &
wait
