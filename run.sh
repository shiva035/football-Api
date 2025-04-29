#!/bin/bash
   # Load .env file, ignoring comments and empty lines
   set -a
   source <(grep -v '^#' .env | sed -E 's/^[ \t]*//;s/[ \t]*$//')
   set +a
   # Run Maven
   mvn clean spring-boot:run