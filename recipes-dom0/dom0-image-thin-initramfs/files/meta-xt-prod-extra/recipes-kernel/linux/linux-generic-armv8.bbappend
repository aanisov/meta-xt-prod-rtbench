FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BRANCH = "pr_pv_drv_upstream_for_yocto_3_7_migration"
SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://github.com/andr2000/linux.git;branch=${BRANCH} \
    file://defconfig \
  "
do_deploy_append () {
    find ${D}/boot -iname "vmlinux*" -exec tar -cJvf ${STAGING_KERNEL_BUILDDIR}/vmlinux.tar.xz {} \;
}
