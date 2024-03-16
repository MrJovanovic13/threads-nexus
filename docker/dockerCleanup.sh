#!/usr/bin/env bash
if [[ "$(docker images -q threads-nexus-image 2> /dev/null)" != "" ]]; then
	docker-compose rm -f -s threads_nexus
	docker image rm threads-nexus-image
fi
