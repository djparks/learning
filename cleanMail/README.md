# cleanMail
Too much 'legal' spam.  Delete loads of too much email.

## TODO: 
- [X] Make OS independent.
- [ ] Move to Spring
  - [ ] Run based on a schedule
  - [ ] REST-based kick off Old and Analyze
- [ ] Dockerize
- [ ] Delete from GMail too. 
  - Having issues with this?
- [ ] Compile to executable.

## MAC
```BASH

cd ~/projects/cleanMail
mvn clean package
cp target/cleanMail-jar-with-dependencies.jar ~/utils/cleanMail.jar
cd ~/utils
```

`crontab -e`

*/5 * * * * cd /Users/djparks/utils/ && ./cleanMail.sh  >> /Users/djparks/utils/cleanMail.txt 2>&1

## PC

