#!/bin/sh
WORKSPACE="/letv/auto_testing/ad_show_test/test_data"/$2

pwd

cd $WORKSPACE

#1.update date filename schedule.csv

TODAY=`date +%Y-%m-%d`
echo $TODAY

sed -ig "s/[0-9]\{4\}-[0-9]\{2\}-[0-9]\{2\}/$TODAY/g" ./schedule.csv

pwd

#2.dos2unix *

dos2unix *

pwd

#3.clear250

ssh root@$1 rm -rf /letv/data/dump/load_data/*

ssh root@$1 /letv/sources/letvredis/src/redis-cli -p 1090 -n 0 keys "*" | xargs /letv/sources/letvredis/src/redis-cli -p 1090 -n 0 del

#4.cp data to 250

scp -r $WORKSPACE/* root@$1:/letv/data/dump/load_data/

#5.at 250,load_data

ssh root@$1  /letv/testing/load_data.sh