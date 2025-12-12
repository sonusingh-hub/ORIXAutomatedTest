#/bin/bash

bash ./setupCustomProperties.sh

PROPERTY_FILE="fitnesse-automation.properties"

function getProperty {
   PROP_KEY=$1
   PROP_VALUE=`cat "${PROPERTY_FILE}" | grep "${PROP_KEY}=" | cut -d'=' -f2`
   echo "${PROP_VALUE}"
}

if [ -f "${PROPERTY_FILE}" ]; then

  port=$(getProperty "port")
  customProperties=$(getProperty "customProperties")
  test=$(getProperty "testPath")
  format=$(getProperty "format")
  returnFile=$(getProperty "returnFile")

  java -jar lib/fitnesse-20230503-standalone.jar -p "${port}" -f "${customProperties}" -c "${test}&format=${format}" -b "${returnFile}"

else
  echo "$PROPERTY_FILE not found."
fi
