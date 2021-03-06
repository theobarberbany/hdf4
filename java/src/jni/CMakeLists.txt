cmake_minimum_required (VERSION 3.2.2)
PROJECT (HDF4_JAVA_JNI C)

set (HDF4_JAVA_JNI_CSRCS
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfanImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfdfpalImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfdfuImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfexceptionImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfgrImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfheImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfhxImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfnativeImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfr24Imp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfr8Imp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfsdsImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfstructsutil.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfvdataImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfvfImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfvgroupImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfvhImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfvqImp.c
    ${HDF4_JAVA_JNI_SOURCE_DIR}/hdfvsqImp.c
)

INCLUDE_DIRECTORIES ( ${HDF4_JAVA_JNI_SOURCE_DIR} )

set (CMAKE_INCLUDE_CURRENT_DIR_IN_INTERFACE ON)

########### JNI libraries always must be built shared  ###############
add_library (${HDF4_JAVA_JNI_LIB_TARGET} SHARED ${HDF4_JAVA_JNI_CSRCS})
TARGET_C_PROPERTIES (${HDF4_JAVA_JNI_LIB_TARGET} SHARED " " " ")
target_link_libraries (${HDF4_JAVA_JNI_LIB_TARGET} PRIVATE ${HDF4_MF_LIB_TARGET} ${HDF4_SRC_LIB_TARGET})
set_target_properties (${HDF4_JAVA_JNI_LIB_TARGET} PROPERTIES FOLDER libraries/jni)
SET_GLOBAL_VARIABLE (HDF4_JAVA_LIBRARIES_TO_EXPORT "${HDF4_JAVA_LIBRARIES_TO_EXPORT};${HDF4_JAVA_JNI_LIB_TARGET}")
H4_SET_LIB_OPTIONS (${HDF4_JAVA_JNI_LIB_TARGET} ${HDF4_JAVA_JNI_LIB_NAME} SHARED)
if (WIN32)
  get_filename_component (HDF4_JAVA_JNI_DLL_NAME ${HDF4_JAVA_JNI_LIB_TARGET} NAME_WE)
  set (HDF4_JAVA_JNI_DLL_NAME "${HDF4_JAVA_JNI_DLL_NAME}$<$<CONFIG:Debug>:_D>")
  # message (STATUS "HDF4_JAVA_JNI_DLL_NAME: ${HDF4_JAVA_JNI_DLL_NAME}")
  if (BUILD_TESTING)
    add_custom_target (HDF4_JAVA_JNI-Test-Copy ALL
        COMMAND ${CMAKE_COMMAND} -E copy_if_different ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/${CMAKE_CFG_INTDIR}/${HDF4_JAVA_JNI_DLL_NAME}${CMAKE_SHARED_LIBRARY_SUFFIX} ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/${HDF4_JAVA_JNI_DLL_NAME}${CMAKE_SHARED_LIBRARY_SUFFIX}
        COMMENT "Copying ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/${CMAKE_CFG_INTDIR}/${HDF4_JAVA_JNI_DLL_NAME}${CMAKE_SHARED_LIBRARY_SUFFIX} to ${CMAKE_RUNTIME_OUTPUT_DIRECTORY}/"
    )
    add_dependencies (HDF4_JAVA_JNI-Test-Copy ${HDF4_JAVA_JNI_LIB_TARGET})
  endif ()
endif ()

#-----------------------------------------------------------------------------
# Add Target(s) to CMake Install for import into other projects
#-----------------------------------------------------------------------------
if (HDF4_EXPORTED_TARGETS)
  INSTALL_TARGET_PDB (${HDF4_JAVA_JNI_LIB_TARGET} ${HDF4_INSTALL_BIN_DIR} libraries)
  install (
      TARGETS
          ${HDF4_JAVA_JNI_LIB_TARGET}
      EXPORT
          ${HDF4_EXPORTED_TARGETS}
      LIBRARY DESTINATION ${HDF4_INSTALL_LIB_DIR} COMPONENT libraries
      ARCHIVE DESTINATION ${HDF4_INSTALL_LIB_DIR} COMPONENT libraries
      RUNTIME DESTINATION ${HDF4_INSTALL_LIB_DIR} COMPONENT libraries
      FRAMEWORK DESTINATION ${HDF4_INSTALL_FWRK_DIR} COMPONENT libraries
      INCLUDES DESTINATION include
  )
endif ()
