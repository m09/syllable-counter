SHELL := /bin/bash

deploy-site:
	mvn clean site
	git checkout --orphan deploy
	git rm -r --cached .
	git add -f target/site
	git mv target/site/* .
	git commit -m "deploy maven website to github pages"
	git push -f github deploy:gh-pages
	git mv -k * target/site
	git checkout -f master
	git add .
	git branch -D deploy

.PHONY: deploy-site
