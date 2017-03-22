# Weather Test

## Content:

### API Tests:
Exercises the endpoint below: http://api.wunderground.com/api/5c25f5ea989a5950/conditions/q/CA/San_Francisco.json
* Checks version to be "0.1" (not "v0.1" as documentation suggests)
* Checks current_observation.temp_f and current_observation.temp_c for unit conversion errors. (Does not check `current_conditions.*` as the field does not exist.)
* Checks if `current_observation.observation_time` is within 5 minutes of current time hence the right date. (Does not check `current_observation.*` as the field does not exist.)

The data models can be modified to force the field names to what was in the instructions, but if done so all tests in this category would fail consistently.

### UI Tests:
Exercises the URL below using Selenium: https://www.wunderground.com/us/ca/freedom/zmw:95073.1.99999?MR=1
* Checks history tab link and if link leads to a history page
* Checks if the cursor with all the labels shows up and updates as the user hovers on the weathe graph. (Mildly flakly, it may need a few tweaks to make sure hover happens after the graph is loaded and functional)
* Checks if customize menu for the graph works and checks the initial state and functionality of the 'Dew Point" checkbox

The data models can be modified to force the field names to what was in the instructions, but if done so all tests in this category would fail consistently.

## Dependencies
* MacOS Sierra (will run fine on pretty much any system as long as the chromedriver/geckodriver for the platform is swapped out for the darwin builds the bootstrap.sh script will download. For windows one may need to change the start_se.sh as well to reference the right executable for the chromedriver/geckodriver)
* wget
* jdk8u121
* mvn 3.3.9
* Safari 10

## Usage
* Run the `bootstrap.sh` script from the project root.
* Run the `start_se.sh` script from the project root. (in a separate terminal)
* Run `mvn verify`

## Known Issues:
* SafariDriver ang GeckoDriver do not get along with Se too well. It requires some **shimming** to work
* Selenium server needs to be started manually.
* The moveTo command is sensitive to mouse movement, so ideally the UI tests should run headless or unattended.



