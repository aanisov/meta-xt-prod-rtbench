IMAGE_INSTALL = " \
    dhcp-client \
"

IMAGE_FEATURES += "ssh-server-openssh"

SERIAL_CONSOLE = "115200 hvc0"

PREFERRED_PROVIDER_virtual/kernel = "linux-generic-armv8"

require inc/xt_shared_env.inc

share_artifacts () {
    mkdir -p ${XT_DIR_ABS_SHARED_BOOT_DOMU}
    rm -f ${XT_DIR_ABS_SHARED_BOOT_DOMU}/*.tar.xz
    cp -f --no-dereference --preserve=links ${IMGDEPLOYDIR}/*.tar.xz ${XT_DIR_ABS_SHARED_BOOT_DOMU}
}

IMAGE_POSTPROCESS_COMMAND += " share_artifacts; "
