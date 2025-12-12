#!/usr/bin/bash

CURRENT_DIR=`dirname $0`

RESOURCES=$(realpath $CURRENT_DIR)
echo "Setting resources to be $RESOURCES"

TARGET=$(realpath "$CURRENT_DIR/target")
CUSTOM_PROPERTIES="$RESOURCES/configs/custom.properties"

sed-populate-key() {
  sed -i "s#$1=.*#$1=$2#" $CUSTOM_PROPERTIES
}

sed-populate-key "automated.testing.home" "$RESOURCES"
sed-populate-key "download.directory" "$TARGET"
