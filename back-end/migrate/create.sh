#!/bin/sh

sqlite3 khaneBeDoosh.db < migrate.sql
sudo chown _www:staff khaneBeDoosh.db
