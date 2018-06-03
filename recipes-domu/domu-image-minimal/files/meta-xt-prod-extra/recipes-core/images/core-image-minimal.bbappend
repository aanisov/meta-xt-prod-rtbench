IMAGE_INSTALL = " \
    dhcp-client \
"

IMAGE_FEATURES += "ssh-server-openssh"

SERIAL_CONSOLE = "115200 hvc0"

PREFERRED_PROVIDER_virtual/kernel = "linux-generic-armv8"
