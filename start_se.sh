#!/usr/bin/env bash
UTIL_FOLDER="utilities"
cd ${UTIL_FOLDER}
java -Dwebdriver.chrome.driver=chromedriver -Dwebdriver.gecko.driver=geckodriver -jar selenium-server-standalone-3.3.1.jar