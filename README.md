Deploy with ansible:

`ansible-playbook -i ansible_hosts -K deploy.yaml`

The above command requires an SSH config entry named `abmd-root`, providing passwordless root access to the target server.

This also assumes that the server has already been configured with a docker daemon setup in swarm mode, and access to `docker-compose` from the default root user's PATH.

For access to my hosted docker images, the root user must also be considered logged in, to the relevant ECR.

NOTE: I should probably setup an ansible role, or a NixOS environment to automate the needed system state.
