#!/bin/bash

ssh_folder="${HOME}/.ssh"
ssh_key_file_name="server-configuration-key.rsa"
ssh_key_file_path="${ssh_folder}/${ssh_key_file_name}"
ssh_config_file_path="${ssh_folder}/config"
ssh_hostname="server-configuration"

if [[ ! -d "${ssh_folder}" ]]
then
  mkdir "${ssh_folder}"
fi

if [[ ! -f "${ssh_key_file_path}" ]]
then
  ssh-keygen -m PEM -t rsa -b 4096 -f "${ssh_key_file_path}" -q -N ""
fi

if [[ ! -f "${ssh_config_file_path}" ]]
then
  touch "${ssh_config_file_path}"
fi

exists=$(cat "${ssh_config_file_path}" | grep -w "Host ${ssh_hostname}")
if [[ -z "${exists}" ]]
then
  {
    echo "Host ${ssh_hostname}"
    echo "  HostName github.com"
    echo "  User git"
    echo "  PreferredAuthentications publickey"
    echo "  IdentityFile ${ssh_key_file_path}"
    echo "  UseKeychain yes"
    echo "  AddKeysToAgent yes"
  } >> "${ssh_config_file_path}"
fi

echo "=============================================================================="
echo "As a final step to complete the setup,"
echo "you need to upload the public key to Github."
echo "Go to Github > Settings > SSH and GPG keys > New SSH key."
echo "Provide a title and paste the contents of the following file on the key field:"
echo "${ssh_key_file_path}.pub"
echo "=============================================================================="
