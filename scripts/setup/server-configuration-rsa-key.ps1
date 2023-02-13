$sshFolder="${env:HOME}/.ssh"
$sshKeyFileName="server-configuration-key.rsa"
$sshKeyFilePath="${sshFolder}/${sshKeyFileName}"
$sshConfigFilePath="${sshFolder}/config"
$sshHostname="server-configuration"

If ( -Not ( Test-Path -PathType "Container" -Path "$sshFolder" ) ) {
    New-Item -ItemType "Directory" -Path "$sshFolder"
}

If ( -Not ( Test-Path -PathType "Leaf" -Path "$sshKeyFilePath" ) ) {
    ssh-keygen -b 2048 -t rsa -f "$sshKeyFilePath" -q -N '""'
}

If ( -Not ( Test-Path -PathType "Leaf" -Path "$sshConfigFilePath" ) ) {
    New-Item -ItemType "File" "$sshConfigFilePath"
}

$exists=$( Select-String -Path "$sshConfigFilePath" -Pattern "Host ${sshHostname}" )
if ( -Not ( $exists ) ) {
    Write-Output "Host ${sshHostname}" | Out-File -Append -Path "$sshConfigFilePath"
    Write-Output "  HostName github.com" | Out-File -Append -Path "$sshConfigFilePath"
    Write-Output "  User git" | Out-File -Append -Path "$sshConfigFilePath"
    Write-Output "  PreferredAuthentications publickey" | Out-File -Append -Path "$sshConfigFilePath"
    Write-Output "  IdentityFile ${sshKeyFilePath}" | Out-File -Append -Path "$sshConfigFilePath"
    Write-Output "  UseKeychain yes" | Out-File -Append -Path "$sshConfigFilePath"
    Write-Output "  AddKeysToAgent yes" | Out-File -Append -Path "$sshConfigFilePath"
}

Write-Host "=============================================================================="
Write-Host "As a final step to complete the setup,"
Write-Host "you need to upload the public key to Github."
Write-Host "Go to Github > Settings > SSH and GPG keys > New SSH key."
Write-Host "Provide a title and paste the contents of the following file on the key field:"
Write-Host "${sshKeyFilePath}.pub"
Write-Host "=============================================================================="
