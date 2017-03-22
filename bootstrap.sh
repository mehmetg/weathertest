#!/usr/bin/env bash
UTIL_FOLDER="utilities"
rm -rf ${UTIL_FOLDER}  && \
mkdir -p ${UTIL_FOLDER} && \
cd ${UTIL_FOLDER} && \
echo "Downloading Selenium jar" && \
wget http://selenium-release.storage.googleapis.com/3.3/selenium-server-standalone-3.3.1.jar && \
echo "...done" && \
echo "Downloading Chrome driver" && \
wget https://chromedriver.storage.googleapis.com/2.28/chromedriver_mac64.zip && \
unzip -a chromedriver_mac64.zip && \
rm chromedriver_mac64.zip && \
echo "...done" && \
echo "Downloading Gecko driver" && \
wget https://github.com/mozilla/geckodriver/releases/download/v0.15.0/geckodriver-v0.15.0-macos.tar.gz && \
tar zxf geckodriver-v0.15.0-macos.tar.gz && \
rm geckodriver-v0.15.0-macos.tar.gz