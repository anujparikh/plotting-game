#!/bin/bash

# set ownership of the db folder for pgadmin user (uid=5050)
sudo chown -R 5050:5050 db

# Inspect containers to get bridge network info.
# This is required to connect from one container to another.
# Connections from host can use localhost or equivalent.
# In our case this is required when connecting from the pgadmin (running in the container)
# to pg db (also running in the container).

## See https://stackoverflow.com/questions/53610385/docker-postgres-and-pgadmin-4-connection-refused

# sudo docker inspect <container-id>