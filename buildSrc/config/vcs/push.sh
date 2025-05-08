#!/usr/bin/env bash

git add .
git commit -m "$(/bin/date "+%F %T")"

lst=$(git remote -v | awk -F' ' '{print $1}' | sort -u | xargs echo)
for name in $lst;
do
  git push $name
done
