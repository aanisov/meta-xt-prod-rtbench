SUMMARY = "A config file and a run script for a minimal domain"
DESCRIPTION = "A config file and a run script for a minimal domain"

require inc/xt_shared_env.inc

PV = "0.1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "\
    file://domu.cfg \
    file://guest_domu \
"

S = "${WORKDIR}"

FILES_${PN} = " \
    ${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/domu.cfg \
"

inherit update-rc.d

FILES_${PN}-run += " \
    ${sysconfdir}/init.d/guest_domu \
"

PACKAGES += " \
    ${PN}-run \
"

# configure init.d scripts
INITSCRIPT_PACKAGES = "${PN}-run"

INITSCRIPT_NAME_${PN}-run = "guest_domu"
INITSCRIPT_PARAMS_${PN}-run = "defaults 87"

do_install() {
    install -d ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}
    install -m 0744 ${WORKDIR}/${DOMA_CONFIG} ${D}${base_prefix}${XT_DIR_ABS_ROOTFS_DOM_CFG}/domu.cfg

    install -d ${D}${sysconfdir}/init.d
    install -m 0744 ${WORKDIR}/guest_domu ${D}${sysconfdir}/init.d/

}
