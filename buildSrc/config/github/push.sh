#!/usr/bin/env bash

git status
git add .
git commit -m "$(/bin/date "+%F %T")"
