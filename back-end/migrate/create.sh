#!/bin/sh
rm -f khaneBeDoosh.db &> /dev/null
sqlite3 khaneBeDoosh.db < migrate.sql
sudo chown _www:staff khaneBeDoosh.db
