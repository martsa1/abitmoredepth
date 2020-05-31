#!/usr/bin/env bash

# Ensure we are at the base of the repository for the deploy command
pushd $(git rev-parse --show-toplevel)

ansible-playbook -i ansible_hosts deploy.yaml

# Return to wherever we were
popd
