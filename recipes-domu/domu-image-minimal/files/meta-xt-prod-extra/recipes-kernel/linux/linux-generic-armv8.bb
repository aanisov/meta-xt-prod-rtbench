DESCRIPTION = "Generic recipe for AArch64 kernels"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Skip processing of this recipe if it is not explicitly specified as the
# PREFERRED_PROVIDER for virtual/kernel. This avoids errors when trying
# to build multiple virtual/kernel providers, e.g. as dependency of
# core-image-rt-sdk, core-image-rt.
python () {
    if d.getVar("PREFERRED_PROVIDER_virtual/kernel", True) != "linux-generic-armv8":
        raise bb.parse.SkipPackage("Set PREFERRED_PROVIDER_virtual/kernel to linux-generic-armv8 to enable it")
}

LINUX_KERNEL_TYPE = "tiny"
LINUX_VERSION ?= "4.14.30"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "generic-armv8-xt"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BRANCH = "yocto-3.7-migration"
SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/xen-troops/linux.git;branch=${BRANCH} \
    file://defconfig \
  "

require inc/xt_shared_env.inc

do_deploy_append() {
    mkdir -p ${XT_DIR_ABS_SHARED_BOOT_DOMU}
    rm -f ${XT_DIR_ABS_SHARED_BOOT_DOMU}/Image*
    cp -f --no-dereference --preserve=links ${DEPLOYDIR}/Image* ${XT_DIR_ABS_SHARED_BOOT_DOMU}/
}

