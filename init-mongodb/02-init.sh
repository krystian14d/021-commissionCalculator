#!/bin/bash

echo "########### Loading data to Mongo DB ###########"

mongoimport --db testdb1 --collection transactions --type=csv --headerline --file=/tmp/data/transacions.csv