#!/usr/bin/bash

CURRENT_DIR=`dirname $0`

RESOURCES=$(realpath $CURRENT_DIR)
echo "Setting resources to be $RESOURCES"

TARGET=$(realpath "$CURRENT_DIR/target")
DRIVERS="$RESOURCES/lib/drivers"
CUSTOM_PROPERTIES="$RESOURCES/src/main/resources/configs/custom.properties"

sed-populate-key() {
  sed -i "s#$1=.*#$1=$2#" $CUSTOM_PROPERTIES
}

sed-populate-key "automated.testing.home" "$RESOURCES"
sed-populate-key "download.directory" "$TARGET"
sed-populate-key "chrome.driver.home" "$DRIVERS/chromedriver-linux"
sed-populate-key "firefox.driver.home" "$DRIVERS/geckodriver-linux64"
sed-populate-key "edge.driver.home" "$DRIVERS/msedgedriver-linux"
