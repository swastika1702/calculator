FROM jenkins/jenkins:lts-jdk17

USER root
# Maven, Python, git, ssh client, curl, plus the Docker CLI from Docker's official apt repo
RUN apt-get update && apt-get install -y --no-install-recommends \
        ca-certificates curl gnupg maven python3 python3-venv python3-pip git openssh-client \
    && install -m 0755 -d /etc/apt/keyrings \
    && curl -fsSL https://download.docker.com/linux/debian/gpg -o /etc/apt/keyrings/docker.asc \
    && chmod a+r /etc/apt/keyrings/docker.asc \
    && . /etc/os-release \
    && echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] \
        https://download.docker.com/linux/debian $VERSION_CODENAME stable" \
        > /etc/apt/sources.list.d/docker.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends docker-ce-cli \
    && rm -rf /var/lib/apt/lists/*

# Ansible + Docker SDK for Python in an isolated virtual environment
RUN python3 -m venv /opt/ansible-venv \
    && /opt/ansible-venv/bin/pip install --no-cache-dir ansible docker
ENV PATH="/opt/ansible-venv/bin:${PATH}"

# Ansible collection with the docker_image / docker_container modules
RUN ansible-galaxy collection install community.docker -p /usr/share/ansible/collections

# Learning shortcut: stay root so Jenkins can use the mounted Docker socket (see Section 13)
USER root