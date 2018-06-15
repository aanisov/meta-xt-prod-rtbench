SUMMARY = "Base for Fusion images"

LICENSE = "MIT"

inherit build_yocto
inherit xt_quirks

S = "${WORKDIR}/repo"

################################################################################
# Generic ARMv8
################################################################################

# Set default poky and openembedded branch to rocko
BRANCH = "rocko"

SRC_URI = " \
    git://git.yoctoproject.org/poky;destsuffix=repo/poky;branch=${BRANCH} \
"
#    git://git.openembedded.org/openembedded;destsuffix=repo/meta-openembedded;branch=${BRANCH} \
#"

SRCREV_repo/poky = "342fbd6a3e57021c8e28b124b3adb241936f3d9d"
#SRCREV_repo/meta-openembedded = "dacfa2b1920e285531bec55cd2f08743390aaf57"

###############################################################################
# extra layers and files to be put after Yocto's do_unpack into inner builder
###############################################################################
# these will be populated into the inner build system on do_unpack_xt_extras
XT_QUIRK_UNPACK_SRC_URI += " \
    file://meta-xt-images-extra;subdir=repo \
    file://meta-xt-prod-extra;subdir=repo \
    file://xt_shared_env.inc;subdir=repo/meta-xt-prod-extra/inc \
"

# these layers will be added to bblayers.conf on do_configure
XT_QUIRK_BB_ADD_LAYER += " \
    meta-xt-images-extra \
    meta-xt-prod-extra \
"

# override what xt-distro wants as machine as we don't depend on HW
MACHINE = "generic-armv8"

XT_BB_IMAGE_TARGET = "core-image-weston"

################################################################################
# set to AUTOREV so we can override this while reconstructing this build with
# specific commit ID
################################################################################
configure_versions_base() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    base_update_conf_value ${local_conf} "PREFERRED_PROVIDER_virtual/kernel" "linux-generic-armv8"
}

python do_configure_append() {
    bb.build.exec_func("configure_versions_base", d)
}

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
FILESEXTRAPATHS_prepend := "${THISDIR}/../../inc:"


XT_BB_LAYERS_FILE = "meta-xt-prod-extra/doc/bblayers.conf.domf-image-weston"
XT_BB_LOCAL_CONF_FILE = "meta-xt-prod-extra/doc/local.conf.domf-image-weston"

configure_versions() {
    local local_conf="${S}/build/conf/local.conf"

    cd ${S}

    # FIXME: normally bitbake fails with error if there are bbappends w/o recipes
    # which is the case for agl-demo-platform's recipe-platform while building
    # agl-image-weston: due to AGL's Yocto configuration recipe-platform is only
    # added to bblayers if building agl-demo-platform, thus making bitbake to
    # fail if this recipe is absent. Workaround this by allowing bbappends without
    # corresponding recipies.
    base_update_conf_value ${local_conf} BB_DANGLINGAPPENDS_WARNONLY "yes"

    # hvc0 is not a serial console, so is not processes properly by a modern
    # start_getty script which is installed for sysvinit based systems.
    # Instead a distro feature xen should be enabled in a configuration, so a
    # direct call to getty with hvc0 is installed into inittab by meta-viltualization.
    # Though systemd properly processes hvc0 advertised as serial console, and is not
    # provided with console by distro-feature xen.
    # So keep following line aligned with an init manager set for the system.
    base_update_conf_value ${local_conf} SERIAL_CONSOLES = "115200;hvc0"

    # set default timezone to Las Vegas
    base_update_conf_value ${local_conf} DEFAULT_TIMEZONE "US/Pacific"
}

python do_configure_append() {
    bb.build.exec_func("configure_versions", d)
}

