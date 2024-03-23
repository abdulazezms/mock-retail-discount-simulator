ifeq ($(OS),Windows_NT)
    OPEN := start
else
    UNAME := $(shell uname -s)
    ifeq ($(UNAME),Linux)
        OPEN := xdg-open
    endif
endif

build:
	mvn clean package -DskipTests

test:
	mvn clean test -Dspring.profiles.active=unit-test

test-and-display-test-results:
	mvn clean test -Dspring.profiles.active=unit-test
	$(OPEN) "target/site/surefire-report.html"

test-and-display-test-coverage:
	mvn clean test -Dspring.profiles.active=unit-test
	$(OPEN) "target/site/jacoco/index.html"

run:
	docker-compose up --build


.PHONY: build test test-and-report-test-results test-and-report-test-coverage run