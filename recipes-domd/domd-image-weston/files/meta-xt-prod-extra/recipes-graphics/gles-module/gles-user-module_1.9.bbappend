require inc/xt_shared_env.inc

BRANCH = "1.9/4813199-dev"
SRCREV = "${AUTOREV}"

EXTRA_OEMAKE += "PVRSRV_VZ_NUM_OSID=${XT_PVR_NUM_OSID}"
DEPENDS += " gles-module-egl-headers wayland-native"
RDEPENDS_${PN} += "python"
